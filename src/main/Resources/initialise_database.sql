DROP TABLE IF EXISTS ErrorDef;;

CREATE TABLE ErrorDef (
	ErrorCode INT PRIMARY KEY,
	Description VARCHAR,
	Priority INT,
	Explanation VARCHAR
);;

DROP TABLE IF EXISTS Lea;;

CREATE TABLE Lea (
	LEANo INT PRIMARY KEY,
	Name VARCHAR
);;

CREATE TABLE IF NOT EXISTS FileHeader (
    DatabaseID INT,
    SourceLEA INT,
    DateOfSend DATE,
    PeriodEnd DATE,
    SupplierName VARCHAR,
    SupplierXMLVersion VARCHAR,
    XMLSchemaVersion VARCHAR
);;

CREATE TABLE IF NOT EXISTS YoungPersonsRecord (
	YoungPersonsID VARCHAR,
	GivenName VARCHAR,
	FamilyName VARCHAR,
	MiddleName VARCHAR,
	Gender VARCHAR,
	DOB DATE,
	LeadLEA INT,
	CohortStatus VARCHAR,
	LEACodeAtYear11 INT,
	TransferredToLACode INT,
	AddressLine1 VARCHAR,
	AddressLine2 VARCHAR,
	AddressLine3 VARCHAR,
	AddressLine4 VARCHAR,
	Town VARCHAR,
	County VARCHAR,
	Postcode VARCHAR,
	Ethnicity VARCHAR,
	EducatedLEA INT,
	UniqueLearnerNo INT,
	SENDFlag VARCHAR,
	GuaranteeStatusIndicator VARCHAR,
	YouthContractIndicator VARCHAR,
	YouthContractStartDate DATE,
	PreviousYPIDIdentifier VARCHAR,
	EstablishmentNumber VARCHAR,
	UniquePupilNumber VARCHAR,
	EstablishmentName VARCHAR,
	UKProviderReferenceNumber VARCHAR,
	ActivityCode INT,
	StartDate DATE,
	DateAscertained DATE,
	DateVerified DATE,
	ReviewDate DATE,
	DueToLapseDate DATE,
	CurrencyLapsed VARCHAR,
	LevelOfNeedCode VARCHAR,
	NEETStartDate DATE,
	PredictedEndDate DATE,
	IntendedDestinationYr11 INT,
	GuaranteeStatusY11 INT,
	LEACodeY11 INT,
	GuaranteeStatusY12 INT,
	LEACodeY12 INT
);;

CREATE TABLE IF NOT EXISTS Characteristic (
	YoungPersonsID VARCHAR,
	CharacteristicCode VARCHAR
);;

DROP TABLE IF EXISTS ErrorFound;;

CREATE TABLE ErrorFound (
	YoungPersonsID VARCHAR,
	ErrorCode INT
);;

CREATE TABLE IF NOT EXISTS PreviousError (
	YoungPersonsID VARCHAR,
	ErrorCode INT,
	PeriodEnd DATE
);;

CREATE TABLE IF NOT EXISTS PreviousCohort (
	YoungPersonsID VARCHAR,
	CohortStatus VARCHAR
);;

INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (1,'‘YoungPersonsID’ not of the correct length digits',1,'The young person’s identifier must contain 13 the 3 digit DatabaseID followed by the local CCIS ID, and with padded 0s. eg 4440000123456');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (2,'‘GivenName’ does not contain a value',1,'The young person’s given name is missing');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (3,'‘FamilyName’ does not contain a value',1,'The young person’s family name is missing');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (4,'‘Gender’ does not contain a value',2,'The young person’s gender has not been recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (5,'‘Gender’ does not contain a recognised value',2,'The value input is not valid – see YP07 for a list of valid entries');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (6,'‘Ethnicity’ does not contain a value',2,'The young person’s ethnicity has not been recorded on CCIS');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (7,'‘Ethnicity’ does not contain a recognised value',2,'The value input is not valid – see YP27 for a list of valid entries');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (8,'‘DOB’ does not contain a value',1,'The young person’s date of birth has not been recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (9,'‘DOB’ contains a value which makes the young person over the academic age of 25',2,'This error has occurred either because the young person’s date of birth has been entered incorrectly, or because the young person has reached the end of the academic year in which they had their 25th birthday and is, therefore, no longer in the cohort to be returned in the XML');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (10,'‘DOB’ contains a value which makes the young person below the academic age of 15',2,'This error has occurred either because the young person’s date of birth has been entered incorrectly, or because the young person has not yet reached the academic age of 15 and is, therefore, outside the cohort to be returned in the XML');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (11,'‘CohortStatus’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (13,'‘Cohort Status’ of T but no value in ‘TransferredToLEACode’',2,'Where a young person’s record has been transferred to another local authority, the LA code of the importing authority must be recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (14,'Mandatory field ‘LeadLEA’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (15,'‘LeadLEA’ does not contain a recognised value',1,'The ‘LeadLEA’ code that has been entered on CCIS is not one of the recognised LA codes – see appendix B for valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (19,'‘EducatedLEA’ does not contain a recognised value',2,'The code shown in the XML is not one of the recognised LA codes – see appendix B for valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (24,'‘LEACodeAtYear11’ does not contain a recognised value',2,'The code shown in the XML is not one of the recognised LA codes – see appendix B for valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (25,'‘LEACodeAtYear11’ does not equal the Year 11 Offer LEACode',1,'These two codes must have the same value');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (26,'‘LeadLEA’ does not contain the LEA specified in the XML return Header',2,'The XML should only include young people for whom the service has lead responsibility. Secondary registrations must not be returned in the XML');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (27,'‘GuaranteeStatusIndicator’ does not contain a recognised value',1,'The value input is not a valid entry – see SG02 or SG11 for a list of valid entries');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (29,'‘YouthContactStartDate’ must contain a value as ‘YouthContractIndicator’ is Y',2,'The Youth Contract Indicator shows that the young person is participating in the Youth Contract, but their start date has not been input');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (30,'‘UniqueLearnerNo’ invalid format',2,'The ULN should contain 10 numbers. If it is too short, too long, or contains letters an error will be returned');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (31,'‘Address’ fields do not contain any data',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (32,'‘Postcode’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (33,'YP aged 20+ but without SEND',2,'Information about young people aged 20 or over should only be included in the XML if the young person has a SEND flag');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (34,'‘TransferredToLACode’ exists but ‘CohortStatus’ is not ‘T’',2,'Where a young person’s record has been transferred to another local authority and the LA code of the importing authority is recorded, the ‘CohortStatus’ must be recorded as ‘T’');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (35,'‘TransferredToLACode’ is not a recognised value',2,'The code shown in the XML is not one of the recognised LA codes – see appendix B for valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (36,'‘YouthContractIndicator’ does not contain a recognised value',2,'The Youth Contract Indicator shows that the young person is participating in the Youth Contract. Recognised values are ‘Y’ and ‘N’');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (37,'‘PreviousYPIDIdentifier’ not of the correct length.',1,'This should be the full 13-digit identifier made up of the 3-digit database code plus the 10-digit unique number previously allocated to that young person');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (38,'‘UniquePupilNumber’ not of correct length',2,'The unique pupil number (UPN) identifies each pupil in England and must be a unique 13 digit number, including the LA code and DfE establishment number of the school/academy allocating the UPN, and year of allocation and 3 digit serial number');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (39,'‘UKProviderReferenceNumber’ is not the correct format',2,'This should be an 8 digit number starting with 1');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (40,'‘DOB’ contains a value which makes the young person without a SEND flag over the age of 20',1,'20-25 year olds should only be included in the XML extract if they have a current Education, Health and Care (EHC) plan or a Learning Difficulty Assessment (LDA)');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (41,'‘TransferredToLACode’ returned is 004 which is not valid',1,'No young person should be transferred to an unknown LA');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (42,'‘TransferredToLACode’ should not be the same as the Lead LA code',1,'Services should not be transferring a young person to themselves');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (43,'‘Postcode’ is not in a recognised format',2,'Postcode should meet the required format YP19');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (100,'‘LevelOfNeedCode’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (101,'‘LevelOfNeedCode" does not contain a recognised value',2,'The value in the XML is not valid– see CA13 for a list of valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (102,'‘SENDFlag’ does not contain a recognised value',2,'Recognised values are ‘Y’ and ‘N’');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (103,'‘SENDFlag’does not contain a value',2,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (104,'‘CharacteristicCode’ does not contain a recognised value',2,'The characteristic type shown in the XML is not valid – see IC01 for a list of valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (200,'Mandatory field ‘ActivityCode’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (201,'‘ActivityCode’ does not contain a recognised value',1,'The activity code input is not one of the valid codes listed in CA01');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (202,'‘StartDate’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (203,'‘DateAscertained’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (220,'‘CurrencyLapsed’ does not contain a value',1,'This field must be completed for every record');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (221,'‘CurrencyLapsed’ does not contain a recognised value',1,'Recognised values are ‘Y’ and ‘N’');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (224,'No value in ‘NEETStartDate’ for a Young Person with a current NEET Activity',1,'The young person’s current activity is NEET, but the date on which they became NEET has not been entered');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (225,'‘NEETStartDate’ is after the young person’s current Activity ‘StartDate’',1,'The young person’s NEET start date must always be the same as or earlier than their current activity start date');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (226,'‘NEETStartDate’ should not be populated for a young person whose current Activity is not NEET',1,'A NEET start date should only be recorded if the young person’s current activity is NEET. Please check the young person’s current activity and update it to NEET if appropriate');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (228,'‘EstablishmentNumber’ does not contain a valid value',1,'Length must be 7 digits; the 3 digit LA code followed by the 4 digit DfE Number. Where a young person is educated at home or there is no valid DfE number, use the relevant LA code followed by 9999');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (229,'Young person is not old enough to have a post-16 Activity Code',1,'Either the young person’s date of birth has been input incorrectly, or they have been given a current activity code that is not appropriate to their age.');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (230,'Young person is too old for a compulsory education activity',1,'Either the young person’s date of birth has been input incorrectly, or they have been given a current activity code that is not appropriate to their age');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (231,'Field ‘CurrencyLapsed’ is Y but ‘DueToLapseDate’ is greater than ‘Period End Date’',2,'The young person’s record is marked as having lapsed but the ‘DueToLapseDate’ is after the period end date of the XML submission');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (232,'Field ‘CurrencyLapsed’ is N but ‘DueToLapseDate’ is less than ‘Period End Date’',2,'Record is marked as NOT being lapsed but the ‘DueToLapseDate’ is before the period end date for the XML submission');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (233,'Activity of young carer without supporting characteristic',2,'The young person’s current activity is recorded as ‘NEET – young carer’ so characteristic type of 140 should also be recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (234,'Activity of teenage parent without supporting characteristic',2,'The young person’s current activity is recorded as ‘NEET – caring for own child’ so characteristic type 120 should also be recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (235,'Activity of pregnancy without supporting characteristic',2,'The young person’s current activity is recorded as ‘NEET – pregnant, so characteristic type 180 should also be recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (236,'Activity of refugee/asylum seeker without supporting characteristic',2,'The young person’s current activity is recorded as ‘Refugee/asylum seeker’ so characteristic type 130 should also be recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (237,'‘DateAscertained’ is greater than ‘DateVerified’',2,'The date on which a record was created should never be greater (later) that the date on which the young person’s activity was last verified');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (238,'‘DueToLapseDate’ is greater than ‘PredictedEndDate’',2,'The currency of a young person’s record must never extend beyond their expected course end date');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (242,'‘DateAscertained’ is less than ‘StartDate’',2,'The date on which a record was created should never be less than the date the young person’s activity started');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (243,'‘DueToLapseDate’ does not contain a value',2,'The date on which the young person’s current activity is due to lapse is missing');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (244,'‘DueToLapseDate’ does not contain a recognised value',2,'The date on which the young person’s current activity is not of the format CCYY-MM-DD');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (251,'‘NEETStartDate’ is before the young person’s 15th Birthday',2,'A young person of this age should not have a NEET activity, therefore should not have a NEETStartDate');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (253,'Activity of Work Programme not allowed for 16 and 17 year olds',2,'The young person is 16 or 17 so shouldn’t have an activity code of 440');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (254,'Activity of ‘Independent Specialist Provider’ without SEND Flag of ‘Y’',1,'This activity is only valid for young people with a SEND');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (255,'Activity of ‘Supported Internship’ without SEND Flag of “Y”',1,'This activity is only valid for young people with a SEND');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (256,'‘DateVerified’ is less than ‘StartDate’',1,'The date on which a young person’s activity was last verified should never be before the start date of their current activity');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (257,'‘ReviewDate’ is greater than the ‘PredictedEndDate’',2,'The young person’s current activity review date must not extend beyond their course end date');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (258,'‘PredictedEndDate’ doesn’t contain a recognised value',1,'This error is most likely to have occurred if the course end date has been input incorrectly');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (259,'Young person’s current activity is education or training without a ‘PredictedEndDate’',1,'The course end date is mandatory for all education and training destinations except apprenticeships');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (260,'‘CharacteristicCode’ is 180 (Pregnant) but Gender is recorded as Male',2,'Either the characteristic needs removing or the gender correcting');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (261,'Guarantee ‘LEACode’ is not a recognised value (used for year 11 and 12)',1,'Valid LA codes can be found in appendix B.');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (262,'‘ActivityCode’ is 260, 290, 350, 710 or 720 and the ‘ReviewDate’ doesn’t contain a value',1,'All young people in temporary employment, gap year, custody, asylum seekers or those with an agreed start date should have a current activity review date');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (301,'‘IntendedDestinationYr11’ does not have a value',2,'Data missing');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (302,'‘IntendedDestinationYr11’ does not contain a recognised value',2,'The value given in the XML is not valid– see ID01 for a list of valid codes');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (902,'Duplicate ‘YoungPersonsID’ found',1,'The same value for ‘YoungPersonsID’ was found more than once in the XML file. If both records are for the same young person, they may be merged. If they are for different young people, then the young person’s identifier should be amended as appropriate');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (905,'Young person in Guarantee cohort but either ‘GuaranteeStatus’ or ‘LEACode’ is missing from the ‘September Guarantee’ node',1,'The young person’s cohort status indicates that they are covered by the September Guarantee, but neither their guarantee status, nor LA code, have been recorded');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (906,'‘GuaranteeStatusIndicator’ = Y but either ‘GuaranteeStatus’ or ‘LEACode’ is missing from The September Guarantee node',1,'The ‘GuaranteeStatusIndicator’ has been marked as ‘Y’ for this young person but either the ‘GuaranteeStatus’ code or ‘LEACode’ for this record is missing');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (907,'‘ULN’ used more than once for different young person records',1,'Check that the ULN has been input correctly, and if in doubt, please remove the ULN as having an incorrect ULN causes more problems');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (908,'Suspected duplicate young person found by ‘GivenName’,  ‘FamilyName’ and ‘DOB’',3,'This error won’t count in the monthly error reports but indicates to the service that they have potential duplicates on their system');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (240,'Node Activities not found in young person’s record',1,'‘Activities’ node missing from XML file. File will be rejected and marked as ‘Failed’. Notification will be sent');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (903,'‘YoungPersonsID’ does not contain a value that is specified as the ‘DatabaseID’ in the XML FileHeader',1,'The first three digits of the ‘YoungPersonsID’ must always be the same as the ‘DatabaseID’ returned in the FileHeader');;
INSERT INTO ErrorDef (ErrorCode,Description,Priority,Explanation) VALUES (904,'The field ‘CohortStatus’ contains an unrecognised value, the file will fail validation',1,'See YP10 for a list of recognised values');;
