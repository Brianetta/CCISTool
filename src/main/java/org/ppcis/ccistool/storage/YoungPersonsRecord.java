package org.ppcis.ccistool.storage;

import java.util.ArrayList;
import java.util.Date;

/**
 * Copyright © Brian Ronald
 * 22/02/15
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class YoungPersonsRecord {
    private PersonalDetails personalDetails;
    private SeptemberGuarantee septemberGuarantee;
    private LevelOfNeed levelOfNeed;
    private Activities activities;
    private Integer intendedDestination;

    class PersonalDetails {
        private Integer youngPersonsID;
        private char cohortStatus;
        private String givenName;
        private String middleName;
        private String familyName;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String addressLine4;
        private String town;
        private String postcode;
        private char gender;
        private Date dob;
        private String ethnicity;
        private Integer leadLea;
        private Integer educatedLEA;
        private Integer transferredToLACode;
        private Integer LEACodeAtYear11;
        private Integer uniqueLearnerNo;
        private Integer uniquePupilNumber;
        private char guaranteeStatusIndicator;
        private char youthContractIndicator;
        private Date youthContractStartDate;

        public void setYoungPersonsID(Integer youngPersonsID) {
            this.youngPersonsID = youngPersonsID;
        }

        public void setCohortStatus(char cohortStatus) {
            this.cohortStatus = cohortStatus;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public void setAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
        }

        public void setAddressLine4(String addressLine4) {
            this.addressLine4 = addressLine4;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        public void setDob(Date dob) {
            this.dob = dob;
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public void setLeadLea(Integer leadLea) {
            this.leadLea = leadLea;
        }

        public void setEducatedLEA(Integer educatedLEA) {
            this.educatedLEA = educatedLEA;
        }

        public void setTransferredToLACode(Integer transferredToLACode) {
            this.transferredToLACode = transferredToLACode;
        }

        public void setLEACodeAtYear11(Integer LEACodeAtYear11) {
            this.LEACodeAtYear11 = LEACodeAtYear11;
        }

        public void setUniqueLearnerNo(Integer uniqueLearnerNo) {
            this.uniqueLearnerNo = uniqueLearnerNo;
        }

        public void setUniquePupilNumber(Integer uniquePupilNumber) {
            this.uniquePupilNumber = uniquePupilNumber;
        }

        public void setGuaranteeStatusIndicator(char guaranteeStatusIndicator) {
            this.guaranteeStatusIndicator = guaranteeStatusIndicator;
        }

        public void setYouthContractIndicator(char youthContractIndicator) {
            this.youthContractIndicator = youthContractIndicator;
        }

        public void setYouthContractStartDate(Date youthContractStartDate) {
            this.youthContractStartDate = youthContractStartDate;
        }
    }

    class SeptemberGuarantee {
        public SeptemberGuarantee() {
            year12 = new Guarantee();
            year11 = new Guarantee();
        }

        public void setYoungPersonsID(Integer youngPersonsID) {
            this.youngPersonsID = youngPersonsID;
        }

        class Guarantee {
            private Integer guaranteeStatus;
            private Integer LEACode;

            void setGuaranteeStatus(Integer status) {
                this.guaranteeStatus = status;
            }

            void setLEACode(Integer code) {
                this.LEACode = code;
            }
        }
        Guarantee year11;
        Guarantee year12;
        private Integer youngPersonsID;
    }

    class LevelOfNeed {
        private Integer youngPersonsID;
        private Integer levelOfNeedCode;
        private Boolean sendFlag;
        private ArrayList<Integer> Characteristics;

        public void setLevelOfNeedCode(Integer levelOfNeedCode) {
            this.levelOfNeedCode = levelOfNeedCode;
        }

        public void setSendFlag(Boolean sendFlag) {
            this.sendFlag = sendFlag;
        }

        public void addCharacteristic(Integer characteristic) {
            Characteristics.add(characteristic);
        }
    }

    class Activities {
        private Integer youngPersonsID;
        private Integer activityCode;
        private Date startDate;
        private Date dateAscertained;
        private Date dateVerified;
        private Date reviewDate;
        private Date dueToLapseDate;
        private Boolean currencyLapsed;
        private Integer establishmentNumber;
        private String establishmentName;
        private Date NEETStartDate;
        private Date predictedEndDate;

        public void setYoungPersonsID(Integer youngPersonsID) {
            this.youngPersonsID = youngPersonsID;
        }

        public void setActivityCode(Integer activityCode) {
            this.activityCode = activityCode;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public void setDateAscertained(Date dateAscertained) {
            this.dateAscertained = dateAscertained;
        }

        public void setDateVerified(Date dateVerified) {
            this.dateVerified = dateVerified;
        }

        public void setReviewDate(Date reviewDate) {
            this.reviewDate = reviewDate;
        }

        public void setDueToLapseDate(Date dueToLapseDate) {
            this.dueToLapseDate = dueToLapseDate;
        }

        public void setCurrencyLapsed(Boolean currencyLapsed) {
            this.currencyLapsed = currencyLapsed;
        }

        public void setEstablishmentNumber(Integer establishmentNumber) {
            this.establishmentNumber = establishmentNumber;
        }

        public void setEstablishmentName(String establishmentName) {
            this.establishmentName = establishmentName;
        }

        public void setNEETStartDate(Date NEETStartDate) {
            this.NEETStartDate = NEETStartDate;
        }

        public void setPredictedEndDate(Date predictedEndDate) {
            this.predictedEndDate = predictedEndDate;
        }
    }
}
