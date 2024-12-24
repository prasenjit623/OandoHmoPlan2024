package com.view.beans;

import com.view.uiutils.ADFUtils;
import com.view.uiutils.JSFUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.imageio.ImageIO;

import javax.xml.bind.DatatypeConverter;

import model.vo.XxhrHmoTrxHdr_EOViewRowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

import view.ApprovalPayload;
import view.ApprovalProcess;

public class AddEditHmoBB {

    private RichPopup dependentPop;
    private RichPopup evidencePop;

    private RichSelectBooleanCheckbox checkBox;
    private RichButton submitButton;
    private RichSelectOneChoice addiCategory;
    private RichButton copyDepButton;
    private RichInputDate dependDob;

    public AddEditHmoBB() {
        super();
    }

    public Date getCurrentSystemDate() {
        try {

            System.out.println("Date method is called ------");
            Calendar now = Calendar.getInstance();
            java.util.Date date = now.getTime();
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String currentDate = formatter.format(date);
            return formatter.parse(currentDate);
        } catch (Exception e) {
            return null;
        }
    }

    public void uploadProfilePicHeader(ValueChangeEvent valueChangeEvent) {
        UploadedFile uploadedFile = (UploadedFile) valueChangeEvent.getNewValue();
        String imageFormat=uploadedFile.getContentType();
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        Row row = hdrVo.getCurrentRow();
        String imageByteArrayInString = convertBlobToBase64Image(createBlobDomain(uploadedFile),imageFormat);
        row.setAttribute("Photo", createBlobDomain(uploadedFile));
        row.setAttribute("PhotoClob", imageByteArrayInString);
        ADFUtils.findOperation("Commit").execute();
        this.refresh();

    }
    private String convertBlobToBase64Image(BlobDomain blobDomain,String imageFormat) {
        String imageByteArrayInString = null;
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(blobDomain.toByteArray());
        ByteArrayOutputStream byteArrayImageOutputStream = new ByteArrayOutputStream();
        try {
            if("image/png".equals(imageFormat)){
            ImageIO.write(ImageIO.read(imageInputStream), "png", byteArrayImageOutputStream);
            }else if("image/jpg".equals(imageFormat)){
                ImageIO.write(ImageIO.read(imageInputStream), "jpg", byteArrayImageOutputStream);
            }else{
                ImageIO.write(ImageIO.read(imageInputStream), "jpeg", byteArrayImageOutputStream);
            }
            byteArrayImageOutputStream.flush();
            imageByteArrayInString = DatatypeConverter.printBase64Binary(byteArrayImageOutputStream.toByteArray());
        } catch (IOException e) {
        }

        return imageByteArrayInString;
    }

    public void uploadProfileDependent(ValueChangeEvent valueChangeEvent) {
        UploadedFile uploadedFile = (UploadedFile) valueChangeEvent.getNewValue();
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        Row row = hdrVo.getCurrentRow();
        String imageByteArrayInString = convertBlobToBase64(createBlobDomain(uploadedFile));
        row.setAttribute("DepPhoto", createBlobDomain(uploadedFile));
        row.setAttribute("DepPhotoClob", imageByteArrayInString);
        ADFUtils.findOperation("Commit").execute();
        this.refresh();

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

    private String convertBlobToBase64(BlobDomain blobDomain) {
        String imageByteArrayInString = null;
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(blobDomain.toByteArray());
        ByteArrayOutputStream byteArrayImageOutputStream = new ByteArrayOutputStream();
        try {
            
            ImageIO.write(ImageIO.read(imageInputStream), "png", byteArrayImageOutputStream);
            byteArrayImageOutputStream.flush();
            imageByteArrayInString = DatatypeConverter.printBase64Binary(byteArrayImageOutputStream.toByteArray());
        } catch (IOException e) {
        }

        return imageByteArrayInString;
    }

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentView = context.getViewRoot().getViewId();
        ViewHandler vh = context.getApplication().getViewHandler();
        UIViewRoot x = vh.createView(context, currentView);
        x.setViewId(currentView);
        context.setViewRoot(x);
    }

    public void setDependentPop(RichPopup dependentPop) {
        this.dependentPop = dependentPop;
    }

    public RichPopup getDependentPop() {
        return dependentPop;
    }

    public void onClickAddDependent(ActionEvent actionEvent) {
        if (isEvidenceAdded()) {
            if (isDepPhotoAdded()) {
                ADFUtils.findOperation("CreateDependent").execute();
                JSFUtils.showPopup(dependentPop);
            } else {
                JSFUtils.addFacesErrorMessage("Please Check, Dependent Photo Missing !");
            }
        } else {
            JSFUtils.addFacesErrorMessage("Please Check, Evidence of DOB Missing !");
        }
    }

    public void onClickEditDependent(ActionEvent actionEvent) {
        JSFUtils.showPopup(dependentPop);
    }

    public void onClickOkDepPop(ActionEvent actionEvent) {
        dependentPop.hide();
        ADFUtils.findOperation("Commit").execute();
        this.refresh();
    }

    public void onCancelDepPop(ActionEvent actionEvent) {
        Object mode = ADFContext.getCurrent()
                                .getPageFlowScope()
                                .get("ADD_EDIT_MODE");
        if (mode != null && "ADD".equals(mode)) {
            ADFUtils.findOperation("Delete").execute();
        }
        dependentPop.hide();
        this.refresh();
    }

    public void setEvidencePop(RichPopup evidencePop) {
        this.evidencePop = evidencePop;
    }

    public RichPopup getEvidencePop() {
        return evidencePop;
    }

    public void onOkEvidencePop(ActionEvent actionEvent) {
        evidencePop.hide();
    }

    public String setDependentCondition(String hmoOption) {
        String path = "";
        if ("Employee Only".equals(hmoOption)) {
            ADFContext.getCurrent()
                      .getPageFlowScope()
                      .put("HMO_OPTION", "HIDE");
            path = "toReviewFromHdr";
        } else {
            ADFContext.getCurrent()
                      .getPageFlowScope()
                      .put("HMO_OPTION", "SHOW");
            path = "toDependent";
        }
        return path;
    }

    /**Method to download file from actual path
     * @param facesContext
     * @param outputStream
     */
    public void onDownloadEvidence(FacesContext facesContext, OutputStream outputStream) throws IOException {
        ViewObject hdrVo = ADFUtils.findIterator("SelfAttachment_EOViewIterator").getViewObject();
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

    /**Method to Upload Multiple Files to DB ,called on ValueChangeEvent of inputFile
     * @param vce
     */
    public void onUploadFileVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            UploadedFile myfile = (UploadedFile) vce.getNewValue();
            if (myfile != null) {
                ViewObject vo = ADFUtils.findIterator("SelfAttachment_EOViewIterator").getViewObject();
                Row curRow = vo.getCurrentRow();
                String filename = myfile.getFilename();
                String ContentType = myfile.getContentType();

                curRow.setAttribute("AttachmentName", filename);
                //curRow.setAttribute("AttachmentType", ContentType);
                if (ContentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    curRow.setAttribute("AttachmentType", "application/docx");
                } else if (ContentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    curRow.setAttribute("AttachmentType", "application/xlsx");

                } else {

                    curRow.setAttribute("AttachmentType", ContentType);
                   
                    //ADFUtils.findOperation("Commit").execute();
                }
                
                curRow.setAttribute("Attachment", createBlobDomain(myfile));
                curRow.setAttribute("SourceDocument", "SELF");
                System.out.println("Get source document Id -- " + curRow.getAttribute("SourceDocumentId"));
                //  curRow.setAttribute("Attachment", createBlobDomain(myfile));


            }
        }
    }

    public String cancelActionHdr() {
        ADFUtils.findOperation("Rollback").execute();

        ViewObject HdrVO = com.view
                              .utils
                              .ADFUtils
                              .findIterator("XxhrHmoTrxHdr_EOViewIterator")
                              .getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        return "toSearchFromHdr";
    }

    public String cancelActionDep() {
        ADFUtils.findOperation("Rollback").execute();
        return "toSearchFromDep";
    }

    public String cancelActionReview() {
        ADFUtils.findOperation("Rollback").execute();
        return "backToSearch";
    }

    public String actionSaveAsDraft() {
        ViewObject HdrVO = com.view
                              .utils
                              .ADFUtils
                              .findIterator("SearchHmoTrxHdr_ROVOIterator")
                              .getViewObject();
        HdrVO.applyViewCriteria(null);
        HdrVO.executeQuery();
        return "backToSearch";
    }

    public String onSubmitHMOReq() {
        String flag = "";
        boolean value = false;
        ViewObject searchVO =
            ADFUtils.getApplicationModuleForDataControl("Oando_AMDataControl").findViewObject("XxhrHmoTrxHdr_ROVO1");
        DCIteratorBinding xxhrHmoHdrIter = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator");
        XxhrHmoTrxHdr_EOViewRowImpl xxhrHmoHdrRow = (XxhrHmoTrxHdr_EOViewRowImpl) xxhrHmoHdrIter.getCurrentRow();
        if (xxhrHmoHdrRow != null) {
            flag = xxhrHmoHdrRow.getHmoAcceptanceFlag();
            System.out.println("Flag-- " + flag);
            //            System.out.println("Hmo trans Hdr ID -- " + xxhrHmoHdrRow.getHmoTrxHdrId());

            if ("N".equalsIgnoreCase(flag)) {
                System.out.println("Inside submit method if loop");
                checkBox.setDisabled(false);
                submitButton.setDisabled(false);
                xxhrHmoHdrRow.setHmoAcceptanceFlag(flag);
                JSFUtils.addFacesErrorMessage("Please select Terms and Conditions !!");
                // AdfFacesContext.getCurrentInstance().addPartialTarget(submitButton);
            } else {

                xxhrHmoHdrRow.setHmoAcceptanceFlag(flag);
                xxhrHmoHdrRow.setAttribute("HmoAcceptanceFlag", "Y");
                xxhrHmoHdrRow.setAttribute("ApprovalStatus", "SUBMITTED FOR APPROVAL");
                String xmlData = null;
                String[] a = null;
                String hmoSubmitWSDL = null;
                hmoSubmitWSDL = ApprovalPayload.HMO_SUBMIT_WSDL;

                ViewObject HdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
                String p_EmployeeName =
                    HdrVo.getCurrentRow().getAttribute("EmpName_Trans") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                .getAttribute("EmpName_Trans")
                                                                                                                .toString();
                String p_EmployeeNumber =
                    HdrVo.getCurrentRow().getAttribute("EmpNumber_Trans") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                    .getAttribute("EmpNumber_Trans")
                                                                                                                    .toString();
                String p_Email = HdrVo.getCurrentRow().getAttribute("Email_Trans") == null ? "" : HdrVo.getCurrentRow()
                                                                                                       .getAttribute("Email_Trans")
                                                                                                       .toString();
                String p_BusinessGroup =
                    HdrVo.getCurrentRow().getAttribute("BusinessUnit_Trans") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                      .getAttribute("BusinessUnit_Trans")
                                                                                                                      .toString();
                String p_ReqNo = HdrVo.getCurrentRow().getAttribute("HmoRequestNo") == null ? "" : HdrVo.getCurrentRow()
                                                                                                        .getAttribute("HmoRequestNo")
                                                                                                        .toString();

                String p_ReqDate = HdrVo.getCurrentRow().getAttribute("HmoRequestDate") == null ? "" : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("HmoRequestDate")
                                                                                                            .toString();
                String p_PersonId = HdrVo.getCurrentRow().getAttribute("PersonId") == null ? "" : HdrVo.getCurrentRow()
                                                                                                       .getAttribute("PersonId")
                                                                                                       .toString();


                String p_Title = HdrVo.getCurrentRow().getAttribute("Trans_Title") == null ? "" : HdrVo.getCurrentRow()
                                                                                                       .getAttribute("Trans_Title")
                                                                                                       .toString();
                String p_FName = HdrVo.getCurrentRow().getAttribute("Trans_FirstName") == null ? "" : HdrVo.getCurrentRow()
                                                                                                           .getAttribute("Trans_FirstName")
                                                                                                           .toString();

                String p_LName = HdrVo.getCurrentRow().getAttribute("Trans_LastName") == null ? "" : HdrVo.getCurrentRow()
                                                                                                          .getAttribute("Trans_LastName")
                                                                                                          .toString();
                String p_Entity = HdrVo.getCurrentRow().getAttribute("Trans_Entity") == null ? "" : HdrVo.getCurrentRow()
                                                                                                         .getAttribute("Trans_Entity")
                                                                                                         .toString();
                String p_DepName = HdrVo.getCurrentRow().getAttribute("DeptName_Trans") == null ? "" : HdrVo.getCurrentRow()
                                                                                                            .getAttribute("DeptName_Trans")
                                                                                                            .toString();
                String p_Gender = HdrVo.getCurrentRow().getAttribute("Gender") == null ? "" : HdrVo.getCurrentRow()
                                                                                                   .getAttribute("Gender")
                                                                                                   .toString();
                String p_AlternateEmail =
                    HdrVo.getCurrentRow().getAttribute("AlternateEmail") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                   .getAttribute("AlternateEmail")
                                                                                                                   .toString();
                String p_ResiAddress =
                    HdrVo.getCurrentRow().getAttribute("ResiAddress") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                             .getAttribute("ResiAddress")
                                                                                                             .toString();
                String p_PlanName =
                    HdrVo.getCurrentRow().getAttribute("Trans_PlanName") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                             .getAttribute("Trans_PlanName")
                                                                                                             .toString();

                String p_HmoOption = HdrVo.getCurrentRow().getAttribute("HmoType") == null ? "" : HdrVo.getCurrentRow()
                                                                                                       .getAttribute("HmoType")
                                                                                                       .toString();

                String p_Category = HdrVo.getCurrentRow().getAttribute("HmoCategory") == null ? "" : HdrVo.getCurrentRow()
                                                                                                          .getAttribute("HmoCategory")
                                                                                                          .toString();


                String p_DependentOption =
                    HdrVo.getCurrentRow().getAttribute("HmoOption") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                               .getAttribute("HmoOption")
                                                                                                               .toString();


                String p_StateOfRes =
                    HdrVo.getCurrentRow().getAttribute("StateOfResidence") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                 .getAttribute("StateOfResidence")
                                                                                                                 .toString();


                String p_Hospital = HdrVo.getCurrentRow().getAttribute("Hospital") == null ? "" : HdrVo.getCurrentRow()
                                                                                                       .getAttribute("Hospital")
                                                                                                       .toString();


                String p_ApprovalStatus =
                    HdrVo.getCurrentRow().getAttribute("ApprovalStatus") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                   .getAttribute("ApprovalStatus")
                                                                                                                   .toString();

                String p_ApprovalComments =
                    HdrVo.getCurrentRow().getAttribute("ApproverComments") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                       .getAttribute("ApproverComments")
                                                                                                                       .toString();

                String p_HMOPlanTotal =
                    HdrVo.getCurrentRow().getAttribute("HmoPlanTotal") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                               .getAttribute("HmoPlanTotal")
                                                                                                               .toString();

                String p_HMOPlanDeduction =
                    HdrVo.getCurrentRow().getAttribute("HmoPlanDeduction") == null ? "" :
                    HdrVo.getCurrentRow()
                                                                                                                       .getAttribute("HmoPlanDeduction")
                                                                                                                       .toString();


                xmlData =
                    ApprovalPayload.businessTypeXMLData(p_EmployeeName, p_EmployeeNumber, p_Email, p_BusinessGroup,
                                                        p_ReqNo, p_ReqDate, p_PersonId, p_Title, p_FName, p_LName,
                                                        p_Entity, p_DepName, p_Gender, p_AlternateEmail, p_ResiAddress,
                                                        p_PlanName, p_HmoOption, p_Category, p_DependentOption,
                                                        p_StateOfRes, p_Hospital, p_ApprovalStatus, p_ApprovalComments,
                                                        p_HMOPlanTotal, p_HMOPlanDeduction);
                System.err.println("xmlData =>" + xmlData);
                a = ApprovalProcess.invokeWsdl(xmlData, hmoSubmitWSDL, ApprovalPayload.HMO_SUBMIT_METHOD);
                if (a[0] != null && a[0].equals("True")) {

                    ADFUtils.findOperation("Commit").execute();
                    JSFUtils.addFacesInformationMessage("Submitted Successfully !!");
                    value = true;
                } else {
                    JSFUtils.addFacesInformationMessage("Error");
                    xxhrHmoHdrRow.setAttribute("ApprovalStatus", "DRAFT");
                    // value = true;

                }


            }
        }

        if (value) {
            //            System.out.println("Inside value loop");
            //            searchVO.applyViewCriteria(null);
            //            searchVO.executeQuery();
            ViewObject HdrVO = com.view
                                  .utils
                                  .ADFUtils
                                  .findIterator("SearchHmoTrxHdr_ROVOIterator")
                                  .getViewObject();
            HdrVO.applyViewCriteria(null);
            HdrVO.executeQuery();
            return "backToSearch";
        } else {
            System.out.println("Inside value else  loop");
            return null;

        }

    }

    public void onOkEvidPop(ActionEvent actionEvent) {
        ADFUtils.findOperation("Commit").execute();
        evidencePop.hide();
        this.refresh();
    }

    public void onChangeDepDOB(ValueChangeEvent valueChangeEvent) throws ParseException {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String ageDescription = null;
        boolean check = false;
        if (valueChangeEvent.getNewValue().toString() != null) {
            String depDob = valueChangeEvent.getNewValue().toString();
            ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
            Row lineRow = depVo.getCurrentRow();
            String depType =
                lineRow.getAttribute("DependentType") == null ? "null" :
                lineRow.getAttribute("DependentType").toString();
            if (checkChildAge(depDob, depType)) {
                double p_amount = 0;
                OperationBinding op = ADFUtils.findOperation("getHMOCost");
                ArrayList list = (ArrayList) op.execute();
                if (list != null) {
                    p_amount = Double.parseDouble(list.get(0).toString());

                    if (p_amount > 0) {
                        System.err.println("covered");
                        lineRow.setAttribute("DependentCost", p_amount);

                    } else {
                        check = true;
                        System.err.println("not covered");
                        JSFUtils.addFacesErrorMessage("HMO selected does not cover the age range");
                        lineRow.setAttribute("DependentCost", 0);
                        lineRow.setAttribute("DepDob", null);
                        lineRow.setAttribute("DepAgeCategory", null);
                        lineRow.setAttribute("DepAgeCategory", "");
                        return;
                    }
                }
                String p_age = null;
                if (!check) {
                    OperationBinding oP = ADFUtils.findOperation("getAgeCategory");
                    ArrayList lists = (ArrayList) oP.execute();
                    if (lists != null) {
                        p_age = lists.get(0).toString();
                        Row[] orgRows;
                        if (p_age != null) {
                            orgRows = ADFUtils.findIterator("XxfndLookupValues_V_ROVOIterator")
                                              .getViewObject()
                                              .getFilteredRows("LookupValueCode", p_age);
                            if (orgRows.length > 0) {
                                ageDescription = orgRows[0].getAttribute("LookupValueNameDisp").toString();
                            }
                            lineRow.setAttribute("DepAgeCategory", ageDescription);
                        } else {
                            lineRow.setAttribute("DepAgeCategory", "");
                        }
                    }
                }
            } else {
                lineRow.setAttribute("DepAgeCategory", "");
                lineRow.setAttribute("DepDob", null);
                lineRow.setAttribute("DependentCost", 0);
            }
        }
    }

    private boolean isEvidenceAdded() {
        ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        RowSetIterator itr = depVo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            String count = r.getAttribute("EvidenceCount") != null ? r.getAttribute("EvidenceCount").toString() : "0";
            if ("0".equals(count)) {
                return false;
            }
        }
        itr.closeRowSetIterator();
        return true;
    }

    public String depNextAction() throws ParseException {
        ViewObject dtlVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        String copy = hdrVo.getCurrentRow().getAttribute("CopyDep") == null ? "null" : hdrVo.getCurrentRow()
                                                                                            .getAttribute("CopyDep")
                                                                                            .toString();
        if (copy.equals("Y")) {

            String hmoOption = hdrVo.getCurrentRow()
                                    .getAttribute("HmoOption")
                                    .toString();
            System.err.println("HmoOption" + hmoOption);
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int e = 0;
            int f = 0;
            int g = 0;
            int h = 0;
            int i = 0;
            //   String hmoOption = ADFUtils.getBoundAttributeValue("HmoOption").toString();
            RowSetIterator uopLine = dtlVo.createRowSetIterator(null);
            //uopLine.reset();
            System.err.println(uopLine.getRowCount());
            while (uopLine.hasNext()) {
                System.err.println("inside while");
                Row r = uopLine.next();
                String dep =
                    r.getAttribute("DependentType") == null ? "null" : r.getAttribute("DependentType").toString();
                String dob = r.getAttribute("DepDob") == null ? "null" : r.getAttribute("DepDob").toString();

                if ("Employee +1 Dependant Child".equals(hmoOption)) {
                    if (dep.equals("Spouse") || dep.equals("Sibling") || dep.equals("Grand Parent") ||
                        dep.equals("Parent") || dep.equals("Domestic Staff")) {
                        d++;
                        System.err.println("Came inside spouse");

                    }

                    if (dep.equals("Others")) {
                        e++;
                        System.err.println("Came inside other");

                    }
                    if (dep.equals("Child")) {
                        c++;


                    }
                } else if ("Employee + Dependant Children".equals(hmoOption)) {

                    if (dep.equals("Spouse") || dep.equals("Sibling") || dep.equals("Grand Parent") ||
                        dep.equals("Parent") || dep.equals("Domestic Staff")) {
                        a++;
                        System.err.println("Came inside spouse");

                    }

                    if (dep.equals("Others")) {
                        b++;
                        System.err.println("Came inside other");

                    }

                } else if ("Employee +Spouse+ Children".equals(hmoOption)) {
                    if (dep.equals("Spouse") || dep.equals("Sibling") || dep.equals("Grand Parent") ||
                        dep.equals("Parent") || dep.equals("Domestic Staff")) {
                        f++;
                        System.err.println("Came inside spouse");

                    }

                    if (dep.equals("Others")) {
                        g++;
                        System.err.println("Came inside other");

                    }

                } else if ("Employee + Spouse".equals(hmoOption)) {
                    if (dep.equals("Spouse") || dep.equals("Sibling") || dep.equals("Grand Parent") ||
                        dep.equals("Parent") || dep.equals("Domestic Staff") || dep.equals("Child")) {
                        h++;
                        System.err.println("Came inside spouse");

                    }

                    if (dep.equals("Others")) {
                        i++;
                        System.err.println("Came inside other");

                    }

                }
                if (dep.equals("Child")) {


                    ViewObject dependVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
                    if ("Child".equals(dep)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                        LocalDate locDob = LocalDate.parse(dob, formatter);
                        LocalDate today = LocalDate.now();
                        Period p = Period.between(locDob, today);
                        if (p.getYears() == 24) {
                            if (p.getMonths() == 0 && p.getDays() == 0) {

                            } else {
                                //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                                //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                                JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");

                                return "";
                            }
                        } else {
                            if (p.getYears() < 24) {

                            } else {
                                //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                                //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                                JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                                return "";
                            }
                        }
                    }


                }

            }


            if (a > 0 || b > 0) {

                JSFUtils.addFacesErrorMessage("Please delete all the dependent type except child , to proceed further ");
                return "";
            }
            if (c > 1 || d > 0 || e > 0) {

                JSFUtils.addFacesErrorMessage("Please delete all the dependent type  except only one child , to proceed further");
                return "";
            }
            if (f > 1 || g > 0) {

                JSFUtils.addFacesErrorMessage("Please delete all the dependent type except child and only one spouse , to proceed further ");
                return "";
            }
            if (h > 1 || i > 0) {

                JSFUtils.addFacesErrorMessage("Please delete all the dependent type except only one spouse , to proceed further ");
                return "";
            }


        }
        RowSetIterator uopLine1 = dtlVo.createRowSetIterator(null);
        while (uopLine1.hasNext()) {
            System.err.println("inside while");
            Row r = uopLine1.next();
            String dep1 = r.getAttribute("DependentType") == null ? "null" : r.getAttribute("DependentType").toString();
            String dob1 = r.getAttribute("DepDob") == null ? "null" : r.getAttribute("DepDob").toString();

            if (dep1.equals("Child")) {
                ViewObject dependVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
                if ("Child".equals(dep1)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                    LocalDate locDob = LocalDate.parse(dob1, formatter);
                    LocalDate today = LocalDate.now();
                    Period p = Period.between(locDob, today);
                    if (p.getYears() == 24) {
                        if (p.getMonths() == 0 && p.getDays() == 0) {

                        } else {
                            //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                            //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                            JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");

                            return "";
                        }
                    } else {
                        if (p.getYears() < 24) {

                        } else {
                            //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                            //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                            JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                            return "";
                        }
                    }
                }


            }
        }
        ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        BigDecimal totAmt =
            (depVo.getCurrentRow().getAttribute("HmoPlanTotal") == null ? new BigDecimal(0) :
             (BigDecimal) depVo.getCurrentRow().getAttribute("HmoPlanTotal"));
        depVo.getCurrentRow().setAttribute("HmoPlanTotal", totAmt);
        BigDecimal deductionAmt =
            (depVo.getCurrentRow().getAttribute("HmoPlanDeduction") == null ? new BigDecimal(0) :
             (BigDecimal) depVo.getCurrentRow().getAttribute("HmoPlanDeduction"));
        depVo.getCurrentRow().setAttribute("HmoPlanDeduction", deductionAmt);

        ADFUtils.findOperation("Commit").execute();
        if (isEvidenceAdded()) {
            if (isDepPhotoAdded()) {
                if (isAllDependentAdded()) {
                    ADFUtils.findOperation("Commit").execute();
                    return "toReview";
                } else {
                    return "";
                }
            } else {
                JSFUtils.addFacesErrorMessage("Please Check, Dependent Photo Missing !");
                return "";
            }
        } else {
            JSFUtils.addFacesErrorMessage("Please Check, Evidence of DOB Missing !");
            return "";
        }
    }

    private boolean isDepPhotoAdded() {
        ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        RowSetIterator itr = depVo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            String count = r.getAttribute("DepPhoto") != null ? r.getAttribute("DepPhoto").toString() : "";
            if ("".equals(count)) {
                return false;
            }
        }
        itr.closeRowSetIterator();
        return true;
    }

    public String onNextHdr() {
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        Row r = hdrVo.getCurrentRow();
        String hmoTyp = r.getAttribute("HmoType") != null ? r.getAttribute("HmoType").toString() : "";
        String hmoOption = r.getAttribute("HmoOption") != null ? r.getAttribute("HmoOption").toString() : "";
        String photo = r.getAttribute("Photo") != null ? r.getAttribute("Photo").toString() : "";
        String hmoCategory = r.getAttribute("HmoCategory") != null ? r.getAttribute("HmoCategory").toString() : "";
        String stateOfRes =
            r.getAttribute("StateOfResidence") != null ? r.getAttribute("StateOfResidence").toString() : "";
        String statusValue =
            r.getAttribute("ApprovalStatus") != null ? r.getAttribute("ApprovalStatus").toString() : "";
        if ("".equals(hmoCategory)) {
            JSFUtils.addFacesErrorMessage("Please Check, HMO Category Required !");
            return "";
        }
        if ("".equals(hmoOption)) {
            JSFUtils.addFacesErrorMessage("Please Check, HMO Option Required !");
            return "";
        }
        if ("AXAMANSARD HEALTH".equals(hmoTyp) || "RELIANCE HEALTH".equals(hmoTyp) || "LIBERTY BLUE".equals(hmoTyp)) {
            if ("".equals(stateOfRes)) {
                JSFUtils.addFacesErrorMessage("Please Check, State Of Residence Required!");
                return "";
            }
        }
        if ("".equals(photo)) {
            JSFUtils.addFacesErrorMessage("Please Check, Dependent Photo Missing !");
            return "";
        } else {
            String retVal = this.setDependentCondition(hmoOption);
            if ("DRAFT".equals(statusValue)) {
                this.setEmployeeHMOCost();
                BigDecimal totAmt =
                    (hdrVo.getCurrentRow().getAttribute("HmoPlanTotal") == null ? new BigDecimal(0) :
                     (BigDecimal) hdrVo.getCurrentRow().getAttribute("HmoPlanTotal"));
                hdrVo.getCurrentRow().setAttribute("HmoPlanTotal", totAmt);
                BigDecimal deductionAmt =
                    (hdrVo.getCurrentRow().getAttribute("HmoPlanDeduction") == null ? new BigDecimal(0) :
                     (BigDecimal) hdrVo.getCurrentRow().getAttribute("HmoPlanDeduction"));
                hdrVo.getCurrentRow().setAttribute("HmoPlanDeduction", deductionAmt);
                ADFUtils.findOperation("Commit").execute();
            }
            return retVal;
        }
    }

    public void checkVal() {
        ViewObject dtlVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        String hmoOption = hdrVo.getCurrentRow()
                                .getAttribute("HmoOption")
                                .toString();
        System.err.println("HmoOption" + hmoOption);
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;
        int i = 0;
        //   String hmoOption = ADFUtils.getBoundAttributeValue("HmoOption").toString();
        RowSetIterator uopLine = dtlVo.createRowSetIterator(null);
        //uopLine.reset();
        System.err.println(uopLine.getRowCount());
        while (uopLine.hasNext()) {
            System.err.println("inside while");
            Row r = uopLine.next();
            String dep = r.getAttribute("DependentType") == null ? "null" : r.getAttribute("DependentType").toString();
            if ("Employee +1 Dependant Child".equals(hmoOption)) {
                if (dep.equals("Spouse")) {
                    d++;
                    System.err.println("Came inside spouse");

                }

                if (dep.equals("Others")) {
                    e++;
                    System.err.println("Came inside other");

                }
                if (dep.equals("Child")) {
                    c++;

                }
            } else if ("Employee + Dependant Children".equals(hmoOption)) {

                if (dep.equals("Spouse")) {
                    a++;
                    System.err.println("Came inside spouse");

                }

                if (dep.equals("Others")) {
                    b++;
                    System.err.println("Came inside other");

                }

            } else if ("Employee +Spouse+ Children".equals(hmoOption)) {
                if (dep.equals("Spouse")) {
                    f++;
                    System.err.println("Came inside spouse");

                }

                if (dep.equals("Others")) {
                    g++;
                    System.err.println("Came inside other");

                }

            } else if ("Employee + Spouse".equals(hmoOption)) {
                if (dep.equals("Spouse")) {
                    h++;
                    System.err.println("Came inside spouse");

                }

                if (dep.equals("Others")) {
                    i++;
                    System.err.println("Came inside other");

                }

            }

        }
        if (a > 0 || b > 0) {

            JSFUtils.addFacesErrorMessage("Please delete all the dependent type except child , to proceed further ");

        }
        if (c > 1 || d > 0 || e > 0) {

            JSFUtils.addFacesErrorMessage("Please delete all the dependent type  except only one child , to proceed further");
        }
        if (f > 1 || g > 0) {

            JSFUtils.addFacesErrorMessage("Please delete all the dependent type except child and only one spouse , to proceed further ");

        }
        if (h > 1 || i > 0) {

            JSFUtils.addFacesErrorMessage("Please delete all the dependent type except only one spouse , to proceed further ");

        }

    }

    public void onChangeDepType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String hmoOption = ADFUtils.getBoundAttributeValue("HmoOption").toString();
        ViewObject dtlVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        Row r = dtlVo.getCurrentRow();
        String dep = r.getAttribute("DependentType") == null ? "null" : r.getAttribute("DependentType").toString();
        System.out.println("Dep value is "+ dep);
        Row[] msDtlRows = dtlVo.getFilteredRows("DependentType", dep);
        if (msDtlRows.length > 1) {
            if (dep.equals("Spouse")) {
                RichSelectOneChoice depType = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc1");
                depType.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(depType);
                JSFUtils.addFacesErrorMessage("Spouse Already Exist !");
            } else {
              
            }
        }
        if ("Employee +1 Dependant Child".equals(hmoOption)) {
            Row[] dtlRows = dtlVo.getFilteredRows("DependentType", "Child");
            if (dtlRows.length > 1) {
                RichSelectOneChoice depType = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc1");
                depType.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(depType);
                JSFUtils.addFacesErrorMessage("Child Already Exist !");
            }
        }
        
        if ("Employee +Spouse+ Children".equals(hmoOption) ){
            System.out.println("Dep value is "+ dep);
            Row[] dtlRows = dtlVo.getFilteredRows("DependentType", "Child");
                if (dtlRows.length > 4) {
                    System.out.println("Inside if loop------");
                    RichSelectOneChoice depType = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc1");
                    depType.setValue(" ");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(depType);
                    JSFUtils.addFacesErrorMessage("Child Already Exist, You Cannot Add more than 4 !");
                }
        }
    }

    public void setEmployeeHMOCost() {
        boolean result = false;
        String p_age = null;
        String ageDescription = null;
        System.err.println("came inside onClickHeaderSave");
        //   ViewObject vodtl = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        ViewObject vo = com.view
                           .utils
                           .ADFUtils
                           .findIterator("XxhrHmoTrxHdr_EOViewIterator")
                           .getViewObject();
        ViewObject linesVo = com.view
                                .utils
                                .ADFUtils
                                .findIterator("XxhrHmoTrxDtl_EOViewIterator")
                                .getViewObject();
        //   String type= vo.getCurrentRow().getAttribute("HmoType").toString();
        //       BigDecimal hmoded= (BigDecimal) vo.getCurrentRow().getAttribute("HmoPlanDeduction");
        oracle.jbo.domain.Number busUnitId =
            (vo.getCurrentRow().getAttribute("BusinessUnitId") == null ? new oracle.jbo.domain.Number(0) :
             (oracle.jbo.domain.Number) vo.getCurrentRow().getAttribute("BusinessUnitId"));
        vo.getCurrentRow().setAttribute("BusinessUnitId", busUnitId);
        //        vo.getCurrentRow().setAttribute("HmoAcceptanceFlag", vo.getCurrentRow().getAttribute("HmoAcceptanceFlag"));
        //        vo.getCurrentRow().setAttribute("AcceptanceFlag_Trans", "N");
        RowSetIterator itr = vo.createRowSetIterator(null);
        System.err.println(itr.getRowCount());
        String depOptions = "";
        String agedepOptions = "";
        String selectedHmoOption = vo.getCurrentRow().getAttribute("HmoOption") == null ? "" : vo.getCurrentRow()
                                                                                                 .getAttribute("HmoOption")
                                                                                                 .toString();
        String selectedHmoCategory = vo.getCurrentRow().getAttribute("HmoCategory") == null ? "" : vo.getCurrentRow()
                                                                                                     .getAttribute("HmoCategory")
                                                                                                     .toString();

        String selectedHmoType = vo.getCurrentRow().getAttribute("HmoType") == null ? "" : vo.getCurrentRow()
                                                                                             .getAttribute("HmoType")
                                                                                             .toString();
        System.out.println("selectedHmoOption 000 ----" + selectedHmoOption);
        System.out.println("selectedHmoCategory 000 ----" + selectedHmoCategory);
        System.out.println("selectedHmoType ---------- " + selectedHmoType);
        if (selectedHmoOption.equals("EMPLOYEE + DEPENDANTS")) {
            depOptions = "Employee + Dependants";
            System.out.println("inside first if-");
        } else if (selectedHmoOption.equals("EMP +SPOUSE_CHILDREN")) {
            depOptions = "Employee +Spouse+ Children";

        } else if (selectedHmoOption.equals("EMP_CHILDREN")) {
            depOptions = "Employee + Dependant Children";
        } else if (selectedHmoOption.equals("EMP_CHILD")) {
            depOptions = "Employee +1 Dependant Child";
        } else if (selectedHmoOption.equals("EMP_SPOUSE")) {
            depOptions = "Employee + Spouse";
        } else if (selectedHmoOption.equals("EMP_ONLY") && !"AXAMANSARD HEALTH".equals(selectedHmoType)) {
            depOptions = "Employee Only";
            System.out.println("Unside if LOOOOOOOOPP");
            // result = true;
        } else if (selectedHmoOption.equals("Employee + Other Dependant") &&
                   ("AXAMANSARD HEALTH".equals(selectedHmoType) || "RELIANCE HEALTH".equals(selectedHmoType) ||
                    "LEADWAY HEALTH PREMIUM".equals(selectedHmoType) ||
                    "LEADWAY HEALTH STANDARD".equals(selectedHmoType))) {
            agedepOptions = "EMP_ONLY";
            selectedHmoCategory = "Traditional Dependent Categorization";
            result = true;
            System.out.println("Unside if looppppppp");
        } else if (selectedHmoOption.equals("Employee Only") &&
                   ("AXAMANSARD HEALTH".equals(selectedHmoType) || "LEADWAY HEALTH PREMIUM".equals(selectedHmoType) ||
                    "LEADWAY HEALTH STANDARD".equals(selectedHmoType))) {
            agedepOptions = "EMP_ONLY";
            result = true;
            System.out.println("Unside ELSE ");
        } else {
            depOptions = selectedHmoOption;
        }
        System.out.println("-depOptions---" + depOptions);
        System.out.println("-agedepOptions---" + agedepOptions);

        oracle.jbo.domain.Number hmo_master_hdr_id =
            vo.getCurrentRow().getAttribute("HmoMasterHdrId") == null ? new oracle.jbo.domain.Number(0) :
            (oracle.jbo.domain.Number) vo.getCurrentRow().getAttribute("HmoMasterHdrId");

        oracle.jbo.domain.Number person_id =

            vo.getCurrentRow().getAttribute("PersonId") == null ? new oracle.jbo.domain.Number(0) :
            (oracle.jbo.domain.Number) vo.getCurrentRow().getAttribute("PersonId");


        System.out.println("hmo_master_hdr_id save--- " + hmo_master_hdr_id + "----- save-----person_id ----- " +
                           person_id);


        String hmo_type = vo.getCurrentRow().getAttribute("HmoType") == null ? " " : vo.getCurrentRow()
                                                                                       .getAttribute("HmoType")
                                                                                       .toString();

        String hmo_category = vo.getCurrentRow().getAttribute("HmoCategory") == null ? " " : vo.getCurrentRow()
                                                                                               .getAttribute("HmoCategory")
                                                                                               .toString();

        String hmo_option = vo.getCurrentRow().getAttribute("HmoOption") == null ? " " : vo.getCurrentRow()
                                                                                           .getAttribute("HmoOption")
                                                                                           .toString();

        String dob = vo.getCurrentRow().getAttribute("Trans_DateofBirth") == null ? "" : vo.getCurrentRow()
                                                                                           .getAttribute("Trans_DateofBirth")
                                                                                           .toString();

        String addi_category = vo.getCurrentRow().getAttribute("AddiCategory") == null ? " " : vo.getCurrentRow()
                                                                                                 .getAttribute("AddiCategory")
                                                                                                 .toString();

        System.out.println("AddiCategory----- " + addi_category);
        System.out.println("hmo_option----- " + hmo_option);

        System.out.println("Dob value is -" + dob);
        if (!"AXAMANSARD HEALTH".equals(hmo_type) || !"RELIANCE HEALTH".equals(hmo_type) ||
            "Plus Africa".equals(hmo_category) || "Global Elite".equals(hmo_category) ||
            "Age-based Categorization".equals(hmo_category)) {
            OperationBinding op = com.view
                                     .utils
                                     .ADFUtils
                                     .findOperation("getEmpHmoCost");
            System.out.println("--------1----------------");

            op.getParamsMap().put("p_hmo_master_hdr_id", hmo_master_hdr_id);
            op.getParamsMap().put("p_hmo_type", hmo_type);
            op.getParamsMap().put("p_person_id", person_id);
            if (result) {
                System.out.println("Unside if looppppppp result --------");
                System.out.println("selectedHmoCategory--------- " + selectedHmoCategory);
                System.out.println("agedepOptions  result ------- " + agedepOptions);
                op.getParamsMap().put("p_hmo_category", selectedHmoCategory);
                op.getParamsMap().put("p_hmo_option", agedepOptions);
            } else {
                System.out.println("Unside else looppppppp result --------");
                op.getParamsMap().put("p_hmo_category", hmo_category);
                op.getParamsMap().put("p_hmo_option", hmo_option);
            }
            op.getParamsMap().put("p_emp_dob", dob);
            op.getParamsMap().put("p_addi_category", addi_category);
            System.out.println("----------2---------------");

            ArrayList list = (ArrayList) op.execute();


            System.out.println("----------4---------------");
            double p_amount = 0;
            if (list != null) {
                System.out.println("---5---");
                p_amount = Double.parseDouble(list.get(0).toString());
                System.out.println(p_amount + "---------------p_amount");
                if (p_amount >= 0) {
                    System.out.println("Positive----");
                    vo.getCurrentRow().setAttribute("Trans_EmpCost", p_amount);
                    vo.getCurrentRow().setAttribute("HmoEmpCost", p_amount);
                }

                // }
            }
        }

        // To get Employee Age

        OperationBinding op = com.view
                                 .utils
                                 .ADFUtils
                                 .findOperation("getEmpAgeCategory");
        op.getParamsMap().put("p_hmo_master_hdr_id", hmo_master_hdr_id);
        op.getParamsMap().put("p_hmo_type", hmo_type);
        op.getParamsMap().put("p_emp_dob", dob);
        ArrayList listss = (ArrayList) op.execute();
        if (listss != null) {
            System.out.println("---5---");
            p_age = listss.get(0).toString();
            System.out.println(p_age + "---------------p_age");
            Row[] orgRows;
            if (p_age != null) {
                orgRows = com.view
                             .utils
                             .ADFUtils
                             .findIterator("XxfndLookupValues_V_ROVOIterator")
                             .getViewObject()
                             .getFilteredRows("LookupValueCode", p_age);
                if (orgRows.length > 0) {
                    ageDescription = orgRows[0].getAttribute("LookupValueNameDisp").toString();
                    System.out.println("---------------ageDescription------" + ageDescription);
                    if ("Other Dependant Age Between 0-21".equals(ageDescription)) {
                        ageDescription = "Age Between 0 to 21";
                    } else if ("Other Dependant Age Between 22-39".equals(ageDescription)) {
                        ageDescription = "Age Between 22 to 39";
                    } else if ("Other Dependant Age Between 40-60".equals(ageDescription)) {
                        ageDescription = "Age Between 40 to 60";
                    } else if ("Other Dependant Age Between 61-65".equals(ageDescription)) {
                        ageDescription = "Age Between 61 to 65";
                    } else if ("Other Dependant Age Between 66-70".equals(ageDescription)) {
                        ageDescription = "Age Between 66 to 70";
                    } else if ("Other Dependant Age Between 71-75".equals(ageDescription)) {
                        ageDescription = "Age Between 71 to 75";
                    } else if ("Other Dependant Age Between 76-80".equals(ageDescription)) {
                        ageDescription = "Age Between 76 to 80";
                    } else if ("Other Dependant Age Between 81-85".equals(ageDescription)) {
                        ageDescription = "Age Between 81 to 85";
                    } else if ("Other Dependant Age Between 86-90".equals(ageDescription)) {
                        ageDescription = "Age Between 86 to 90";
                    } else if ("Other Dependant Age Between 91-95".equals(ageDescription)) {
                        ageDescription = "Age Between 91 to 95";
                    } else {
                        ageDescription = orgRows[0].getAttribute("LookupValueNameDisp").toString();
                    }
                }
                vo.getCurrentRow().setAttribute("Trans_EmpAge", ageDescription);
            } else {
                vo.getCurrentRow().setAttribute("Trans_EmpAge", "");
            }
        }


        ViewObject LineVo = com.view
                               .utils
                               .ADFUtils
                               .getApplicationModuleForDataControl("Oando_AMDataControl")
                               .findViewObject("MaximumEligibleCost_ROVO");
        ViewCriteria viewCriteria = LineVo.createViewCriteria();
        ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();
        viewCriteriaRow.setAttribute("HmoOptions", hmo_type);
        viewCriteriaRow.setAttribute("HmoCategory", hmo_category);
        viewCriteriaRow.setAttribute("DependentOptions", depOptions);
        viewCriteria.addRow(viewCriteriaRow);
        LineVo.applyViewCriteria(viewCriteria);
        LineVo.executeQuery();

        System.out.println(" LineVo---query  ----" + LineVo.getQuery());
        System.out.println(" LineVo---getEstimatedRowCount  ----" + LineVo.getEstimatedRowCount());
        if (LineVo.getEstimatedRowCount() > 0) {
            Row row = LineVo.first();
            BigDecimal orgObj = (BigDecimal) row.getAttribute("HmoCategoryId");
            System.out.println("orgObj----- " + orgObj);
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("categoryId", orgObj);
        }

        while (itr.hasNext()) {
            Row r = itr.next();
            BigDecimal totAmt =
                (r.getAttribute("HmoPlanTotal") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("HmoPlanTotal"));
            System.out.println("Current Value---- " + vo.getCurrentRow().getAttribute("HmoPlanTotal"));
            r.setAttribute("HmoPlanTotal", totAmt);

            BigDecimal deductionAmt =
                (r.getAttribute("HmoPlanDeduction") == null ? new BigDecimal(0) :
                 (BigDecimal) r.getAttribute("HmoPlanDeduction"));
            System.out.println("Current Value---- " + vo.getCurrentRow().getAttribute("HmoPlanDeduction"));
            r.setAttribute("HmoPlanDeduction", deductionAmt);

        }
        //            OperationBinding duplOb = executeOperation("Commit");
        //            duplOb.execute();
        //  ViewObject vo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject
        //          ADFContext.getCurrent().getPageFlowScope().put("person_id",
        //                                                                vo.getCurrentRow().getAttribute("PersonId"));
        //          System.err.println("PersonId"+vo.getCurrentRow().getAttribute("PersonId"));
        //
        //                  ADFContext.getCurrent().getPageFlowScope().put("hdr_id",
        //                                                                 vo.getCurrentRow().getAttribute("HmoMasterHdrId"));
        //            System.err.println("HmoMasterHdrId"+vo.getCurrentRow().getAttribute("HmoMasterHdrId"));
        String retStr = null;
        System.out.println("Value flag is ------- " + vo.getCurrentRow().getAttribute("HmoCopyDepFlag"));
        //  String copyFlag ="N";
        //        if (vo.getCurrentRow().getAttribute("HmoCopyDepFlag") != null &&
        //            "N".equalsIgnoreCase((String) vo.getCurrentRow().getAttribute("HmoCopyDepFlag"))) {
        //            System.out.println("Inside if loop of flag");
        //            Object obj = com.view.utils.ADFUtils.findOperation("dependent_details").execute();
        //            if (obj != null) {
        //                retStr = obj.toString();
        //            }
        //
        //
        //            System.out.println("retStr ------------ " + retStr);
        //            if (retStr.equals("[E, No Data Found for prev Header id ]")) {
        //
        //                if (linesVo.getEstimatedRowCount() == 0) {
        //                    System.err.println("Came in createinsert");
        ////                    OperationBinding dulOb = executeOperation("CreateWithParams");
        ////                    dulOb.execute();
        //                }
        //                System.err.println("linesVo.getEstimatedRowCount()--- " + linesVo.getEstimatedRowCount());
        //            }
        //
        //        }
        //            OperationBinding oP = ADFUtils.findOperation("dependent_details");
        //            oP.execute();
        ADFUtils.findOperation("Commit").execute();
        OperationBinding dulOb = executeOperation("Commit");
        dulOb.execute();
        //            vo.getCurrentRow().getAttribute("PersonId");
        //            vo.getCurrentRow().getAttribute("HmoMasterHdrId");
        com.view
           .utils
           .JSFUtils
           .addFacesInformationMessage("Saved Successfully !!");
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;
    }

    /*****Generic Method to Get BindingContainer**/
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setCheckBox(RichSelectBooleanCheckbox checkBox) {
        this.checkBox = checkBox;
    }

    public RichSelectBooleanCheckbox getCheckBox() {
        return checkBox;
    }

    public void setSubmitButton(RichButton submitButton) {
        this.submitButton = submitButton;
    }

    public RichButton getSubmitButton() {
        return submitButton;
    }

    public void onChangeHmoCategory(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.refresh();
        AdfFacesContext.getCurrentInstance().addPartialTarget(addiCategory);
    }

    public void onchangeDepOption(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.refresh();
        AdfFacesContext.getCurrentInstance().addPartialTarget(addiCategory);
    }

    public void onChangeHmoOption(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        this.refresh();

    }

    public void setAddiCategory(RichSelectOneChoice addiCategory) {
        this.addiCategory = addiCategory;
    }

    public RichSelectOneChoice getAddiCategory() {
        return addiCategory;
    }

    private boolean checkChildAge(String depDob, String depType) throws ParseException {
        ViewObject dependVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        if ("Child".equals(depType)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDate locDob = LocalDate.parse(depDob, formatter);
            LocalDate today = LocalDate.now();
            Period p = Period.between(locDob, today);
            if (p.getYears() == 24) {
                if (p.getMonths() == 0 && p.getDays() == 0) {
                    return true;
                } else {
                    dependVo.getCurrentRow().setAttribute("DepDob", null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                    JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");

                    return false;
                }
            } else {
                if (p.getYears() < 24) {
                    return true;
                } else {
                    dependVo.getCurrentRow().setAttribute("DepDob", null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                    JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                    return false;
                }
            }
        } else {
            return true;
        }
    }


    private boolean checkChildAgeNext(String depDob, String depType) throws ParseException {
        ViewObject dependVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        if ("Child".equals(depType)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDate locDob = LocalDate.parse(depDob, formatter);
            LocalDate today = LocalDate.now();
            Period p = Period.between(locDob, today);
            if (p.getYears() == 24) {
                if (p.getMonths() == 0 && p.getDays() == 0) {
                    return true;
                } else {
                    //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                    //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                    JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");

                    return false;
                }
            } else {
                if (p.getYears() < 24) {
                    return true;
                } else {
                    //                    dependVo.getCurrentRow().setAttribute("DepDob",null);
                    //                    AdfFacesContext.getCurrentInstance().addPartialTarget(dependDob);
                    JSFUtils.addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    private boolean isAllDependentAdded() {
        String hmoOption = ADFUtils.getBoundAttributeValue("HmoOption").toString();
        ArrayList<String> addedDepType = new ArrayList<String>();
        ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        RowSetIterator itr = depVo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row r = itr.next();
            String depType = r.getAttribute("DependentType") != null ? r.getAttribute("DependentType").toString() : "";
            addedDepType.add(depType);
        }
        System.err.println("hmoOption-->" + hmoOption);
        System.err.println("addedDepType-->" + addedDepType);

        if ("Employee +1 Dependant Child".equals(hmoOption) || "Employee + Dependant Children".equals(hmoOption)) {
            if (!addedDepType.contains("Child")) {
                JSFUtils.addFacesErrorMessage("Please check, Child not added !");
                return false;
            }
        } else if ("Employee +Spouse+ Children".equals(hmoOption)) {
            if (!addedDepType.contains("Spouse")) {
                JSFUtils.addFacesErrorMessage("Please check, Spouse not added !");
                return false;
            }
            if (!addedDepType.contains("Child")) {
                JSFUtils.addFacesErrorMessage("Please check, Child not added !");
                return false;
            }
        }
        return true;
    }

    public void copyDepDetails(ActionEvent actionEvent) {
        ArrayList result = (ArrayList) ADFUtils.findOperation("copyDepDetails").execute();
        System.err.println("Result==" + result);
        ViewObject depVo = ADFUtils.findIterator("XxhrHmoTrxDtl_EOViewIterator").getViewObject();
        depVo.executeQuery();
        ViewObject hdrVo = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();

        AdfFacesContext.getCurrentInstance().addPartialTarget(copyDepButton);
        JSFUtils.addFacesInformationMessage("Dependent details copied successfully");
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(copyDepButton);
        hdrVo.getCurrentRow().setAttribute("CopyDep", "Y");
        ADFUtils.findOperation("Commit").execute();

    }

    public void setCopyDepButton(RichButton copyDepButton) {
        this.copyDepButton = copyDepButton;
    }

    public RichButton getCopyDepButton() {
        return copyDepButton;
    }

    public void setDependDob(RichInputDate dependDob) {
        this.dependDob = dependDob;
    }

    public RichInputDate getDependDob() {
        return dependDob;
    }
}

