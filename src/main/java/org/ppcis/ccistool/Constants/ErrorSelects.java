package org.ppcis.ccistool.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © Brian Ronald
 * 09/05/15
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
public class ErrorSelects {
    public static final Map<Integer,String> SQL = new HashMap<>(153);
    static {
        SQL.put(1, "SELECT YoungPersonsID,1 FROM YoungPersonsRecord WHERE LENGTH(YoungPersonsID)<>13");
        SQL.put(2, "SELECT YoungPersonsID,2 FROM YoungPersonsRecord WHERE GivenName IS NULL");
        SQL.put(3, "SELECT YoungPersonsID,3 FROM YoungPersonsRecord WHERE FamilyName IS NULL");
        SQL.put(4, "SELECT YoungPersonsID,4 FROM YoungPersonsRecord WHERE GENDER IS NULL");
        SQL.put(5, "SELECT YoungPersonsID,5 FROM YoungPersonsRecord WHERE GENDER NOT IN ('F','M','U','N')");
        SQL.put(6, "SELECT YoungPersonsID,6 FROM YoungPersonsRecord WHERE ETHNICITY IS NULL");
        SQL.put(7, "SELECT YoungPersonsID,7 FROM YoungPersonsRecord WHERE ETHNICITY NOT IN (\n" +
                "'WBRI','WIRI','WROM','WOTH'," +
                "'MWBC','MWBA','MWAS','MOTH'," +
                "'AIND','APKN','ABAN','CHNE','AOTH'," +
                "'BCRB','BAFR','BOTH'," +
                "'OARA','OOTH'," +
                "'REFU','NOBT'" +
                ")");
        SQL.put(8, "SELECT YoungPersonsID,8 FROM YoungPersonsRecord WHERE DOB IS NULL");
        SQL.put(9, "SELECT YoungPersonsID,9 FROM YoungPersonsRecord WHERE DATE(DOB,'+25 year') < '2015-08-31'");
        SQL.put(10, "SELECT YoungPersonsID,10 FROM YoungPersonsRecord WHERE DATE(DOB,'+15 year') > '2015-08-31'");
        SQL.put(11, "SELECT YoungPersonsID,11 FROM YoungPersonsRecord WHERE CohortStatus IS NULL");
        SQL.put(13, "SELECT YoungPersonsID,13 FROM YoungPersonsRecord WHERE CohortStatus = 'T' AND TransferredToLACode IS NULL");
        SQL.put(14, "SELECT YoungPersonsID,14 FROM YoungPersonsRecord WHERE LeadLEA IS NULL");
        SQL.put(15, "SELECT YoungPersonsID,15 FROM YoungPersonsRecord WHERE LeadLEA NOT IN (SELECT LEANo FROM LEA)");
        SQL.put(19, "SELECT YoungPersonsID,19 FROM YoungPersonsRecord WHERE EducatedLEA NOT IN (SELECT LEANo FROM LEA) AND EducatedLEA NOT IN (000,001,002,003,004)");
        SQL.put(24, "SELECT YoungPersonsID,24 FROM YoungPersonsRecord WHERE LEACodeAtYear11 NOT IN (SELECT LEANo FROM LEA) AND LEACodeAtYear11 NOT IN (000,001,002,003,004)");
        SQL.put(25, "SELECT YoungPersonsID,25 FROM YoungPersonsRecord WHERE LEACodeAtYear11 <> LEACodeY11");
        SQL.put(26, "SELECT YoungPersonsID,26 FROM YoungPersonsRecord WHERE LeadLEA NOT IN (%s)"); // String parameter is comma separated list of LEA numbers
        SQL.put(27, "SELECT YoungPersonsID,27 FROM YoungPersonsRecord WHERE GuaranteeStatusY11 NOT IN (110,124,120,122,123,140,150,151,153,154,159)" +
                " UNION ALL " +
                "SELECT YoungPersonsID,27 FROM YoungPersonsRecord WHERE GuaranteeStatusY12 NOT IN (110,114,115,124,120,122,123,140,150,151,153,154,159);)");
        SQL.put(29, "SELECT YoungPersonsID,29 FROM YoungPersonsRecord WHERE YouthContractIndicator='Y' AND YouthContractStartDate IS NULL");
        SQL.put(30, "SELECT YoungPersonsID,30 FROM YoungPersonsRecord WHERE UniqueLearnerNo NOT GLOB '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'");
        SQL.put(31, "SELECT YoungPersonsID,31 FROM YoungPersonsRecord WHERE AddressLine1 IS NULL AND AddressLine2 IS NULL AND AddressLine3 IS NULL AND AddressLine4 IS NULL AND Town IS NULL AND County IS NULL");
        SQL.put(32, "SELECT YoungPersonsID,32 FROM YoungPersonsRecord WHERE Postcode IS NULL");
        SQL.put(33, "SELECT YoungPersonsID,33 FROM YoungPersonsRecord WHERE DATE(DOB,'+19 year') > '2015-08-31' AND SENDFlag IS NULL");
        SQL.put(34, "SELECT YoungPersonsID,34 FROM YoungPersonsRecord WHERE CohortStatus <> 'T' AND TransferredToLACode IS NOT NULL");
        SQL.put(35, "SELECT GivenName,YoungPersonsID,35 FROM YoungPersonsRecord WHERE " +
                "(CohortStatus = 'T' AND TransferredToLACode NOT IN (SELECT LEANo FROM LEA)) OR " +
                "(CohortStatus = 'E' AND TransferredToLACode NOT IN (000,001,002,003))");
        SQL.put(36, "SELECT YoungPersonsID,36 FROM YoungPersonsRecord WHERE YouthContractIndicator NOT IN ('Y','N')");
        SQL.put(37, "SELECT YoungPersonsID,37 FROM YoungPersonsRecord WHERE LENGTH(PreviousYPIDIdentifier) <> 13");
        SQL.put(38, "SELECT YoungPersonsID,38 FROM YoungPersonsRecord WHERE LENGTH(UniquePupilNumber) <> 13");
        SQL.put(39, "SELECT YoungPersonsID,39 FROM YoungPersonsRecord WHERE UKProviderReferenceNumber NOT GLOB '1[0-9][0-9][0-9][0-9][0-9][0-9][0-9]'");
        SQL.put(40, "SELECT YoungPersonsID,40 FROM YoungPersonsRecord WHERE DATE(DOB,'+19 year') > '2015-08-31' AND SENDFlag IS NULL");
        SQL.put(41, "SELECT YoungPersonsID,41 FROM YoungPersonsRecord WHERE TransferredToLACode=004");
        SQL.put(42, "SELECT YoungPersonsID,42 FROM YoungPersonsRecord WHERE TransferredToLACode=LeadLEA");
        SQL.put(43, "SELECT YoungPersonsID,43 FROM YoungPersonsRecord WHERE " +
                "Postcode NOT GLOB '[A-z][0-9] [0-9][A-z][A-z]' AND " +
                "Postcode NOT GLOB '[A-z][A-z][0-9] [0-9][A-z][A-z]' AND " +
                "Postcode NOT GLOB '[A-z][0-9][A-z] [0-9][A-z][A-z]' AND " +
                "Postcode NOT GLOB '[A-z][0-9][0-9] [0-9][A-z][A-z]' AND " +
                "Postcode NOT GLOB '[A-z][A-z][0-9][A-z] [0-9][A-z][A-z]' AND " +
                "Postcode NOT GLOB '[A-z][A-z][0-9][0-9] [0-9][A-z][A-z]'");
        SQL.put(100, "SELECT YoungPersonsID,100 FROM YoungPersonsRecord WHERE LevelOfNeedCode IS NULL");
        SQL.put(101, "SELECT YoungPersonsID,101 FROM YoungPersonsRecord WHERE LevelOfNeedCode NOT BETWEEN 1 AND 3");
        SQL.put(102, "SELECT YoungPersonsID,102 FROM YoungPersonsRecord WHERE SENDFlag NOT IN ('Y','N')");
        SQL.put(103, "SELECT YoungPersonsID,103 FROM YoungPersonsRecord WHERE SENDFlag IS NULL");
        SQL.put(104, "SELECT YoungPersonsID,104 FROM Characteristic WHERE CharacteristicCode NOT IN (110,120,130,140,150,160,170,180,190) GROUP BY YoungPersonsID");
        SQL.put(200, "SELECT YoungPersonsID,200 FROM YoungPersonsRecord WHERE ActivityCode IS NULL");
        SQL.put(201, "SELECT YoungPersonsID,201 FROM YoungPersonsRecord WHERE ActivityCode NOT IN (110,120,130,140,150," +
                "210,220,230,240,250,260,270,280,290," +
                "310,320,330,340,350,360,380,381,550," +
                "410,430,440,450,460," +
                "530," +
                "540,610,615,616,619,620,630,640,650,660,670,680," +
                "710,720," +
                "810,820,830)");
        SQL.put(202, "SELECT YoungPersonsID,202 FROM YoungPersonsRecord WHERE StartDate IS NULL");
        SQL.put(203, "SELECT YoungPersonsID,203 FROM YoungPersonsRecord WHERE DateAscertained IS NULL");
        SQL.put(220, "SELECT YoungPersonsID,220 FROM YoungPersonsRecord WHERE CurrencyLapsed IS NULL");
        SQL.put(221, "SELECT YoungPersonsID,221 FROM YoungPersonsRecord WHERE CurrencyLapsed NOT IN ('Y','N')");
    }
}