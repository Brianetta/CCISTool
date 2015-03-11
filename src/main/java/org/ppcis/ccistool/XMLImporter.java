package org.ppcis.ccistool;

import org.ppcis.ccistool.Constants.ErrorStrings;
import org.ppcis.ccistool.storage.FileHeader;
import org.ppcis.ccistool.storage.YoungPersonsRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright © Brian Ronald
 * 21/02/15
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

public class XMLImporter extends DefaultHandler {
    // NCCIS sets the format of dates to be thus
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private Stack<String> currentNode = new Stack<>();
    private StringBuilder currentContent;
    private String rootNode;
    private FileHeader fileHeader;
    private YoungPersonsRecord currentYoungPersonsRecord;
    // A flag to track the number of fileHeaders seen. One bit should be big enough.
    private boolean fileHeaderSeen = false;
    // A pair of flags for keeping track of which data structure we're importing,
    // because some tags (like SourceLEA) appear in both
    private boolean fileHeaderImport = false;
    private boolean youngPersonsRecordImport = false;
    private boolean personalDetailsFound = false;

    public XMLImporter() {
        fileHeader = new FileHeader();
    }

    FileHeader importXMLWithFix(String filename) {
        File tempFile;
        Scanner inputScanner;
        BufferedWriter tempFileWriter;

        try {
            tempFile = File.createTempFile("CCISTool", ".tmp");
            tempFile.deleteOnExit();
            System.out.println("Temporary file: " + tempFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            tempFileWriter = new BufferedWriter(new FileWriter(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            inputScanner = new Scanner(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        while (inputScanner.hasNext()) {
            // Fix each line, turning ampersands into the ampersand entity
            try {
                tempFileWriter.write(inputScanner.nextLine().replaceAll("&","&amp;"));
                tempFileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
                fileValidationError("Error processing file");
            }
        }
        try {
            tempFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return importXML(tempFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            fileValidationError("Temp file died");
            return null;
        }
    }

    FileHeader importXML(String filename) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        // Inhale the XML file
        try {
            sp = spf.newSAXParser();
            sp.parse(filename, this);
        } catch (ParserConfigurationException e) {
            // No idea what mishaps throw these
            e.printStackTrace();
        } catch (SAXException e) {
            if (e instanceof SAXParseException) {
                // This was how the ampersand problem worked around earlier was detected
                fileValidationError(String.format(ErrorStrings.XML_ERROR_AT_LINE_D + ": %s", ((SAXParseException) e).getLineNumber(), e.getMessage()));
            } else {
                // There might be other data problems. File might well not even be XML.
                fileValidationError(ErrorStrings.XML_DATA_ERROR + ": "+e.getMessage());
            }
        } catch (IOException e) {
            // Probably, the user managed to select a non-file of some sort
            fileValidationError(ErrorStrings.XML_FILE_ERROR + ": " + e.getMessage());
        }
        // Check FileHeader for errors
        if (!fileHeaderSeen) {
            fileValidationError(ErrorStrings.ERR_NO_FILEHEADER);
        }
        if (!currentNode.isEmpty()) {
            fileValidationError(ErrorStrings.ERR_NO_ROOT_CLOSE);
        }
        return fileHeader;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (currentNode.isEmpty()) { // This node has no parents in the node stack
            if (rootNode == null) {  // ...and no root node has been seen yet...
                rootNode = qName;    // ...making this the root node.
            } else { // Only one root node, please
                fileValidationError(ErrorStrings.ERR_MULTIPLE_ROOT);
            }
        } else {
            switch (qName) {
                case "FileHeader":
                    // We want exactly one of these
                    fileHeaderImport = true;
                    if (fileHeaderSeen) {
                        // Hope this is self-explanatory
                        fileValidationError(ErrorStrings.ERR_MULTIPLE_ROOT);
                    }
                    fileHeaderSeen = true;
                    break;
                case "YoungPersonsRecord":
                    // New tag, new data.
                    currentYoungPersonsRecord = new YoungPersonsRecord();
                    youngPersonsRecordImport = true;
                    break;
                case "PersonalDetails":
                    if (youngPersonsRecordImport) {
                        personalDetailsFound = true;
                    }
            }
        }
        currentNode.push(qName);
        // This next line enforces a specific data model - that XML tags
        // can contain either XML tags or data, but not both. While it's
        // perfectly valid XML, the current schema doesn't allow it.
        currentContent = new StringBuilder();
    }

    private void fileValidationError(String errorMessage) {
        // TODO: Add fileValidationError implementation with error code argument
        fileHeader.addFileValidationError(errorMessage);
        System.out.println(errorMessage);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // In between the open and close tags, this function gets called an
        // arbitrary number of times (usually once, but not always) with some
        // more of the tag's data. We append it, to be dealt with when the tag
        // is closed.
        if (length > 0 && currentContent != null) {
            currentContent.append(ch, start, length);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Re-usable content variables
        String currentString;
        Integer currentValue;
        Date currentDate;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        dateFormatter.setLenient(false);
        if (currentContent != null) {                   // Current tag contains data, not more tags
            currentString = currentContent.toString();  // We always need the String
            if (fileHeaderImport) {
                // We're currently looking at tags inside the FileHeader node
                assert(!youngPersonsRecordImport);
                // A quick check - this object should have been initialised in startElement
                assert(fileHeader!=null);
                // We're not checking to see if nodes are contained in the correct nodes.
                // I considered it, but the intention is to check the data rather than
                // the XML structure. Other tools can validate against XSD.
                switch (currentNode.peek()) { // This is our current tag; data is in qNode
                    case "DatabaseID":
                        currentValue = Integer.decode(currentString);
                        if (currentValue == null) {
                            fileValidationError(ErrorStrings.ERR_INVALID_DBIDS + ": " + currentString);
                        } else {
                            fileHeader.addDatabase(currentValue);
                        }
                        break;
                    case "LEACode":
                        currentValue = Integer.decode(currentString);
                        if (currentValue == null) {
                            fileValidationError(ErrorStrings.ERR_INVALID_FHLEA + ": " + currentString);
                        } else {
                            fileHeader.addSourceLea(currentValue);
                        }
                        break;
                    case "DateOfSend":
                        try {
                            // Enforce the date format
                            if (currentString.length() != DATE_FORMAT.length()) {
                                throw new ParseException(null, 0);
                            }
                            currentDate = dateFormatter.parse(currentString);
                            fileHeader.setDateOfSend(currentDate);
                        } catch (ParseException e) {
                            fileValidationError(ErrorStrings.ERR_INVALID_DATE_SEND + ": " + currentString);
                        }
                        break;
                    case "PeriodEnd":
                        try {
                            // Enforce the date format
                            if (currentString.length() != DATE_FORMAT.length()) {
                                throw new ParseException(null, 0);
                            }
                            currentDate = dateFormatter.parse(currentString);
                            fileHeader.setPeriodEnd(currentDate);
                        } catch (ParseException e) {
                            fileValidationError(ErrorStrings.ERR_INVALID_PERIODEND + ": " + currentString);
                        }
                        break;
                    case "SupplierName":
                        fileHeader.setSupplierName(currentString);
                        break;
                    case "SupplierXMLVersion":
                        fileHeader.setSupplierXMLVersion(currentString);
                        break;
                    case "XMLSchemaVersion":
                        fileHeader.setXMLSchemaVersion(currentString);
                        break;
                    default:
                        fileValidationError(ErrorStrings.ERR_UNEXPECTED_DATA + ": "+currentNode.peek()+", "+currentString);
                        break;
                }
            }
            if (youngPersonsRecordImport) {
                // Our tag data is associated with a YoungPersonsRecord.
                assert (!fileHeaderImport); // This would mean our state is messed up
                assert (currentYoungPersonsRecord != null); // Should have been initialised in startElement
                switch (currentNode.peek()) { // This is our current tag; data is in qNode
                    case "YoungPersonsID":
                        currentYoungPersonsRecord.personalDetails.setYoungPersonsID(Long.decode(currentString));
                        break;
                    case "CohortStatus":
                        currentYoungPersonsRecord.personalDetails.setCohortStatus(currentString.charAt(0));
                        break;
                    case "GivenName":
                        currentYoungPersonsRecord.personalDetails.setGivenName(currentString);
                        break;
                    case "MiddleName":
                        currentYoungPersonsRecord.personalDetails.setMiddleName(currentString);
                        break;
                    case "FamilyName":
                        currentYoungPersonsRecord.personalDetails.setFamilyName(currentString);
                        break;
                    case "AddressLine1":
                        currentYoungPersonsRecord.personalDetails.setAddressLine1(currentString);
                        break;
                    case "AddressLine2":
                        currentYoungPersonsRecord.personalDetails.setAddressLine2(currentString);
                        break;
                    case "AddressLine3":
                        currentYoungPersonsRecord.personalDetails.setAddressLine3(currentString);
                        break;
                    case "AddressLine4":
                        currentYoungPersonsRecord.personalDetails.setAddressLine4(currentString);
                        break;
                    case "Town":
                        currentYoungPersonsRecord.personalDetails.setTown(currentString);
                        break;
                    case "County":
                        currentYoungPersonsRecord.personalDetails.setCounty(currentString);
                        break;
                    case "Postcode":
                        currentYoungPersonsRecord.personalDetails.setPostcode(currentString);
                        break;
                    case "Gender":
                        currentYoungPersonsRecord.personalDetails.setGender(currentString.charAt(0));
                        break;
                    case "DOB":
                        try {
                            // Enforce the date format
                            if (currentString.length() != DATE_FORMAT.length()) {
                                throw new ParseException(null, 0);
                            }
                            currentDate = dateFormatter.parse(currentString);
                            currentYoungPersonsRecord.personalDetails.setDob(currentDate);
                        } catch (ParseException e) {
                            //TODO: Double-check date format thing
                            fileValidationError(ErrorStrings.ERR_INVALID_DOB + ": " + currentString);
                        }
                        break;
                }
            }
            // This next line enforces a specific data model - that XML tags
            // can contain either XML tags or data, but not both. While it's
            // perfectly valid XML, the current schema doesn't allow it.
            // This program will ignore it.
            currentContent = null;
        }

        switch (currentNode.pop()) {
            // Clear the flags on the way up the stack
            case "FileHeader":
                fileHeaderImport = false;
                break;
            case "YoungPersonsRecord":
                // For now, dump some stuff. TODO: Database.
                currentYoungPersonsRecord.dumpSomeStuff();
                youngPersonsRecordImport = false;
                if (!personalDetailsFound) {
                    fileValidationError(ErrorStrings.ERR_NO_PERSONAL_DETAILS);
                }
                personalDetailsFound = false;
                break;
        }
    }
}

