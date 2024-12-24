package com.view.beans;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import view.ApprovalPayload;
import view.ApprovalProcess;

public class MedicalDetailsBB {
    public MedicalDetailsBB() {
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
        ViewObject mediVo = com.view.uiutils.ADFUtils.findIterator("HMOMaintance_VOIterator").getViewObject();
        Row r = mediVo.getCurrentRow();
        r.setAttribute("ActiveFlag", "Y");
        r.setAttribute("ApprovalStatus", "SUBMITTED FOR APPROVAL");
        ADFUtils.findOperation("Commit").execute();
        
        String[] a = null;
        String email = ADFContext.getCurrent().getSessionScope().get("UserEmailAddress").toString();
        String maintanceId = r.getAttribute("MaintanceId")!=null?r.getAttribute("MaintanceId").toString():"";
        String year = r.getAttribute("HmoTransYear")!=null?r.getAttribute("HmoTransYear").toString():"";
        String hmo_ref = r.getAttribute("HmoTransReference")!=null?r.getAttribute("HmoTransReference").toString():"";
        String personId = r.getAttribute("PersonId")!=null?r.getAttribute("PersonId").toString():"";
        String personNo = r.getAttribute("PersonNumber")!=null?r.getAttribute("PersonNumber").toString():"";
        String title = r.getAttribute("Title")!=null?r.getAttribute("Title").toString():"";
        String first_name = r.getAttribute("FirstName")!=null?r.getAttribute("FirstName").toString():"";
        String last_name = r.getAttribute("LastName")!=null?r.getAttribute("LastName").toString():"";        
        String hmo_option = r.getAttribute("HmoOption")!=null?r.getAttribute("HmoOption").toString():"";
        String stateOfResi = r.getAttribute("StateOfResidence")!=null?r.getAttribute("StateOfResidence").toString():"";
        String hospital = r.getAttribute("Hospital")!=null?r.getAttribute("Hospital").toString():"";
        String reason = r.getAttribute("ReasonForChange")!=null?r.getAttribute("ReasonForChange").toString():"";
        String reqType = r.getAttribute("RequestType")!=null?r.getAttribute("RequestType").toString():"";
        String status = r.getAttribute("ApprovalStatus")!=null?r.getAttribute("ApprovalStatus").toString():"";
        String maintance_no = r.getAttribute("MaintanceNo")!=null?r.getAttribute("MaintanceNo").toString():"";
        String comments = r.getAttribute("ApproverComments")!=null?r.getAttribute("ApproverComments").toString():"";
        
                                                                          
        String xmlData = ApprovalPayload.medicalChangesXMLData(maintanceId, year, hmo_ref, personId, personNo, title, 
                                                               first_name, last_name, email, hmo_option, stateOfResi, hospital, reason, 
                                                               reqType, status, maintance_no, comments);
        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, ApprovalPayload.MEDICAL_CHANGE_WSDL, ApprovalPayload.MEDICAL_CHANGE_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Change of Medical Details Submitted Successfully !!");
            return true;
        } else {
            com.view.uiutils.JSFUtils.addFacesInformationMessage("Error");
            r.setAttribute("ApprovalStatus", "DRAFT");
        }
        return false;
    }
}
