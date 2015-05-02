package org.ppcis.ccistool;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © Brian Ronald
 * 01/05/15
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
public class ErrorTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "YoungPersonsID",
            "ErrorCode",
            "Description",
            "Priority",
    };

    private class TableRow {
        public TableRow(String youngPersonsID, Integer errorCode, String description, Integer priority) {
            this.youngPersonsID = youngPersonsID;
            this.errorCode = errorCode;
            this.description = description;
            this.priority = priority;
        }

        public String youngPersonsID = null;
        public Integer errorCode = null;
        public String description = null;
        public Integer priority = null;
    }

    private List<TableRow> cells = new ArrayList<>();

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return cells.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (row > cells.size()) return null;
        switch (col) {
            case 0: return cells.get(row).youngPersonsID;
            case 1: return cells.get(row).errorCode;
            case 2: return cells.get(row).description;
            case 3: return cells.get(row).priority;
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void addRow(String youngPersonsID,Integer errorCode,String description,Integer priority) {
        TableRow tableRow = new TableRow(youngPersonsID,errorCode,description,priority);
        cells.add(tableRow);
    }
}
