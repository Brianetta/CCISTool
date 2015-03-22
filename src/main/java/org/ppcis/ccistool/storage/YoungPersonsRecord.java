package org.ppcis.ccistool.storage;

import org.ppcis.ccistool.Constants.ErrorStrings;
import org.ppcis.ccistool.Constants.UsefulData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public PersonalDetails personalDetails;
    public SeptemberGuarantee septemberGuarantee;
    public LevelOfNeed levelOfNeed;
    public Activities activities;
    private Integer intendedDestination;

    SimpleDateFormat dateFormatter; // Tool for decoding dates

    public YoungPersonsRecord() {
        personalDetails = new PersonalDetails();
        septemberGuarantee = new SeptemberGuarantee();
        levelOfNeed = new LevelOfNeed();
        activities = new Activities();
        dateFormatter = new SimpleDateFormat(UsefulData.DATE_FORMAT);
        dateFormatter.setLenient(false);
    }



    public void setIntendedDestination(Integer intendedDestination) {
        this.intendedDestination = intendedDestination;
    }

    public class PersonalDetails {
        private String youngPersonsID;
        private Character cohortStatus;
        private String givenName;
        private String middleName;
        private String familyName;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String addressLine4;
        private String county;
        private String town;
        private String postcode;
        private Character gender;
        private Date dob;
        private String ethnicity;
        private Integer leadLea;
        private Integer educatedLEA;
        private Integer transferredToLACode;
        private Integer LEACodeAtYear11;
        private Long uniqueLearnerNo;
        private String uniquePupilNumber;
        private Character guaranteeStatusIndicator;
        private Character youthContractIndicator;
        private Date youthContractStartDate;
        private String previousYPIDIdentifier;

        public void setYoungPersonsID(String youngPersonsID) {
            this.youngPersonsID = youngPersonsID;
        }

        public void setCohortStatus(String cohortStatus,FileHeader fileHeader) {
            try {
                this.cohortStatus = cohortStatus.charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                // Leave this as null
            }
            if (this.cohortStatus == 'X') {
                // NCCIS treat this data content error as a File Rejection, so we need that fileHeader instance
                fileHeader.addFileValidationError(ErrorStrings.ERR_INCORRECT_COHORT_STATUS);
            }
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

        public void setGender(String gender) {
            try {
                this.gender = gender.charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                // Leave this as null
            }
        }

        public void setDob(String dob) {
            try {
                // Enforce the date format
                if (dob.length() != UsefulData.DATE_FORMAT.length()) {
                    throw new ParseException(null, 0);
                }
                this.dob = dateFormatter.parse(dob);
            } catch (ParseException e) {
                // TODO Date format error (DOB)
            }
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public void setLeadLea(String leadLea) {
            try {
                this.leadLea = Integer.decode(leadLea);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setEducatedLEA(String educatedLEA) {
            try {
                this.educatedLEA = Integer.decode(educatedLEA);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setTransferredToLACode(String transferredToLACode) {
            try {
                this.transferredToLACode = Integer.decode(transferredToLACode);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setLEACodeAtYear11(String LEACodeAtYear11) {
            try {
                this.LEACodeAtYear11 = Integer.decode(LEACodeAtYear11);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setUniqueLearnerNo(String uniqueLearnerNo) {
            try {
                this.uniqueLearnerNo = Long.decode(uniqueLearnerNo);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setUniquePupilNumber(String uniquePupilNumber) {
            this.uniquePupilNumber = uniquePupilNumber;
        }

        public void setGuaranteeStatusIndicator(char guaranteeStatusIndicator) {
            this.guaranteeStatusIndicator = guaranteeStatusIndicator;
        }

        public void setYouthContractIndicator(char youthContractIndicator) {
            this.youthContractIndicator = youthContractIndicator;
        }

        public void setYouthContractStartDate(String youthContractStartDate) {
            try {
                // Enforce the date format
                if (youthContractStartDate.length() != UsefulData.DATE_FORMAT.length()) {
                    throw new ParseException(null, 0);
                }
                this.youthContractStartDate = dateFormatter.parse(youthContractStartDate);
            } catch (ParseException e) {
                // TODO Date format error (DOB)
            }
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public void setPreviousYPIDIdentifier(String previousYPIDIdentifier) {
            this.previousYPIDIdentifier = previousYPIDIdentifier;
        }
    }

    public class SeptemberGuarantee {
        public Guarantee year11;
        public Guarantee year12;

        public SeptemberGuarantee() {
            year12 = new Guarantee();
            year11 = new Guarantee();
        }

        public class Guarantee {
            private Integer guaranteeStatus;
            private Integer LEACode;

            public void setGuaranteeStatus(String status) {
                try {
                    this.guaranteeStatus = Integer.decode(status);
                } catch (NumberFormatException e) {
                    // Leave this as null
                }
            }

            public void setLEACode(String code) {
                try {
                    this.LEACode = Integer.decode(code);
                } catch (NumberFormatException e) {
                    // Leave this as null
                }
            }
        }
    }

    public class LevelOfNeed {
        private Integer levelOfNeedCode;
        private Boolean sendFlag;
        private ArrayList<Integer> Characteristics;

        public LevelOfNeed() {
            Characteristics = new ArrayList<>();
        }

        public void setLevelOfNeedCode(String levelOfNeedCode) {
            try {
                this.levelOfNeedCode = Integer.decode(levelOfNeedCode);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setSendFlag(String sendFlag) {
            try {
                // If the first character is a y (ignoring case) the flag is true, else false
                this.sendFlag = (sendFlag.charAt(0) == 'Y' || sendFlag.charAt(0) == 'y');
            } catch (StringIndexOutOfBoundsException e) {
                // Leave this as null
            }
        }

        public void addCharacteristic(String characteristic) {
            try {
                Characteristics.add(Integer.decode(characteristic));
            } catch (NumberFormatException e) {
                // TODO invalid characteristic
            }
        }
    }

    public class Activities {
        private Integer activityCode;
        private Date startDate;
        private Date dateAscertained;
        private Date dateVerified;
        private Date reviewDate;
        private Date dueToLapseDate;
        private Boolean currencyLapsed;
        private Integer establishmentNumber;
        private String establishmentName;
        private String UKProviderReferenceNumber;
        private Date NEETStartDate;
        private Date predictedEndDate;

        public void setActivityCode(String activityCode) {
            try {
                this.activityCode = Integer.decode(activityCode);
            } catch (NumberFormatException e) {
                //TODO invalid activity
            }
        }

        public void setStartDate(String startDate) {
            try {
                this.startDate = dateFormatter.parse(startDate);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setDateAscertained(String dateAscertained) {
            try {
                this.dateAscertained = dateFormatter.parse(dateAscertained);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setDateVerified(String dateVerified) {
            try {
                this.dateVerified = dateFormatter.parse(dateVerified);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setReviewDate(String reviewDate) {
            try {
                this.reviewDate = dateFormatter.parse(reviewDate);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setDueToLapseDate(String dueToLapseDate) {
            try {
                this.dueToLapseDate = dateFormatter.parse(dueToLapseDate);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setCurrencyLapsed(String currencyLapsed) {
            try {
                // If the first character is a y (ignoring case) the flag is true, else false
                this.currencyLapsed = (currencyLapsed.charAt(0) == 'Y' || currencyLapsed.charAt(0) == 'y');
            } catch (StringIndexOutOfBoundsException e) {
                // Leave this as null
            }
        }

        public void setEstablishmentNumber(String establishmentNumber) {
            try {
                this.establishmentNumber = Integer.decode(establishmentNumber);
            } catch (NumberFormatException e) {
                // Leave this as null
            }
        }

        public void setEstablishmentName(String establishmentName) {
            this.establishmentName = establishmentName;
        }


        public void setNEETStartDate(String NEETStartDate) {
            try {
                this.NEETStartDate = dateFormatter.parse(NEETStartDate);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setPredictedEndDate(String predictedEndDate) {
            try {
                this.predictedEndDate = dateFormatter.parse(predictedEndDate);
            } catch (ParseException e) {
                // Leave this as null
            }
        }

        public void setUKProviderReferenceNumber(String UKProviderReferenceNumber) {
            this.UKProviderReferenceNumber = UKProviderReferenceNumber;
        }
    }

    public void dumpSomeStuff() {
        System.out.println(String.format("%s: %s %s (%d)", personalDetails.youngPersonsID,personalDetails.givenName,personalDetails.familyName,activities.activityCode));
    }
}