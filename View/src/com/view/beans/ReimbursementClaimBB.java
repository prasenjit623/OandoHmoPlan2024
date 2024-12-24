package com.view.beans;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

import view.ApprovalPayload;
import view.ApprovalProcess;

public class ReimbursementClaimBB {
    private RichPopup attachPop;
    
    public ReimbursementClaimBB() {
        super();
    }
    public String onSaveAction() {
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Request Saved Successfully !");
        return "toSearch";
    }
    public String onCancelAction() {
        ADFUtils.findOperation("Rollback").execute();
        return "toSearch";
    }
    
    public String onSubmitAction() {
        if(callSubmitPayload()){
            return "toSearch";
        }
        return "";
    }
    
    private boolean callSubmitPayload() {
        ViewObject mediVo = com.view.uiutils.ADFUtils.findIterator("HMOReimbursement_VOIterator").getViewObject();
        Row r = mediVo.getCurrentRow();
        r.setAttribute("ActiveFlag", "Y");
        r.setAttribute("ApprovalStatus", "SUBMITTED FOR APPROVAL");
        ADFUtils.findOperation("Commit").execute();
        
        String[] a = null;
        
        String reimbursementId = r.getAttribute("ReimbursementId")!=null?r.getAttribute("ReimbursementId").toString():"";
        String year = r.getAttribute("HmoTransYear")!=null?r.getAttribute("HmoTransYear").toString():"";
        String hmo_ref = r.getAttribute("HmoTransReference")!=null?r.getAttribute("HmoTransReference").toString():"";
        String personId = r.getAttribute("PersonId")!=null?r.getAttribute("PersonId").toString():"";
        String personNo = r.getAttribute("PersonNumber")!=null?r.getAttribute("PersonNumber").toString():"";
        String title = r.getAttribute("Title")!=null?r.getAttribute("Title").toString():"";
        String first_name = r.getAttribute("FirstName")!=null?r.getAttribute("FirstName").toString():"";
        String last_name = r.getAttribute("LastName")!=null?r.getAttribute("LastName").toString():"";  
        String mobileNo = r.getAttribute("MobileNo")!=null?r.getAttribute("MobileNo").toString():""; 
        String email = ADFContext.getCurrent().getSessionScope().get("UserEmailAddress").toString();
        String policyHolderName = r.getAttribute("PolicyHolderName")!=null?r.getAttribute("PolicyHolderName").toString():"";
        String stateOfResi = r.getAttribute("StateOfResidence")!=null?r.getAttribute("StateOfResidence").toString():"";
        String hospital = r.getAttribute("Hospital")!=null?r.getAttribute("Hospital").toString():"";
        String hospNameUsed = r.getAttribute("HospitalNameUsed")!=null?r.getAttribute("HospitalNameUsed").toString():"";
        String dateofVisit = r.getAttribute("DateOfVisit")!=null?r.getAttribute("DateOfVisit").toString():"";
        String emergency = r.getAttribute("Emergency")!=null?r.getAttribute("Emergency").toString():"";
        String hmoProvider = r.getAttribute("HmoProvider")!=null?r.getAttribute("HmoProvider").toString():"";
        String reason = r.getAttribute("ReasonForReimbursement")!=null?r.getAttribute("ReasonForReimbursement").toString():"";
        String enrolleeBank = r.getAttribute("EnrolleeBank")!=null?r.getAttribute("EnrolleeBank").toString():"";
        String enrolleeBankAcc = r.getAttribute("EnrolleeBankAccount")!=null?r.getAttribute("EnrolleeBankAccount").toString():"";
        String bankAccName = r.getAttribute("BankAccountName")!=null?r.getAttribute("BankAccountName").toString():"";
        String reqType = r.getAttribute("RequestType")!=null?r.getAttribute("RequestType").toString():"";
        String comments = r.getAttribute("ApproverComments")!=null?r.getAttribute("ApproverComments").toString():"";
        String status = r.getAttribute("ApprovalStatus")!=null?r.getAttribute("ApprovalStatus").toString():"";
        String reimbursementReqNo = r.getAttribute("ReimbursementReqNo")!=null?r.getAttribute("ReimbursementReqNo").toString():"";

        String xmlData =
            ApprovalPayload.reimbursementChangesXMLData(reimbursementId, year, hmo_ref, personId, personNo, title, first_name, last_name,
                                                        mobileNo, email, policyHolderName, stateOfResi, hospital, hospNameUsed, dateofVisit,
                                                        emergency, hmoProvider, reason, enrolleeBank, enrolleeBankAcc, bankAccName, reqType,
                                                        comments, status, reimbursementReqNo);
        
        
        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, ApprovalPayload.REIMBURSEMENT_WSDL, ApprovalPayload.REIMBURSEMENT_CHANGE_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("HMO Reimbursement Form Submitted Successfully !!");
            return true;
        } else {
            com.view.uiutils.JSFUtils.addFacesInformationMessage("Error");
            r.setAttribute("ApprovalStatus", "DRAFT");
        }
        return false;
    }

    /**Method to create blobdomain for uploaded file
     * */
    private BlobDomain createBlobDomain(UploadedFile file) {
        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;

        try {
            in = file.getInputStream();

            blobDomain = new BlobDomain();
            out = blobDomain.getBinaryOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;

            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }

        return blobDomain;
    }


    public void onUploadFileVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            UploadedFile myfile = (UploadedFile) vce.getNewValue();
            if (myfile != null) {
                ViewObject vo = com.view.uiutils.ADFUtils.findIterator("Reimbursement_Attachment_VOIterator").getViewObject();
                Row curRow = vo.getCurrentRow();
                String filename = myfile.getFilename();
                String ContentType = myfile.getContentType();
                try {
                    curRow.setAttribute("AttachmentName", filename);
                   // curRow.setAttribute("AttachmentType", ContentType);
                   if (ContentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                       curRow.setAttribute("AttachmentType", "application/docx");
                   } else if (ContentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                       curRow.setAttribute("AttachmentType", "application/xlsx");
                   } else {
                       curRow.setAttribute("AttachmentType", ContentType);
                   }
                    curRow.setAttribute("Attachment", createBlobDomain(myfile));
                } catch (Exception ex) {
                }
            }
        }
    }

    public void onDownloadProof(FacesContext facesContext, OutputStream outputStream) {
        ViewObject hdrVo = com.view.uiutils.ADFUtils.findIterator("Reimbursement_Attachment_VOIterator").getViewObject();
        Row row = hdrVo.getCurrentRow();
        BlobDomain blob = (BlobDomain) row.getAttribute("Attachment");
        try {
            IOUtils.copy(blob.getInputStream(), outputStream);
            blob.closeInputStream();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setAttachPop(RichPopup attachPop) {
        this.attachPop = attachPop;
    }

    public RichPopup getAttachPop() {
        return attachPop;
    }

    public void onAddInAttachPop(ActionEvent actionEvent) {
        attachPop.hide();
    }

    public void onProofOfPayment(ActionEvent actionEvent) {
        com.view.uiutils.JSFUtils.showPopup(attachPop);
    }
    /**
    * Method to get current date
    * @return
    */
    public Date getSystemDate() {
           try {
               Calendar cal = Calendar.getInstance();
               java.util.Date date = cal.getTime();
               DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
               String currentDate = formatter.format(date);
               return formatter.parse(currentDate);
           } catch (Exception e) {
               return null;
           }
       }
}
