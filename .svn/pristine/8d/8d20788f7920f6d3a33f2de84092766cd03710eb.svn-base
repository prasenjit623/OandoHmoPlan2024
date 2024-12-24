package com.view.beans;

import com.view.utils.ADFUtils;
import com.view.utils.JSFUtils;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import view.ApprovalPayload;
import view.ApprovalProcess;

import java.util.*;
import java.text.*;

public class ComplaintsFormBB {
    public ComplaintsFormBB() {
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
        ViewObject mediVo = com.view.uiutils.ADFUtils.findIterator("HMOComplaint_VOIterator").getViewObject();
        Row r = mediVo.getCurrentRow();
        r.setAttribute("ActiveFlag", "Y");
        r.setAttribute("ApprovalStatus", "SUBMITTED FOR APPROVAL");
        ADFUtils.findOperation("Commit").execute();
        String[] a = null;
        
        String complaintId = r.getAttribute("ComplaintId")!=null?r.getAttribute("ComplaintId").toString():"";
        String year = r.getAttribute("HmoTransYear")!=null?r.getAttribute("HmoTransYear").toString():"";
        String hmo_ref = r.getAttribute("HmoTransReference")!=null?r.getAttribute("HmoTransReference").toString():"";
        String personId = r.getAttribute("PersonId")!=null?r.getAttribute("PersonId").toString():"";
        String personNo = r.getAttribute("PersonNumber")!=null?r.getAttribute("PersonNumber").toString():"";
        String title = r.getAttribute("Title")!=null?r.getAttribute("Title").toString():"";
        String first_name = r.getAttribute("FirstName")!=null?r.getAttribute("FirstName").toString():"";
        String last_name = r.getAttribute("LastName")!=null?r.getAttribute("LastName").toString():""; 
        String email = ADFContext.getCurrent().getSessionScope().get("UserEmailAddress").toString();      
        String hmoOption = r.getAttribute("HmoOption")!=null?r.getAttribute("HmoOption").toString():""; 
        String stateOfResi = r.getAttribute("StateOfResidence")!=null?r.getAttribute("StateOfResidence").toString():"";
        String hospital = r.getAttribute("Hospital")!=null?r.getAttribute("Hospital").toString():"";
        String dateIssueOccurred = r.getAttribute("DateIssueOccurred")!=null?r.getAttribute("DateIssueOccurred").toString():""; 
        String complaint = r.getAttribute("Complaint")!=null?r.getAttribute("Complaint").toString():"";  
        String reqType = r.getAttribute("RequestType")!=null?r.getAttribute("RequestType").toString():"";
        String comments = r.getAttribute("ApproverComments")!=null?r.getAttribute("ApproverComments").toString():"";
        String status = r.getAttribute("ApprovalStatus")!=null?r.getAttribute("ApprovalStatus").toString():"";
        String complaintReqNo = r.getAttribute("ComplaintNo")!=null?r.getAttribute("ComplaintNo").toString():"";

        String xmlData = ApprovalPayload.complaintChangesXMLData(complaintId, year, hmo_ref, personId, personNo, title, first_name, last_name, email, 
                                                                 hmoOption, stateOfResi, hospital, complaint, dateIssueOccurred, reqType, comments,
                                                                 status, complaintReqNo);
        System.err.println("xmlData =>" + xmlData);
        a = ApprovalProcess.invokeWsdl(xmlData, ApprovalPayload.COMPLAINT_WSDL, ApprovalPayload.COMPLAINT_CHANGE_METHOD);
        if (a[0] != null && a[0].equals("True")) {
            ADFUtils.findOperation("Commit").execute();
            com.view.utils.JSFUtils.addFacesInformationMessage("Complaint Form Submitted Successfully !!");
            return true;
        } else {
            com.view.uiutils.JSFUtils.addFacesInformationMessage("Error");
            r.setAttribute("ApprovalStatus", "DRAFT");
        }
        return false;
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
