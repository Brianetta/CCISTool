package org.ppcis.ccistool.storage;

import java.util.ArrayList;
import java.util.Date;
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
    private List<Integer> databases;
    private List<Integer> sourceLEAs;
    private Date dateOfSend;
    private Date periodEnd;
    private String supplierName;
    private String supplierXMLVersion;
    private String XMLSchemaVersion;

    public FileHeader() {
        sourceLEAs = new ArrayList<>();
        databases = new ArrayList<>();
    }

    public void addDatabase(Integer databaseID) {
        this.databases.add(databaseID);
    }

    public List<Integer> getDatabases() {
        return new ArrayList<Integer>(this.databases);
    }

    public void addSourceLea(Integer sourceLEA) {
        this.sourceLEAs.add(sourceLEA);
    }

    public List<Integer> getSourceLEAs()
    {
        return new ArrayList<Integer>(this.sourceLEAs);
    }

    public Date getDateOfSend() {
        return dateOfSend;
    }

    public void setDateOfSend(Date dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
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
}
