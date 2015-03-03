package org.ppcis.ccistool;

import org.ppcis.ccistool.storage.FileHeader;
import org.ppcis.ccistool.storage.YoungPersonsRecord;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

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
    // A flag to track the number of fileHeaders seen. One bit should be big enough.
    private boolean fileHeaderSeen = false;
    // A pair of flags for keeping track of which data structure we're importing,
    // because some tags (like SourceLEA) appear in both
    private boolean fileHeaderImport = false;
    private boolean youngPersonsRecordImport = false;

    public XMLImporter() {
        fileHeader = new FileHeader();
    }

    FileHeader importXML(String filename) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        // Inhale the XML file
        try {
            // Put the ENTIRE XML file into a StringBuilder, line by line, so we can fix XML entities.
            // Would rather read directly from the file, but the XML we get from Cognisoft's product
            // often contains un-escaped ampersands in data strings.
            StringBuilder xml = new StringBuilder();

            // Read all the lines into a list
            List<String> lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
            for (String line : lines) {
                // Fix each line, turning ampersand followed by space into the entity and a space
                line = line.replaceAll("& ", "&amp; ");
                // Put that line into the StringBuilder
                xml.append(line);
            }
            sp = spf.newSAXParser();
            // Turn that StringBuilder into a file-like InputSource, and parse it as XML
            sp.parse(new InputSource(new StringReader(xml.toString())), this);
        } catch (ParserConfigurationException e) {
            // No idea what mishaps throw these
            e.printStackTrace();
        } catch (SAXException e) {
            if (e instanceof SAXParseException) {
                // This was how the ampersand problem worked around earlier was detected
                fileValidationError(String.format("XML error at line %d: %s", ((SAXParseException) e).getLineNumber(), e.getMessage()));
            } else {
                // There might be other data problems. File might well not even be XML.
                fileValidationError("Trouble with XML data: "+e.getMessage());
            }
        } catch (IOException e) {
            // Probably, the user managed to select a non-file of some sort
            fileValidationError("Trouble with XML file: " + e.getMessage());
        }
        // Check FileHeader for errors
        if (!fileHeaderSeen) {
            fileValidationError("XML submission does not contain a FileHeader node");
        }
        return fileHeader;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (currentNode.isEmpty()) { // This node has no parents in the node stack
            if (rootNode == null) {  // ...and no root node has been seen yet...
                rootNode = qName;    // ...making this the root node.
            } else { // Only one root node, please
                fileValidationError("More than one root node in the XML submission");
            }
        } else if (qName.equals("FileHeader")) { // We want exactly one of these
            fileHeaderImport = true;
            if (fileHeaderSeen) {
                    // Hope this is self-explanatory
                    fileValidationError("More than one FileHeader node found in XML");
                }
            fileHeaderSeen=true;
        } else if (qName.equals("YoungPersonsRecord")) {
            // New tag, new data.
            currentYoungPersonsRecord = new YoungPersonsRecord();
            youngPersonsRecordImport = true;
        }
        currentNode.push(qName);
        // This next line enforces a specific data model - that XML tags
        // can contain either XML tags or data, but not both. While it's
        // perfectly valid XML, the current schema doesn't allow it.
        currentContent = new StringBuilder();
    }

    private void fileValidationError(String errorMessage) {
        fileHeader.addFileValidationError(errorMessage);
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
                            fileValidationError("Invalid DateOfSend: " + currentString);
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
                            fileValidationError("Invalid PeriodEnd: " + currentString);
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
                        fileValidationError("Unexpected data in node: "+currentNode.peek()+", "+currentString);
                        break;
                }
            }
            if (youngPersonsRecordImport) {
                assert (!fileHeaderImport);
                assert (currentYoungPersonsRecord != null);
            }
            // This next line enforces a specific data model - that XML tags
            // can contain either XML tags or data, but not both. While it's
            // perfectly valid XML, the current schema doesn't allow it.
            // This program will ignore it.
            currentContent = null;
        }

        switch (currentNode.pop()) {
            case "FileHeader":
                fileHeaderImport = false;
                break;
            case "YoungPersonsRecord":
                youngPersonsRecords.add(currentYoungPersonsRecord);
                youngPersonsRecordImport = false;
                break;
        }
    }
}

