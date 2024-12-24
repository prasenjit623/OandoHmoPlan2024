package com.view.beans;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

import view.ApprovalPayload;
import view.ApprovalProcess;

public class NewDependentBB {
    private RichPopup dependentPop;
    private RichPopup evidencePop;

    public NewDependentBB() {
        super();
    }

    public String onSaveAction() {
        ViewObject mediVo = com.view
                               .uiutils
                               .ADFUtils
                               .findIterator("NewDepHmoHdr_VOIterator")
                               .getViewObject();
        Row r = mediVo.getCurrentRow();
        r.setAttribute("HmoPlanTotal", r.getAttribute("HmoPlanTotal"));
        r.setAttribute("HmoPlanDeduction", r.getAttribute("HmoPlanDeduction"));

        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Request Saved Successfully !");
        return "toSearch";
    }

    public String onCancelAction() {
        ADFUtils.findOperation("Rollback").execute();
        return "toSearch";
    }

    public void onClickEditDependent(ActionEvent actionEvent) {
        com.view
           .uiutils
           .JSFUtils
           .showPopup(dependentPop);
    }

    public void onNewDependent(ActionEvent actionEvent) {
        if (isEvidenceAdded()) {
            if (isDepPhotoAdded()) {
                com.view
                   .uiutils
                   .ADFUtils
                   .findOperation("CreateDependent")
                   .execute();
                com.view
                   .uiutils
                   .JSFUtils
                   .showPopup(dependentPop);
            } else {
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Please Check, Dependent Photo Missing !");
            }
        } else {
            com.view
               .uiutils
               .JSFUtils
               .addFacesErrorMessage("Please Check, Evidence of DOB Missing !");
        }
    }

    private boolean isEvidenceAdded() {
        ViewObject depVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
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

    private boolean isDepPhotoAdded() {
        ViewObject depVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
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

    public void setDependentPop(RichPopup dependentPop) {
        this.dependentPop = dependentPop;
    }

    public RichPopup getDependentPop() {
        return dependentPop;
    }

    public void onClickOkDepPop(ActionEvent actionEvent) {
        dependentPop.hide();
        this.refresh();
    }

    public void onClickCancelDepPop(ActionEvent actionEvent) {
        if ("ADD".equals(ADFContext.getCurrent()
                                   .getPageFlowScope()
                                   .get("ADD_EDIT_MODE"))) {
            ViewObject hdrVo = com.view
                                  .uiutils
                                  .ADFUtils
                                  .findIterator("NewDepHmoDtl_VOIterator")
                                  .getViewObject();
            hdrVo.getCurrentRow().remove();
        }
        dependentPop.hide();
        this.refresh();
    }

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentView = context.getViewRoot().getViewId();
        ViewHandler vh = context.getApplication().getViewHandler();
        UIViewRoot x = vh.createView(context, currentView);
        x.setViewId(currentView);
        context.setViewRoot(x);
    }

    public void uploadProfileDependent(ValueChangeEvent valueChangeEvent) {
        UploadedFile uploadedFile = (UploadedFile) valueChangeEvent.getNewValue();
        ViewObject hdrVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
        Row row = hdrVo.getCurrentRow();
        String imageByteArrayInString = convertBlobToBase64(createBlobDomain(uploadedFile));
        row.setAttribute("DepPhoto", createBlobDomain(uploadedFile));
        row.setAttribute("DepPhotoClob", imageByteArrayInString);
        com.view
           .uiutils
           .ADFUtils
           .findOperation("Commit")
           .execute();
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
            ImageIO.write(ImageIO.read(imageInputStream), "jpg", byteArrayImageOutputStream);
            byteArrayImageOutputStream.flush();
            imageByteArrayInString = DatatypeConverter.printBase64Binary(byteArrayImageOutputStream.toByteArray());
        } catch (IOException e) {
        }

        return imageByteArrayInString;
    }

    /**Method to download file from actual path
     * @param facesContext
     * @param outputStream
     */
    public void onDownloadEvidence(FacesContext facesContext, OutputStream outputStream) throws IOException {
        ViewObject hdrVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepAttachment_VOIterator")
                              .getViewObject();
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
                ViewObject vo = com.view
                                   .uiutils
                                   .ADFUtils
                                   .findIterator("NewDepAttachment_VOIterator")
                                   .getViewObject();
                Row curRow = vo.getCurrentRow();
                String filename = myfile.getFilename();
                String ContentType = myfile.getContentType();
                try {
                    curRow.setAttribute("AttachmentName", filename);
                    //  curRow.setAttribute("AttachmentType", ContentType);
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

    public void onOkEvidPop(ActionEvent actionEvent) {
        evidencePop.hide();
        this.refresh();
    }

    public void setEvidencePop(RichPopup evidencePop) {
        this.evidencePop = evidencePop;
    }

    public RichPopup getEvidencePop() {
        return evidencePop;
    }

    public void onClickEvidence(ActionEvent actionEvent) {
        ViewObject hdrVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
        Row r = hdrVo.getCurrentRow();
        String exist = r.getAttribute("ExistingData") != null ? r.getAttribute("ExistingData").toString() : "Y";
        ADFContext.getCurrent()
                  .getPageFlowScope()
                  .put("ExistDependent", exist);
        com.view
           .uiutils
           .JSFUtils
           .showPopup(evidencePop);
    }


    public void onChangeDepType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String hmoOption = com.view
                              .uiutils
                              .ADFUtils
                              .getBoundAttributeValue("HmoOption")
                              .toString();
        ViewObject dtlVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
        Row r = dtlVo.getCurrentRow();
        String dep = r.getAttribute("DependentType") == null ? "null" : r.getAttribute("DependentType").toString();
        Row[] msDtlRows = dtlVo.getFilteredRows("DependentType", dep);
        if (msDtlRows.length > 1) {
            if (dep.equals("Spouse")) {
                RichSelectOneChoice depType = (RichSelectOneChoice) com.view
                                                                       .uiutils
                                                                       .JSFUtils
                                                                       .findComponentInRoot("soc2");
                depType.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(depType);
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Spouse Already Exist !");
            } else {
            }
        }
        if ("Employee +1 Dependant Child".equals(hmoOption)) {
            Row[] dtlRows = dtlVo.getFilteredRows("DependentType", "Child");
            if (dtlRows.length > 1) {
                RichSelectOneChoice depType = (RichSelectOneChoice) com.view
                                                                       .uiutils
                                                                       .JSFUtils
                                                                       .findComponentInRoot("soc2");
                depType.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(depType);
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Child Already Exist !");
            }
        }
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

    public void onChangeDepDOB(ValueChangeEvent valueChangeEvent) throws ParseException {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String ageDescription = null;
        boolean check = false;
        if (valueChangeEvent.getNewValue().toString() != null) {
            String depDob = valueChangeEvent.getNewValue().toString();
            ViewObject depVo = com.view
                                  .uiutils
                                  .ADFUtils
                                  .findIterator("NewDepHmoDtl_VOIterator")
                                  .getViewObject();
            Row lineRow = depVo.getCurrentRow();
            String depType =
                lineRow.getAttribute("DependentType") == null ? "null" :
                lineRow.getAttribute("DependentType").toString();
            if (checkChildAge(depDob, depType)) {
                double p_amount = 0;
                OperationBinding op = com.view
                                         .uiutils
                                         .ADFUtils
                                         .findOperation("getHMOCost");
                ArrayList list = (ArrayList) op.execute();
                if (list != null) {
                    p_amount = Double.parseDouble(list.get(0).toString());

                    if (p_amount > 0) {
                        System.err.println("covered");
                        lineRow.setAttribute("DependentCost", p_amount);

                    } else {
                        check = true;
                        System.err.println("not covered");
                        com.view
                           .uiutils
                           .JSFUtils
                           .addFacesErrorMessage("HMO selected does not cover the age range");
                        lineRow.setAttribute("DependentCost", 0);
                        lineRow.setAttribute("DepDob", null);
                        lineRow.setAttribute("DepAgeCategory", null);
                        lineRow.setAttribute("DepAgeCategory", "");
                        return;
                    }
                }
                String p_age = null;
                if (!check) {
                    OperationBinding oP = com.view
                                             .uiutils
                                             .ADFUtils
                                             .findOperation("getAgeCategory");
                    ArrayList lists = (ArrayList) oP.execute();
                    if (lists != null) {
                        p_age = lists.get(0).toString();
                        Row[] orgRows;
                        if (p_age != null) {
                            orgRows = com.view
                                         .uiutils
                                         .ADFUtils
                                         .findIterator("XxfndLookupValues_V_ROVOIterator")
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

    private boolean checkChildAge(String depDob, String depType) throws ParseException {
        if ("Child".equals(depType)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDate locDob = LocalDate.parse(depDob, formatter);
            LocalDate today = LocalDate.now();
            Period p = Period.between(locDob, today);
            if (p.getYears() == 24) {
                if (p.getMonths() == 0 && p.getDays() == 0) {
                    return true;
                } else {
                    com.view
                       .uiutils
                       .JSFUtils
                       .addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                    return false;
                }
            } else {
                if (p.getYears() < 24) {
                    return true;
                } else {
                    com.view
                       .uiutils
                       .JSFUtils
                       .addFacesErrorMessage("Please check, Child age range should not be more than 24.");
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    public String onSubmitAction() {

        if (!isEvidenceAdded()) {
            com.view
               .uiutils
               .JSFUtils
               .addFacesErrorMessage("Please Check, Evidence of DOB Missing !");
            return "";
        }
        if (!isDepPhotoAdded()) {
            com.view
               .uiutils
               .JSFUtils
               .addFacesErrorMessage("Please Check, Dependent Photo Missing !");
            return "";
        }
        if (!isAllDependentAdded()) {
            return "";
        }
        if (callSubmitPayload()) {
            return "toSearch";
        }
        return "";
    }

    private boolean callSubmitPayload() {
        ViewObject mediVo = com.view
                               .uiutils
                               .ADFUtils
                               .findIterator("NewDepHmoHdr_VOIterator")
                               .getViewObject();
        Row r = mediVo.getCurrentRow();
        r.setAttribute("HmoPlanTotal", r.getAttribute("HmoPlanTotal"));
        r.setAttribute("HmoPlanDeduction", r.getAttribute("HmoPlanDeduction"));
        r.setAttribute("ActiveFlag", "Y");
        r.setAttribute("ApprovalStatus", "SUBMITTED FOR APPROVAL");
        ADFUtils.findOperation("Commit").execute();

        String[] a = null;

        String hmoHdrId = r.getAttribute("HmoHdrId") != null ? r.getAttribute("HmoHdrId").toString() : "";
        String hmo_ref =
            r.getAttribute("HmoTransReference") != null ? r.getAttribute("HmoTransReference").toString() : "";
        String requestNo = r.getAttribute("HmoRequestNo") != null ? r.getAttribute("HmoRequestNo").toString() : "";
        String requestDate =
            r.getAttribute("HmoRequestDate") != null ? r.getAttribute("HmoRequestDate").toString() : "";
        String hmoMstHdrId =
            r.getAttribute("HmoMasterHdrId") != null ? r.getAttribute("HmoMasterHdrId").toString() : "";
        String personId = r.getAttribute("PersonId") != null ? r.getAttribute("PersonId").toString() : "";
        String personName = "";
        String email = ADFContext.getCurrent()
                                 .getSessionScope()
                                 .get("UserEmailAddress")
                                 .toString();
        String activeFlag = r.getAttribute("ActiveFlag") != null ? r.getAttribute("ActiveFlag").toString() : "";
        String comments =
            r.getAttribute("ApproverComments") != null ? r.getAttribute("ApproverComments").toString() : "";
        String status = r.getAttribute("ApprovalStatus") != null ? r.getAttribute("ApprovalStatus").toString() : "";
        System.out.println("email------ " + email);

        String xmlData =
            ApprovalPayload.newDependentChangesXMLData(hmoHdrId, hmo_ref, requestNo, requestDate, hmoMstHdrId, personId,
                                                       personName, email, activeFlag, comments, status);
        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, ApprovalPayload.NEW_DEPENDENT_WSDL,
                                       ApprovalPayload.NEW_DEPENDENT_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("New Dependent Form Submitted Successfully !!");
            return true;
        } else {
            com.view
               .uiutils
               .JSFUtils
               .addFacesInformationMessage("Error");
            r.setAttribute("ApprovalStatus", "DRAFT");
        }
        return false;
    }

    public String getEmployeeHMOCost() throws SQLException {
        boolean result = false;
        ViewObject vo = com.view
                           .utils
                           .ADFUtils
                           .findIterator("NewDepHmoHdr_VOIterator")
                           .getViewObject();
        String empCost = vo.getCurrentRow().getAttribute("Trans_EmpCost") == null ? "" : vo.getCurrentRow()
                                                                                           .getAttribute("Trans_EmpCost")
                                                                                           .toString();
        if ("".equals(empCost)) {
            oracle.jbo.domain.Number busUnitId =
                (vo.getCurrentRow().getAttribute("BusinessUnitId") == null ? new oracle.jbo.domain.Number(0) :
                 new oracle.jbo.domain.Number(vo.getCurrentRow().getAttribute("BusinessUnitId")));
            vo.getCurrentRow().setAttribute("BusinessUnitId", busUnitId);
            RowSetIterator itr = vo.createRowSetIterator(null);
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
            } else if (selectedHmoOption.equals("EMP_ONLY")) {
                depOptions = "Employee Only";
            } else if (selectedHmoOption.equals("Employee + Other Dependant") &&
                       ("AXAMANSARD HEALTH".equals(selectedHmoType) || "RELIANCE HEALTH".equals(selectedHmoType))) {
                agedepOptions = "EMP_ONLY";
                selectedHmoCategory = "Traditional Dependent Categorization";
                result = true;
            } else {
                depOptions = selectedHmoOption;
            }

            oracle.jbo.domain.Number hmo_master_hdr_id =
                vo.getCurrentRow().getAttribute("HmoMasterHdrId") == null ? new oracle.jbo.domain.Number(0) :
                new oracle.jbo.domain.Number(vo.getCurrentRow().getAttribute("HmoMasterHdrId"));

            oracle.jbo.domain.Number person_id =

                vo.getCurrentRow().getAttribute("PersonId") == null ? new oracle.jbo.domain.Number(0) :
                new oracle.jbo.domain.Number(vo.getCurrentRow().getAttribute("PersonId"));

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
            if (!"AXAMANSARD HEALTH".equals(hmo_type) || !"RELIANCE HEALTH".equals(hmo_type) ||
                "Plus Africa".equals(hmo_category) || "Global Elite".equals(hmo_category) ||
                "Age-based Categorization".equals(hmo_category)) {
                OperationBinding op = com.view
                                         .utils
                                         .ADFUtils
                                         .findOperation("getEmpHmoCost");

                op.getParamsMap().put("p_hmo_master_hdr_id", hmo_master_hdr_id);
                op.getParamsMap().put("p_hmo_type", hmo_type);
                op.getParamsMap().put("p_person_id", person_id);
                if (result) {
                    op.getParamsMap().put("p_hmo_category", selectedHmoCategory);
                    op.getParamsMap().put("p_hmo_option", agedepOptions);
                } else {
                    op.getParamsMap().put("p_hmo_category", hmo_category);
                    op.getParamsMap().put("p_hmo_option", hmo_option);
                }
                op.getParamsMap().put("p_emp_dob", dob);
                op.getParamsMap().put("p_addi_category", addi_category);

                ArrayList list = (ArrayList) op.execute();

                double p_amount = 0;
                if (list != null) {
                    p_amount = Double.parseDouble(list.get(0).toString());

                    if (p_amount >= 0) {
                        vo.getCurrentRow().setAttribute("Trans_EmpCost", p_amount);
                    }
                }
            }
        }
        return "";
    }

    private boolean isAllDependentAdded() {
        String hmoOption = com.view
                              .uiutils
                              .ADFUtils
                              .getBoundAttributeValue("HmoOption")
                              .toString();
        ArrayList<String> addedDepType = new ArrayList<String>();
        ViewObject depVo = com.view
                              .uiutils
                              .ADFUtils
                              .findIterator("NewDepHmoDtl_VOIterator")
                              .getViewObject();
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
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Please check, Child not added !");
                return false;
            }
        } else if ("Employee +Spouse+ Children".equals(hmoOption)) {
            if (!addedDepType.contains("Spouse")) {
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Please check, Spouse not added !");
                return false;
            }
            if (!addedDepType.contains("Child")) {
                com.view
                   .uiutils
                   .JSFUtils
                   .addFacesErrorMessage("Please check, Child not added !");
                return false;
            }
        }
        return true;
    }

}
