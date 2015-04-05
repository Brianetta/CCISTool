package org.ppcis.ccistool.storage;

import org.ppcis.ccistool.Constants.UsefulData;

import java.sql.*;
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
                    preparedStatement.setInt(1, LEA.getKey());
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
        // Insert into database!
    }
}
