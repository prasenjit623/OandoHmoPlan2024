package model.vo;

import java.math.BigDecimal;

import java.sql.Timestamp;

import model.eo.XxhrHmoDtl_EOImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 25 12:24:21 IST 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class XxhrHmoDtl_VORowImpl extends ViewRowImpl {


    public static final int ENTITY_XXHRHMODTL_EO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CreatedBy,
        CreationDate,
        DepAgeCategory,
        DepDob,
        DepFirstName,
        DepGender,
        DepLastName,
        DepPhoto,
        DepPhotoClob,
        DepTitle,
        DependentClass,
        DependentCost,
        DependentType,
        HmoDtlId,
        HmoHdrId,
        Hospital,
        LastUpdateDate,
        LastUpdateLogin,
        LastUpdatedBy,
        MaritalStatus,
        MobileNo,
        PersonId,
        ResiAddress,
        ResidentialAddress,
        StateOfResidence,
        HmoDtlOldId,
        ExistingData,
        EvidenceCount,
        HMOType,
        ReasonForChange,
        HMOOption,
        XxfndAttachment_EOView,
        XxhrHmoHdr_VO,
        LookupValues_DepTypes,
        LookupValues_Title,
        FetchHospital_ROVO,
        StateOfResidence_ROVO,
        LookupValues_Gender;
        private static AttributesEnum[] vals = null;
        ;
        private static final int firstIndex = 0;

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int DEPAGECATEGORY = AttributesEnum.DepAgeCategory.index();
    public static final int DEPDOB = AttributesEnum.DepDob.index();
    public static final int DEPFIRSTNAME = AttributesEnum.DepFirstName.index();
    public static final int DEPGENDER = AttributesEnum.DepGender.index();
    public static final int DEPLASTNAME = AttributesEnum.DepLastName.index();
    public static final int DEPPHOTO = AttributesEnum.DepPhoto.index();
    public static final int DEPPHOTOCLOB = AttributesEnum.DepPhotoClob.index();
    public static final int DEPTITLE = AttributesEnum.DepTitle.index();
    public static final int DEPENDENTCLASS = AttributesEnum.DependentClass.index();
    public static final int DEPENDENTCOST = AttributesEnum.DependentCost.index();
    public static final int DEPENDENTTYPE = AttributesEnum.DependentType.index();
    public static final int HMODTLID = AttributesEnum.HmoDtlId.index();
    public static final int HMOHDRID = AttributesEnum.HmoHdrId.index();
    public static final int HOSPITAL = AttributesEnum.Hospital.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int LASTUPDATELOGIN = AttributesEnum.LastUpdateLogin.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int MARITALSTATUS = AttributesEnum.MaritalStatus.index();
    public static final int MOBILENO = AttributesEnum.MobileNo.index();
    public static final int PERSONID = AttributesEnum.PersonId.index();
    public static final int RESIADDRESS = AttributesEnum.ResiAddress.index();
    public static final int RESIDENTIALADDRESS = AttributesEnum.ResidentialAddress.index();
    public static final int STATEOFRESIDENCE = AttributesEnum.StateOfResidence.index();
    public static final int HMODTLOLDID = AttributesEnum.HmoDtlOldId.index();
    public static final int EXISTINGDATA = AttributesEnum.ExistingData.index();
    public static final int EVIDENCECOUNT = AttributesEnum.EvidenceCount.index();
    public static final int HMOTYPE = AttributesEnum.HMOType.index();
    public static final int REASONFORCHANGE = AttributesEnum.ReasonForChange.index();
    public static final int HMOOPTION = AttributesEnum.HMOOption.index();
    public static final int XXFNDATTACHMENT_EOVIEW = AttributesEnum.XxfndAttachment_EOView.index();
    public static final int XXHRHMOHDR_VO = AttributesEnum.XxhrHmoHdr_VO.index();
    public static final int LOOKUPVALUES_DEPTYPES = AttributesEnum.LookupValues_DepTypes.index();
    public static final int LOOKUPVALUES_TITLE = AttributesEnum.LookupValues_Title.index();
    public static final int FETCHHOSPITAL_ROVO = AttributesEnum.FetchHospital_ROVO.index();
    public static final int STATEOFRESIDENCE_ROVO = AttributesEnum.StateOfResidence_ROVO.index();
    public static final int LOOKUPVALUES_GENDER = AttributesEnum.LookupValues_Gender.index();

    /**
     * This is the default constructor (do not remove).
     */
    public XxhrHmoDtl_VORowImpl() {
    }

    /**
     * Gets XxhrHmoDtl_EO entity object.
     * @return the XxhrHmoDtl_EO
     */
    public XxhrHmoDtl_EOImpl getXxhrHmoDtl_EO() {
        return (XxhrHmoDtl_EOImpl) getEntity(ENTITY_XXHRHMODTL_EO);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Timestamp getCreationDate() {
        return (Timestamp) getAttributeInternal(CREATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Timestamp value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for DEP_AGE_CATEGORY using the alias name DepAgeCategory.
     * @return the DEP_AGE_CATEGORY
     */
    public String getDepAgeCategory() {
        return (String) getAttributeInternal(DEPAGECATEGORY);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_AGE_CATEGORY using the alias name DepAgeCategory.
     * @param value value to set the DEP_AGE_CATEGORY
     */
    public void setDepAgeCategory(String value) {
        setAttributeInternal(DEPAGECATEGORY, value);
    }

    /**
     * Gets the attribute value for DEP_DOB using the alias name DepDob.
     * @return the DEP_DOB
     */
    public Timestamp getDepDob() {
        return (Timestamp) getAttributeInternal(DEPDOB);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_DOB using the alias name DepDob.
     * @param value value to set the DEP_DOB
     */
    public void setDepDob(Timestamp value) {
        setAttributeInternal(DEPDOB, value);
    }

    /**
     * Gets the attribute value for DEP_FIRST_NAME using the alias name DepFirstName.
     * @return the DEP_FIRST_NAME
     */
    public String getDepFirstName() {
        return (String) getAttributeInternal(DEPFIRSTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_FIRST_NAME using the alias name DepFirstName.
     * @param value value to set the DEP_FIRST_NAME
     */
    public void setDepFirstName(String value) {
        setAttributeInternal(DEPFIRSTNAME, value);
    }

    /**
     * Gets the attribute value for DEP_GENDER using the alias name DepGender.
     * @return the DEP_GENDER
     */
    public String getDepGender() {
        return (String) getAttributeInternal(DEPGENDER);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_GENDER using the alias name DepGender.
     * @param value value to set the DEP_GENDER
     */
    public void setDepGender(String value) {
        setAttributeInternal(DEPGENDER, value);
    }

    /**
     * Gets the attribute value for DEP_LAST_NAME using the alias name DepLastName.
     * @return the DEP_LAST_NAME
     */
    public String getDepLastName() {
        return (String) getAttributeInternal(DEPLASTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_LAST_NAME using the alias name DepLastName.
     * @param value value to set the DEP_LAST_NAME
     */
    public void setDepLastName(String value) {
        setAttributeInternal(DEPLASTNAME, value);
    }

    /**
     * Gets the attribute value for DEP_PHOTO using the alias name DepPhoto.
     * @return the DEP_PHOTO
     */
    public BlobDomain getDepPhoto() {
        return (BlobDomain) getAttributeInternal(DEPPHOTO);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_PHOTO using the alias name DepPhoto.
     * @param value value to set the DEP_PHOTO
     */
    public void setDepPhoto(BlobDomain value) {
        setAttributeInternal(DEPPHOTO, value);
    }

    /**
     * Gets the attribute value for DEP_PHOTO_CLOB using the alias name DepPhotoClob.
     * @return the DEP_PHOTO_CLOB
     */
    public ClobDomain getDepPhotoClob() {
        return (ClobDomain) getAttributeInternal(DEPPHOTOCLOB);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_PHOTO_CLOB using the alias name DepPhotoClob.
     * @param value value to set the DEP_PHOTO_CLOB
     */
    public void setDepPhotoClob(ClobDomain value) {
        setAttributeInternal(DEPPHOTOCLOB, value);
    }

    /**
     * Gets the attribute value for DEP_TITLE using the alias name DepTitle.
     * @return the DEP_TITLE
     */
    public String getDepTitle() {
        return (String) getAttributeInternal(DEPTITLE);
    }

    /**
     * Sets <code>value</code> as attribute value for DEP_TITLE using the alias name DepTitle.
     * @param value value to set the DEP_TITLE
     */
    public void setDepTitle(String value) {
        setAttributeInternal(DEPTITLE, value);
    }

    /**
     * Gets the attribute value for DEPENDENT_CLASS using the alias name DependentClass.
     * @return the DEPENDENT_CLASS
     */
    public String getDependentClass() {
        return (String) getAttributeInternal(DEPENDENTCLASS);
    }

    /**
     * Sets <code>value</code> as attribute value for DEPENDENT_CLASS using the alias name DependentClass.
     * @param value value to set the DEPENDENT_CLASS
     */
    public void setDependentClass(String value) {
        setAttributeInternal(DEPENDENTCLASS, value);
    }

    /**
     * Gets the attribute value for DEPENDENT_COST using the alias name DependentCost.
     * @return the DEPENDENT_COST
     */
    public BigDecimal getDependentCost() {
        return (BigDecimal) getAttributeInternal(DEPENDENTCOST);
    }

    /**
     * Sets <code>value</code> as attribute value for DEPENDENT_COST using the alias name DependentCost.
     * @param value value to set the DEPENDENT_COST
     */
    public void setDependentCost(BigDecimal value) {
        setAttributeInternal(DEPENDENTCOST, value);
    }

    /**
     * Gets the attribute value for DEPENDENT_TYPE using the alias name DependentType.
     * @return the DEPENDENT_TYPE
     */
    public String getDependentType() {
        return (String) getAttributeInternal(DEPENDENTTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for DEPENDENT_TYPE using the alias name DependentType.
     * @param value value to set the DEPENDENT_TYPE
     */
    public void setDependentType(String value) {
        setAttributeInternal(DEPENDENTTYPE, value);
    }

    /**
     * Gets the attribute value for HMO_DTL_ID using the alias name HmoDtlId.
     * @return the HMO_DTL_ID
     */
    public BigDecimal getHmoDtlId() {
        return (BigDecimal) getAttributeInternal(HMODTLID);
    }

    /**
     * Sets <code>value</code> as attribute value for HMO_DTL_ID using the alias name HmoDtlId.
     * @param value value to set the HMO_DTL_ID
     */
    public void setHmoDtlId(BigDecimal value) {
        setAttributeInternal(HMODTLID, value);
    }

    /**
     * Gets the attribute value for HMO_HDR_ID using the alias name HmoHdrId.
     * @return the HMO_HDR_ID
     */
    public BigDecimal getHmoHdrId() {
        return (BigDecimal) getAttributeInternal(HMOHDRID);
    }

    /**
     * Sets <code>value</code> as attribute value for HMO_HDR_ID using the alias name HmoHdrId.
     * @param value value to set the HMO_HDR_ID
     */
    public void setHmoHdrId(BigDecimal value) {
        setAttributeInternal(HMOHDRID, value);
    }

    /**
     * Gets the attribute value for HOSPITAL using the alias name Hospital.
     * @return the HOSPITAL
     */
    public String getHospital() {
        return (String) getAttributeInternal(HOSPITAL);
    }

    /**
     * Sets <code>value</code> as attribute value for HOSPITAL using the alias name Hospital.
     * @param value value to set the HOSPITAL
     */
    public void setHospital(String value) {
        setAttributeInternal(HOSPITAL, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @return the LAST_UPDATE_DATE
     */
    public Timestamp getLastUpdateDate() {
        return (Timestamp) getAttributeInternal(LASTUPDATEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @param value value to set the LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Timestamp value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin.
     * @return the LAST_UPDATE_LOGIN
     */
    public String getLastUpdateLogin() {
        return (String) getAttributeInternal(LASTUPDATELOGIN);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_LOGIN using the alias name LastUpdateLogin.
     * @param value value to set the LAST_UPDATE_LOGIN
     */
    public void setLastUpdateLogin(String value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @return the LAST_UPDATED_BY
     */
    public String getLastUpdatedBy() {
        return (String) getAttributeInternal(LASTUPDATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @param value value to set the LAST_UPDATED_BY
     */
    public void setLastUpdatedBy(String value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for MARITAL_STATUS using the alias name MaritalStatus.
     * @return the MARITAL_STATUS
     */
    public String getMaritalStatus() {
        return (String) getAttributeInternal(MARITALSTATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for MARITAL_STATUS using the alias name MaritalStatus.
     * @param value value to set the MARITAL_STATUS
     */
    public void setMaritalStatus(String value) {
        setAttributeInternal(MARITALSTATUS, value);
    }

    /**
     * Gets the attribute value for MOBILE_NO using the alias name MobileNo.
     * @return the MOBILE_NO
     */
    public String getMobileNo() {
        return (String) getAttributeInternal(MOBILENO);
    }

    /**
     * Sets <code>value</code> as attribute value for MOBILE_NO using the alias name MobileNo.
     * @param value value to set the MOBILE_NO
     */
    public void setMobileNo(String value) {
        setAttributeInternal(MOBILENO, value);
    }

    /**
     * Gets the attribute value for PERSON_ID using the alias name PersonId.
     * @return the PERSON_ID
     */
    public BigDecimal getPersonId() {
        return (BigDecimal) getAttributeInternal(PERSONID);
    }

    /**
     * Sets <code>value</code> as attribute value for PERSON_ID using the alias name PersonId.
     * @param value value to set the PERSON_ID
     */
    public void setPersonId(BigDecimal value) {
        setAttributeInternal(PERSONID, value);
    }

    /**
     * Gets the attribute value for RESI_ADDRESS using the alias name ResiAddress.
     * @return the RESI_ADDRESS
     */
    public String getResiAddress() {
        return (String) getAttributeInternal(RESIADDRESS);
    }

    /**
     * Sets <code>value</code> as attribute value for RESI_ADDRESS using the alias name ResiAddress.
     * @param value value to set the RESI_ADDRESS
     */
    public void setResiAddress(String value) {
        setAttributeInternal(RESIADDRESS, value);
    }

    /**
     * Gets the attribute value for RESIDENTIAL_ADDRESS using the alias name ResidentialAddress.
     * @return the RESIDENTIAL_ADDRESS
     */
    public String getResidentialAddress() {
        return (String) getAttributeInternal(RESIDENTIALADDRESS);
    }

    /**
     * Sets <code>value</code> as attribute value for RESIDENTIAL_ADDRESS using the alias name ResidentialAddress.
     * @param value value to set the RESIDENTIAL_ADDRESS
     */
    public void setResidentialAddress(String value) {
        setAttributeInternal(RESIDENTIALADDRESS, value);
    }

    /**
     * Gets the attribute value for STATE_OF_RESIDENCE using the alias name StateOfResidence.
     * @return the STATE_OF_RESIDENCE
     */
    public String getStateOfResidence() {
        return (String) getAttributeInternal(STATEOFRESIDENCE);
    }

    /**
     * Sets <code>value</code> as attribute value for STATE_OF_RESIDENCE using the alias name StateOfResidence.
     * @param value value to set the STATE_OF_RESIDENCE
     */
    public void setStateOfResidence(String value) {
        setAttributeInternal(STATEOFRESIDENCE, value);
    }

    /**
     * Gets the attribute value for HMO_DTL_OLD_ID using the alias name HmoDtlOldId.
     * @return the HMO_DTL_OLD_ID
     */
    public BigDecimal getHmoDtlOldId() {
        return (BigDecimal) getAttributeInternal(HMODTLOLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for HMO_DTL_OLD_ID using the alias name HmoDtlOldId.
     * @param value value to set the HMO_DTL_OLD_ID
     */
    public void setHmoDtlOldId(BigDecimal value) {
        setAttributeInternal(HMODTLOLDID, value);
    }

    /**
     * Gets the attribute value for EXISTING_DATA using the alias name ExistingData.
     * @return the EXISTING_DATA
     */
    public String getExistingData() {
        return (String) getAttributeInternal(EXISTINGDATA);
    }

    /**
     * Sets <code>value</code> as attribute value for EXISTING_DATA using the alias name ExistingData.
     * @param value value to set the EXISTING_DATA
     */
    public void setExistingData(String value) {
        setAttributeInternal(EXISTINGDATA, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EvidenceCount.
     * @return the EvidenceCount
     */
    public String getEvidenceCount() {
        return (String) getAttributeInternal(EVIDENCECOUNT);
    }

    /**
     * Gets the attribute value for the calculated attribute HMOType.
     * @return the HMOType
     */
    public String getHMOType() {
        ViewRowImpl hdr_VO = this.getXxhrHmoHdr_VO();
        String hmoOption = hdr_VO.getAttribute("HmoType")!=null?hdr_VO.getAttribute("HmoType").toString():"";
        return hmoOption;
//        return (String) getAttributeInternal(HMOTYPE);
    }

    /**
     * Gets the attribute value for REASON_FOR_CHANGE using the alias name ReasonForChange.
     * @return the REASON_FOR_CHANGE
     */
    public String getReasonForChange() {
        return (String) getAttributeInternal(REASONFORCHANGE);
    }

    /**
     * Sets <code>value</code> as attribute value for REASON_FOR_CHANGE using the alias name ReasonForChange.
     * @param value value to set the REASON_FOR_CHANGE
     */
    public void setReasonForChange(String value) {
        setAttributeInternal(REASONFORCHANGE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HMOOption.
     * @return the HMOOption
     */
    public String getHMOOption() {
        ViewRowImpl hdr_VO = this.getXxhrHmoHdr_VO();
        String hmoOption = hdr_VO.getAttribute("HmoOption")!=null?hdr_VO.getAttribute("HmoOption").toString():"";
        return hmoOption;
//        return (String) getAttributeInternal(HMOOPTION);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link XxfndAttachment_EOView.
     */
    public RowIterator getXxfndAttachment_EOView() {
        return (RowIterator) getAttributeInternal(XXFNDATTACHMENT_EOVIEW);
    }

    /**
     * Gets the associated <code>ViewRowImpl</code> using master-detail link XxhrHmoHdr_VO.
     */
    public ViewRowImpl getXxhrHmoHdr_VO() {
        return (ViewRowImpl) getAttributeInternal(XXHRHMOHDR_VO);
    }

    /**
     * Sets the master-detail link XxhrHmoHdr_VO between this object and <code>value</code>.
     */
    public void setXxhrHmoHdr_VO(ViewRowImpl value) {
        setAttributeInternal(XXHRHMOHDR_VO, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LookupValues_DepTypes.
     */
    public RowSet getLookupValues_DepTypes() {
        return (RowSet) getAttributeInternal(LOOKUPVALUES_DEPTYPES);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LookupValues_Title.
     */
    public RowSet getLookupValues_Title() {
        return (RowSet) getAttributeInternal(LOOKUPVALUES_TITLE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> FetchHospital_ROVO.
     */
    public RowSet getFetchHospital_ROVO() {
        return (RowSet) getAttributeInternal(FETCHHOSPITAL_ROVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> StateOfResidence_ROVO.
     */
    public RowSet getStateOfResidence_ROVO() {
        return (RowSet) getAttributeInternal(STATEOFRESIDENCE_ROVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LookupValues_Gender.
     */
    public RowSet getLookupValues_Gender() {
        return (RowSet) getAttributeInternal(LOOKUPVALUES_GENDER);
    }
}

