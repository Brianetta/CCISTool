package org.ppcis.ccistool.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
                System.out.println(createQuery.toString());
                statement.execute(createQuery.toString());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
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
}
