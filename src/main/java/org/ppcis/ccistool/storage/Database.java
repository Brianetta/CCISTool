package org.ppcis.ccistool.storage;

import org.ppcis.ccistool.Constants.UsefulData;
import org.ppcis.ccistool.ErrorTableModel;

import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Copyright © Brian Ronald
 * 22/02/15
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class Database {
    static private Connection connection;

    public Database() {
        if (connection == null) {
            Scanner scanner = new Scanner(getClass().getResourceAsStream("/initialise_database.sql"));
            StringBuilder createQuery = new StringBuilder();
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:CCISTool.db");
                Statement statement = connection.createStatement();
                while (scanner.hasNext()) {
                    createQuery.append(scanner.nextLine());
                }
                // Statements in the SQL file are delimited by double-semicolon, because JDBC is awful
                // and we need to run each statement individually.
                String[] sqlStatements = createQuery.toString().split(";;");
                for(String sql : sqlStatements) {
                    statement.addBatch(sql);
                }
                statement.executeBatch();
                statement.close();
                // Now to populate the LEA table from the UsefulData class. It might be quicker to hard-code the SQL,
                // but that requires more maintenance.
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Lea (LEANo, Name) VALUES (?, ?)");
                for (Map.Entry<Integer,String> LEA : UsefulData.LEA.entrySet()) {
                    preparedStatement.setObject(1, LEA.getKey());
                    preparedStatement.setString(2, LEA.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // Need to tell user about SQLite JDBC requirement
            } finally {
                scanner.close();
            }
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            // Clearly that wasn't open.
        }
    }

    public void storeYoungPersonsRecord(YoungPersonsRecord youngPersonsRecord) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO YoungPersonsRecord (" +
                    "YoungPersonsID," +
                    "GivenName," +
                    "FamilyName," +
                    "MiddleName," +
                    "Gender," +
                    "DOB," +
                    "LeadLEA," +
                    "CohortStatus," +
                    "LEACodeAtYear11," +
                    "TransferredToLACode," +
                    "AddressLine1," +
                    "AddressLine2," +
                    "AddressLine3," +
                    "AddressLine4," +
                    "Town," +
                    "County," +
                    "Postcode," +
                    "Ethnicity," +
                    "EducatedLEA," +
                    "UniqueLearnerNo," +
                    "SENDFlag," +
                    "GuaranteeStatusIndicator," +
                    "YouthContractIndicator," +
                    "YouthContactStartDate," +
                    "PreviousYPIDIdentifier," +
                    "EstablishmentNumber," +
                    "UniquePupilNumber," +
                    "EstablishmentName," +
                    "UKProviderReferenceNumber," +
                    "ActivityCode," +
                    "StartDate," +
                    "DateAscertained," +
                    "DateVerified," +
                    "ReviewDate," +
                    "DueToLapseDate," +
                    "CurrencyLapsed," +
                    "LevelOfNeedCode," +
                    "NEETStartDate," +
                    "PredictedEndDate," +
                    "IntendedDestinationYr11," +
                    "GuaranteeStatusY11," +
                    "LEACodeY11," +
                    "GuaranteeStatusY12," +
                    "LEACodeY12" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, youngPersonsRecord.personalDetails.getYoungPersonsID());
            preparedStatement.setString(2, youngPersonsRecord.personalDetails.getGivenName());
            preparedStatement.setString(3, youngPersonsRecord.personalDetails.getFamilyName());
            preparedStatement.setString(4, youngPersonsRecord.personalDetails.getMiddleName());
            preparedStatement.setString(5, youngPersonsRecord.personalDetails.getGender().toString());
            preparedStatement.setString(6, youngPersonsRecord.personalDetails.getDob());
            preparedStatement.setObject(7, youngPersonsRecord.personalDetails.getLeadLea());
            if (youngPersonsRecord.personalDetails.getCohortStatus() != null)
                preparedStatement.setString(8, youngPersonsRecord.personalDetails.getCohortStatus().toString());
            preparedStatement.setObject(9, youngPersonsRecord.personalDetails.getLEACodeAtYear11());
            preparedStatement.setObject(10, youngPersonsRecord.personalDetails.getTransferredToLACode());
            preparedStatement.setString(11, youngPersonsRecord.personalDetails.getAddressLine1());
            preparedStatement.setString(12, youngPersonsRecord.personalDetails.getAddressLine2());
            preparedStatement.setString(13, youngPersonsRecord.personalDetails.getAddressLine3());
            preparedStatement.setString(14, youngPersonsRecord.personalDetails.getAddressLine4());
            preparedStatement.setString(15, youngPersonsRecord.personalDetails.getTown());
            preparedStatement.setString(16, youngPersonsRecord.personalDetails.getCounty());
            preparedStatement.setString(17, youngPersonsRecord.personalDetails.getPostcode());
            preparedStatement.setString(18, youngPersonsRecord.personalDetails.getEthnicity());
            preparedStatement.setObject(19, youngPersonsRecord.personalDetails.getEducatedLEA());
            preparedStatement.setObject(20, youngPersonsRecord.personalDetails.getUniqueLearnerNo());
            if (youngPersonsRecord.levelOfNeed.isSendFlag() != null)
                preparedStatement.setString(21, youngPersonsRecord.levelOfNeed.isSendFlag() ? "Y" : "N");
            preparedStatement.setObject(22, youngPersonsRecord.personalDetails.getGuaranteeStatusIndicator());
            preparedStatement.setObject(23, youngPersonsRecord.personalDetails.getYouthContractIndicator());
            preparedStatement.setString(24, youngPersonsRecord.personalDetails.getYouthContractStartDate());
            preparedStatement.setString(25, youngPersonsRecord.personalDetails.getPreviousYPIDIdentifier());
            preparedStatement.setObject(26, youngPersonsRecord.activities.getEstablishmentNumber());
            preparedStatement.setString(27, youngPersonsRecord.personalDetails.getUniquePupilNumber());
            preparedStatement.setString(28, youngPersonsRecord.activities.getEstablishmentName());
            preparedStatement.setString(29, youngPersonsRecord.activities.getUKProviderReferenceNumber());
            preparedStatement.setObject(30, youngPersonsRecord.activities.getActivityCode());
            preparedStatement.setString(31, youngPersonsRecord.activities.getStartDate());
            preparedStatement.setString(32, youngPersonsRecord.activities.getDateAscertained());
            preparedStatement.setString(33, youngPersonsRecord.activities.getDateVerified());
            preparedStatement.setString(34, youngPersonsRecord.activities.getReviewDate());
            preparedStatement.setString(35, youngPersonsRecord.activities.getDueToLapseDate());
            preparedStatement.setString(36, youngPersonsRecord.activities.isCurrencyLapsed() ? "Y" : "N");
            preparedStatement.setObject(37, youngPersonsRecord.levelOfNeed.getLevelOfNeedCode());
            preparedStatement.setString(38, youngPersonsRecord.activities.getNEETStartDate());
            preparedStatement.setString(39, youngPersonsRecord.activities.getPredictedEndDate());
            preparedStatement.setObject(40, youngPersonsRecord.getIntendedDestinationY11());
            preparedStatement.setObject(41, youngPersonsRecord.septemberGuarantee.year11.getGuaranteeStatus());
            preparedStatement.setObject(42, youngPersonsRecord.septemberGuarantee.year11.getLEACode());
            preparedStatement.setObject(43, youngPersonsRecord.septemberGuarantee.year12.getGuaranteeStatus());
            preparedStatement.setObject(44, youngPersonsRecord.septemberGuarantee.year12.getLEACode());
            preparedStatement.execute();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement("INSERT INTO Characteristic (YoungPersonsID,CharacteristicCode) VALUES(?,?)");
            for (Integer characteristicCode : (ArrayList<Integer>)youngPersonsRecord.levelOfNeed.getCharacteristics()) {
                preparedStatement.setString(1, youngPersonsRecord.personalDetails.getYoungPersonsID());
                preparedStatement.setInt(2, characteristicCode);
                preparedStatement.execute();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void beginTransaction() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("BEGIN TRANSACTION");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commitTransaction() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("COMMIT TRANSACTION");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TableModel errors(Integer[] LEAs) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ResultSetMetaData metaData;
        ErrorTableModel tableModel = new ErrorTableModel();
        try {
            preparedStatement = connection.prepareStatement("select yp.youngpersonsid,d.ErrorCode,d.description,D.Priority " +
                    "from errorfound as e " +
                    "inner join youngpersonsrecord as yp " +
                    "on e.youngpersonsid=yp.youngpersonsid " +
                    "inner join errordef as d " +
                    "on d.ErrorCode=e.ErrorCode " +
                    "where yp.LeadLEA in (?)");
            preparedStatement.setArray(1, connection.createArrayOf("int", LEAs));
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    tableModel.addRow(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getInt(4));
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableModel;
    }
}
