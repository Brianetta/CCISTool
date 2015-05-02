package org.ppcis.ccistool;

import org.ppcis.ccistool.storage.Database;

import javax.swing.*;
import java.util.List;

/**
 * Copyright © Brian Ronald
 * 25/04/15
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
public class ErrorDisplay {
    private static JFrame jFrame = new JFrame("Errors");

    private JTable jTable;
    private JScrollPane jScrollPane;

    public ErrorDisplay(List LEAs) {
        jTable = new JTable(new Database().errors(LEAs));
        jScrollPane = new JScrollPane(jTable);
        jFrame.setContentPane(jScrollPane);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
