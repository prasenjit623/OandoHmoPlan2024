package com.view.beans;

import com.view.uiutils.ADFUtils;

import com.view.uiutils.JSFUtils;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class SearchHmoBB {
    public SearchHmoBB() {
        super();
    }
 
    public void onDeleteHmoReq(ActionEvent actionEvent) {
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Object trxHdrId = hdrROVO.getCurrentRow().getAttribute("HmoTrxHdrId");
        
        ViewObject hdrVO = ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        ViewCriteria vc1 = hdrVO.createViewCriteria();
        ViewCriteriaRow vcr = vc1.createViewCriteriaRow();
        vcr.setAttribute("HmoTrxHdrId", trxHdrId);
        vc1.addRow(vcr);
        hdrVO.applyViewCriteria(vc1);
        hdrVO.executeQuery();
        if (hdrVO.getEstimatedRowCount() > 0) {
            Row r = hdrVO.first();
            r.remove();
            ADFUtils.findOperation("Commit").execute();
        }
        hdrVO.applyViewCriteria(null);
        hdrVO.executeQuery();
        hdrROVO.executeQuery();
        }

    public void onclickRequestNo(ActionEvent actionEvent) {
        Object obj = ADFContext.getCurrent()
                               .getPageFlowScope()
                               .get("ReqEditID");
        System.err.println("Object Name ReqEditID------" + obj);
        ViewObject HdrVO = com.view.utils.ADFUtils.findIterator("XxhrHmoTrxHdr_EOViewIterator").getViewObject();
        ViewCriteria hdrVC = HdrVO.createViewCriteria();
        ViewCriteriaRow hdrVcr = hdrVC.createViewCriteriaRow();
        hdrVcr.setAttribute("HmoTrxHdrId", obj);
        hdrVC.addRow(hdrVcr);
        HdrVO.applyViewCriteria(hdrVC);
        HdrVO.executeQuery();
    }

    public String onEditOtherRequest() {
        
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row hdrRow = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", hdrRow.getAttribute("EmailAddress"));
        
        ViewObject dtlReqVo = ADFUtils.findIterator("HMOOtherRequestDetails_ROVOIterator").getViewObject();
        Row r = dtlReqVo.getCurrentRow();
        String reqNo = r.getAttribute("RequestNo")!=null?r.getAttribute("RequestNo").toString():"0";
        ADFContext.getCurrent().getPageFlowScope().put("RequestEditID", reqNo);
        ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "EDIT");
        
        String reqType = r.getAttribute("RequestType")!=null?r.getAttribute("RequestType").toString():"0";
        if("MAINTANCE".equals(reqType)){
            return "toMedical"; 
        }
        if("COMPLAINT".equals(reqType)){
            return "toComplaints"; 
        }
        if("CARD".equals(reqType)){
            return "toCard"; 
        }
        if("REIMBURSEMENT_CLAIM".equals(reqType)){
            return "toReimbursement"; 
        }
        if("NEW_DEPENDENT".equals(reqType)){
            return "toNewDep"; 
        }
        return "";
    }

    public String addMedicalDetailsAction() {
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row r = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", r.getAttribute("EmailAddress"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOReqNo", r.getAttribute("HmoRequestNo"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOOption", r.getAttribute("HmoType"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonId", r.getAttribute("PersonId"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonNo", r.getAttribute("PersonNumber"));
        ADFContext.getCurrent().getPageFlowScope().put("Title", r.getAttribute("Title"));
        ADFContext.getCurrent().getPageFlowScope().put("FirstName", r.getAttribute("FirstName"));
        ADFContext.getCurrent().getPageFlowScope().put("LastName", r.getAttribute("LastName"));
        ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "ADD");
                
        return "toMedical";
    }
    public String addComplaintAction() {
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row r = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", r.getAttribute("EmailAddress"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOReqNo", r.getAttribute("HmoRequestNo"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOOption", r.getAttribute("HmoType"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonId", r.getAttribute("PersonId"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonNo", r.getAttribute("PersonNumber"));
        ADFContext.getCurrent().getPageFlowScope().put("Title", r.getAttribute("Title"));
        ADFContext.getCurrent().getPageFlowScope().put("FirstName", r.getAttribute("FirstName"));
        ADFContext.getCurrent().getPageFlowScope().put("LastName", r.getAttribute("LastName"));
        ADFContext.getCurrent().getPageFlowScope().put("StateOfResi", r.getAttribute("StateOfResidence"));
        ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "ADD");
                
        return "toComplaints";
    }

    public String addCardAction() {
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row r = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", r.getAttribute("EmailAddress"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOReqNo", r.getAttribute("HmoRequestNo"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOOption", r.getAttribute("HmoType"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonId", r.getAttribute("PersonId"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonNo", r.getAttribute("PersonNumber"));
        ADFContext.getCurrent().getPageFlowScope().put("Title", r.getAttribute("Title"));
        ADFContext.getCurrent().getPageFlowScope().put("FirstName", r.getAttribute("FirstName"));
        ADFContext.getCurrent().getPageFlowScope().put("LastName", r.getAttribute("LastName"));
        ADFContext.getCurrent().getPageFlowScope().put("Mobile", r.getAttribute("MobileNo"));
        ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "ADD");
        
        return "toCard";
    }

    public String addReimbursementClaim() {
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row r = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", r.getAttribute("EmailAddress"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOReqNo", r.getAttribute("HmoRequestNo"));
        ADFContext.getCurrent().getPageFlowScope().put("HMOOption", r.getAttribute("HmoType"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonId", r.getAttribute("PersonId"));
        ADFContext.getCurrent().getPageFlowScope().put("PersonNo", r.getAttribute("PersonNumber"));
        ADFContext.getCurrent().getPageFlowScope().put("Title", r.getAttribute("Title"));
        ADFContext.getCurrent().getPageFlowScope().put("FirstName", r.getAttribute("FirstName"));
        ADFContext.getCurrent().getPageFlowScope().put("LastName", r.getAttribute("LastName"));
        ADFContext.getCurrent().getPageFlowScope().put("Mobile", r.getAttribute("MobileNo"));
        ADFContext.getCurrent().getPageFlowScope().put("StateOfResi", r.getAttribute("StateOfResidence"));
        ADFContext.getCurrent().getPageFlowScope().put("Hospital", r.getAttribute("Hospital"));
        ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "ADD");
        System.err.println("toReimbursement");
        return "toReimbursement";
    }

    public String onCreateNewDep() {
        ArrayList result = (ArrayList) ADFUtils.findOperation("createNewDependentRequest").execute();
        System.err.println("Result=="+result);
        String hmo_newId = result.get(0)!=null ? result.get(0).toString() :"0";
        String msg = result.get(1)!=null ? result.get(1).toString() :"0";
        ViewObject hdrROVO = ADFUtils.findIterator("SearchHmoTrxHdr_ROVOIterator").getViewObject();
        Row r = hdrROVO.getCurrentRow();
        ADFContext.getCurrent().getSessionScope().put("UserEmailAddress", r.getAttribute("EmailAddress"));
        if("SUCCESS".equals(msg)){
            ADFContext.getCurrent().getPageFlowScope().put("RequestEditID", hmo_newId);
            ADFContext.getCurrent().getPageFlowScope().put("AddEditMode", "EDIT");
            JSFUtils.addFacesInformationMessage("Request Created Successfully !");
            return "toNewDep";    
        }else{
            JSFUtils.addFacesErrorMessage("Error : Please Check, Error In creating new dependent request !");
            return "";
        }
        
    }

    public void onDeleteOtherRequest(ActionEvent actionEvent) {
        RichPopup popup = (RichPopup) com.view.uiutils.JSFUtils.findComponentInRoot("p2");
        String msg = (String) ADFUtils.findOperation("updateDeleteFlag").execute();
        if("S".equals(msg)){
            ViewObject dtlReqVo = ADFUtils.findIterator("HMOOtherRequestDetails_ROVOIterator").getViewObject();
            dtlReqVo.executeQuery();
            popup.hide();
            JSFUtils.addFacesInformationMessage("Request Deleted Successfully !");
    }
    }
}
