package org.ppcis.ccistool.Constants;

/**
 * Copyright © Brian Ronald
 * 04/03/15
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
public class ErrorStrings {
    public static final String ERR_NO_FILEHEADER = "XML submission does not contain a FileHeader node";
    public static final String ERR_NO_ROOT_CLOSE = "Cannot find closing root node in the XML submission";
    public static final String ERR_MULTIPLE_ROOT = "More than one root node in the XML submission";
    public static final String ERR_INVALID_DBIDS = "Invalid databaseIDs found in FileHeader";
    public static final String ERR_INVALID_FHLEA = "Invalid LEA value found in FileHeader";
    public static final String ERR_INVALID_DATE_SEND = "Invalid DateOfSend";
    public static final String ERR_INVALID_PERIODEND = "Invalid PeriodEnd";
    public static final String ERR_UNEXPECTED_DATA = "Unexpected data in node";
    public static final String XML_ERROR_AT_LINE_D = "XML error at line %d";
    public static final String XML_DATA_ERROR = "Trouble with XML data";
    public static final String XML_FILE_ERROR = "Trouble with XML file";
}
