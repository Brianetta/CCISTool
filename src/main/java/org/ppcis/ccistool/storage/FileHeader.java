package org.ppcis.ccistool.storage;

import org.ppcis.ccistool.Constants.ErrorStrings;
import org.ppcis.ccistool.Constants.UsefulData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class FileHeader {
    private List<String> fileValidationErrors;
    private List<Integer> databases;
    private List<Integer> sourceLEAs;
    private LocalDate dateOfSend;
    private LocalDate periodEnd;
    private String supplierName;
    private String supplierXMLVersion;
    private String XMLSchemaVersion;

    SimpleDateFormat dateFormatter; // Tool for decoding dates

    public FileHeader() {
        sourceLEAs = new ArrayList<>();
        databases = new ArrayList<>();
        fileValidationErrors = new ArrayList<>();
        dateFormatter = new SimpleDateFormat(UsefulData.DATE_FORMAT);
        dateFormatter.setLenient(false);
    }

    public void addDatabase(String databaseID) {
        if (databaseID == null || databaseID.length() == 0 ) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_DBIDS);
        } else try {
            this.databases.add(Integer.decode(databaseID));
        } catch (NumberFormatException e) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_DBIDS + ": " + databaseID);
        }
    }

    public List<Integer> getDatabases() {
        return new ArrayList<Integer>(this.databases);
    }

    public void addSourceLea(String sourceLEA) {
        if (sourceLEA == null || sourceLEA.length() == 0 ) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_FHLEA);
        } else try {
            this.sourceLEAs.add(Integer.decode(sourceLEA));
        } catch (NumberFormatException e) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_FHLEA + ": " + sourceLEA);
        }
    }

    public List<Integer> getSourceLEAs()
    {
        return new ArrayList<Integer>(this.sourceLEAs);
    }

    public LocalDate getDateOfSend() {
        return dateOfSend;
    }

    public void setDateOfSend(String dateOfSend) {
        try {
            // Enforce the date format
            if (dateOfSend.length() != UsefulData.DATE_FORMAT.length()) {
                throw new ParseException(null, 0);
            }
            this.dateOfSend = LocalDate.parse(dateOfSend);
        } catch (ParseException e) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_DATE_SEND + ": " + dateOfSend);
        }
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        try {
            // Enforce the date format
            if (periodEnd.length() != UsefulData.DATE_FORMAT.length()) {
                throw new ParseException(null, 0);
            }
            this.periodEnd = LocalDate.parse(periodEnd);
        } catch (ParseException e) {
            this.addFileValidationError(ErrorStrings.ERR_INVALID_PERIODEND + ": " + periodEnd);
        }
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierXMLVersion() {
        return supplierXMLVersion;
    }

    public void setSupplierXMLVersion(String supplierXMLVersion) {
        this.supplierXMLVersion = supplierXMLVersion;
    }

    public String getXMLSchemaVersion() {
        return XMLSchemaVersion;
    }

    public void setXMLSchemaVersion(String XMLSchemaVersion) {
        this.XMLSchemaVersion = XMLSchemaVersion;
    }

    public void addFileValidationError(String fileValidationError) {
        this.fileValidationErrors.add(fileValidationError);
    }

    public List<String> getFileValidationErrors() {
        return fileValidationErrors;
    }

    public String getDatabaseIDs() {
        StringBuilder tempIDList = new StringBuilder();
        for (int databaseID : databases) {
            if (tempIDList.length() > 0) {
                tempIDList.append(", ");
            }
            tempIDList.append(String.valueOf(databaseID));
        }
        return tempIDList.toString();
    }

    public void storeInDatabase() {
        Database database = new Database();
        database.storeFileHeader(this);
    }
}
