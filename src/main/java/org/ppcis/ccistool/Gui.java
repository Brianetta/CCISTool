package org.ppcis.ccistool;

import org.ppcis.ccistool.Constants.UsefulData;
import org.ppcis.ccistool.storage.FileHeader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

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
public class Gui implements ActionListener {

    private JFrame jFrame;          // Main app window
    private JMenuBar jMenuBar;      // The menu bar
    private JMenu fileMenu;         // The file menu
    private JMenuItem fileOpen;     // File -> Open...
    private JMenuItem fileOpenWithFix;     // File -> Open impaired...
    private JMenuItem fileExit;     // File -> Exit
    private JMenu dataMenu;         // The file menu
    private JMenuItem dataShowErrors;     // File -> Open...
    private JPanel jPanel;          // The bit below the menu bar
    private JList<String> leaList;                 // List field to display source LEAs
    private DefaultListModel<String> leaListData;  // Data for that list field
    private JLabel showDateOfSend, showPeriodEnd, showDatabaseID, showSupplierInfo;
    private JLabel guiStatus;
    private static final String MONTH_FORMAT = "MMMMM, YYYY";

    private static ErrorDisplay errorDisplay;
    private static Gui guiInstance;

    FileHeader fileHeader;

    public static Gui getGui() {
        // Return an instance of Gui (which should be *the* instance of Gui)
        return guiInstance;
    }

    public Gui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        final String NO_DATA_LOADED = "No data loaded";

        guiInstance = this;

        jFrame = new JFrame("CCIS Tool");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jMenuBar = new JMenuBar();
        {
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);
            {
                fileOpen = new JMenuItem("Import...");
                fileOpen.setMnemonic('I');
                fileOpen.addActionListener(this);
                fileOpen.setActionCommand("open");
            }
            fileMenu.add(fileOpen);
            {
                fileOpenWithFix = new JMenuItem("Import impaired...");
                fileOpenWithFix.setMnemonic('I');
                fileOpenWithFix.addActionListener(this);
                fileOpenWithFix.setActionCommand("openImpaired");
            }
            fileMenu.add(fileOpenWithFix);
            fileMenu.addSeparator();
            {
                fileExit = new JMenuItem("Exit");
                fileExit.setMnemonic('X');
                fileExit.addActionListener(this);
                fileExit.setActionCommand("exit");
            }
            fileMenu.add(fileExit);
            dataMenu = new JMenu("Data");
            dataMenu.setMnemonic(KeyEvent.VK_D);
            {
                dataShowErrors = new JMenuItem("Show errors...");
                dataShowErrors.setMnemonic('S');
                dataShowErrors.addActionListener(this);
                dataShowErrors.setActionCommand("showErrors");
            }
            dataMenu.add(dataShowErrors);
        }
        jMenuBar.add(fileMenu);
        jMenuBar.add(dataMenu);
        jFrame.setJMenuBar(jMenuBar);

        jPanel = new JPanel(new GridBagLayout());
        jFrame.setContentPane(jPanel);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx=0;c.gridy=0;c.gridwidth=2;
        jPanel.add(new JLabel(new ImageIcon(getClass().getResource("/CCISTool.png"), "Logo"), JLabel.CENTER), c);
        c.gridy=1;c.gridwidth=1;c.weighty=0.0;c.fill = GridBagConstraints.BOTH;
        jPanel.add(new JLabel("Database ID:", JLabel.RIGHT), c);
        showDatabaseID = new JLabel(NO_DATA_LOADED);
        c.gridx=1;c.gridy=1;c.weighty=1.0;
        jPanel.add(showDatabaseID,c);

        c.gridx=0;c.gridy=2;c.weighty=0.0;
        jPanel.add(new JLabel("Source LEAs:", JLabel.RIGHT), c);
        leaListData = new DefaultListModel<>();
        leaList = new JList<>(leaListData);
        leaList.setVisibleRowCount(5);
        leaList.setFixedCellHeight(15);
        leaList.setFixedCellWidth(150);
        leaList.setVisible(true);
        JScrollPane leaListScrollPane = new JScrollPane();
        leaListScrollPane.getViewport().add(leaList);
        c.gridx=1;c.gridy=2;c.weighty=1.0;
        jPanel.add(leaListScrollPane,c);

        c.gridx=0;c.gridy=3;c.weighty=0.0;
        jPanel.add(new JLabel("Submission date:", JLabel.RIGHT), c);
        showDateOfSend = new JLabel(NO_DATA_LOADED);
        c.gridx=1;c.gridy=3;c.weighty=1.0;
        jPanel.add(showDateOfSend, c);

        c.gridx=0;c.gridy=4;c.weighty=0.0;
        jPanel.add(new JLabel("For period end:", JLabel.RIGHT), c);
        showPeriodEnd = new JLabel(NO_DATA_LOADED);
        c.gridx=1;c.gridy=4;c.weighty=1.0;
        jPanel.add(showPeriodEnd, c);

        c.gridx=0;c.gridy=5;c.weighty=0.0;
        jPanel.add(new JLabel("Supplier info:", JLabel.RIGHT), c);
        showSupplierInfo = new JLabel(NO_DATA_LOADED);
        c.gridx=1;c.gridy=5;c.weighty=1.0;
        jPanel.add(showSupplierInfo, c);

        c.gridx=0;c.gridy=6;c.weighty=0.0;
        jPanel.add(new JLabel("Status:", JLabel.RIGHT), c);
        guiStatus = new JLabel(NO_DATA_LOADED);
        c.gridx=1;c.gridy=6;c.weighty=1.0;
        jPanel.add(guiStatus, c);

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setMinimumSize(jFrame.getSize());

    }

    public String getFileName(String fileSpec) {
        FileDialog fileDialog = new FileDialog(jFrame,"FileOpen",FileDialog.LOAD);
        fileDialog.setFile(fileSpec);
        fileDialog.setVisible(true);
        String fileName = fileDialog.getFile();
        if (fileName == null) return null;
        return String.format("%s%s", fileDialog.getDirectory(), fileName);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand())
        {
            case ("open"):
                importXMLFile(true);  // unimpaired XML file
                break;
            case ("openImpaired"):
                importXMLFile(false); // XML probably needs fixing up
                break;
            case ("exit"):
                System.exit(0);
                break;
            case ("showErrors"):
                errorDisplay = new ErrorDisplay(leaList.getSelectedValuesList());
                break;
        }
    }

    private class ImportWorker extends SwingWorker<FileHeader, Void>
    {
        boolean unimpaired;
        String filename;

        public ImportWorker(String filename,boolean unimpaired) {
            this.unimpaired = unimpaired;
            this.filename = filename;
        }

        @Override
        public FileHeader doInBackground() {
            if (unimpaired) {
                return new XMLImporter().importXML(filename);
            } else {
                return new XMLImporter().importXMLWithFix(filename);
            }
        }
        @Override
        public void done() {
            try {
                Gui.getGui().getImportResults(get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    };

    private void importXMLFile(boolean unimpaired) {
        String importFile;
        importFile = getFileName("*.xml");
        ImportWorker importer = new ImportWorker(importFile, unimpaired);
        importer.execute();
    }

    public void getImportResults(FileHeader fileHeader) {
        if (fileHeader == null) return;
        if (fileHeader.getPeriodEnd()==null) return;
        this.fileHeader = fileHeader;
        showDatabaseID.setText(fileHeader.getDatabaseIDs());
        for (Integer sourceLEA : fileHeader.getSourceLEAs()) {
            leaListData.addElement(sourceLEA.toString() + ": " + UsefulData.LEA.get(sourceLEA));
        }
        showDateOfSend.setText(new SimpleDateFormat(MONTH_FORMAT).format(fileHeader.getDateOfSend()));
        if (fileHeader.getDateOfSend().before(fileHeader.getPeriodEnd())) {
            showDateOfSend.setForeground(new Color(224, 128, 0));
        } else {
            showDateOfSend.setForeground(new Color(0, 128, 0));
        }
        showPeriodEnd.setText(new SimpleDateFormat(MONTH_FORMAT).format(fileHeader.getPeriodEnd()));
        if (fileHeader.getPeriodEnd().after((Calendar.getInstance()).getTime())) {
            showPeriodEnd.setForeground(new Color(224, 128, 0));
        } else {
            showPeriodEnd.setForeground(new Color(0, 128, 0));
        }
        showSupplierInfo.setText(fileHeader.getSupplierName()+", "+fileHeader.getSupplierXMLVersion()+", "+fileHeader.getXMLSchemaVersion());
    }

    public void setGuiStatus(String status) {
        guiStatus.setText(status);
    }
}
