package org.ppcis.ccistool;

import org.ppcis.ccistool.storage.FileHeader;
import org.ppcis.ccistool.storage.YoungPersonsRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
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

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private Stack<String> currentNode = new Stack<>();
    private StringBuilder currentContent;
    private String rootNode;
    private FileHeader fileHeader;
    private List<YoungPersonsRecord> youngPersonsRecords = new ArrayList<>();
    private YoungPersonsRecord currentYoungPersonsRecord;

    void experimental(String filename) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        // Inhale the XML file
        try {
            sp = spf.newSAXParser();
            sp.parse(filename,this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        // Check FileHeader for errors
        if (fileHeader == null) {
            fileValidationError("XML submission does not contain a FileHeader node");
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Begin "+qName);
        if (currentNode.isEmpty()) {
            if (rootNode == null) {
                rootNode = qName;
            } else {
                fileValidationError("More than one root node in the XML submission");
            }
        } else if (qName.equals("FileHeader")) {
            if(fileHeader == null) {
                fileHeader = new FileHeader();
            } else {
                fileValidationError("More than one FileHeader node found in XML");
            }
        } else if (qName.equals("YoungPersonsRecord")) {
            currentYoungPersonsRecord = new YoungPersonsRecord();
        }
        currentNode.push(qName);
        // This next line enforces a specific data model - that XML tags
        // can contain either XML tags or data, but not both. While it's
        // perfectly valid XML, the current schema doesn't allow it.
        currentContent = new StringBuilder();
    }

    private void fileValidationError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (length > 0 && currentContent != null) {
            currentContent.append(ch, start, length);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Re-usable content variables
        String currentString;
        Integer currentValue;
        Date currentDate;
        if (currentContent != null) {
            currentString = currentContent.toString();
            switch (currentNode.peek()) {
                case "DatabaseID":
                    currentValue = Integer.decode(currentString);
                    if (currentValue == null) {
                        fileValidationError("Invalid databaseIDs found in FileHeader: " + currentString);
                    } else {
                        fileHeader.addDatabase(currentValue);
                    }
                    break;
                case "LEACode":
                    currentValue = Integer.decode(currentString);
                    if (currentValue == null) {
                        fileValidationError("Invalid LEA value found in FileHeader: " + currentString);
                    } else {
                        // LEACode isn't a unique node name
                        switch (currentNode.elementAt(1)) {
                            case "SourceLEAs":
                                fileHeader.addSourceLea(currentValue);
                                break;
                            case "Year11":
                                currentYoungPersonsRecord.septemberGuarantee.year11.setLEACode(currentValue);
                                break;
                            case "Year12":
                                currentYoungPersonsRecord.septemberGuarantee.year12.setLEACode(currentValue);
                                break;
                        }
                    }
                    break;
                case "DateOfSend":
                    try {
                        currentDate = new SimpleDateFormat(DATE_FORMAT).parse(currentString);
                        fileHeader.setDateOfSend(currentDate);
                    } catch (ParseException e) {
                        fileValidationError("Invalid date: " + currentString);
                    }
            }
            // This next line enforces a specific data model - that XML tags
            // can contain either XML tags or data, but not both. While it's
            // perfectly valid XML, the current schema doesn't allow it.
            currentContent = null;
        }

        System.out.println("End " + qName);
        assert (currentNode.pop().equals(qName));
    }
}
