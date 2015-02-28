package org.ppcis.ccistool;

import org.ppcis.ccistool.storage.FileHeader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private JMenuItem fileExit;     // File -> Exit
    private JPanel jPanel;          // The bit below the menu bar
    private JTextField databaseIDTextField;         // Text field to display database IDs
    private JList<Integer> leaList;                 // List field to display source LEAs
    private DefaultListModel<Integer> leaListData;  // Data for that list field

    FileHeader fileHeader;

    public Gui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
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
            fileMenu.addSeparator();
            {
                fileExit = new JMenuItem("Exit");
                fileExit.setMnemonic('X');
                fileExit.addActionListener(this);
                fileExit.setActionCommand("exit");
            }
            fileMenu.add(fileExit);
        }
        jMenuBar.add(fileMenu);
        jFrame.setJMenuBar(jMenuBar);

        jPanel = new JPanel(new GridBagLayout());
        jFrame.setContentPane(jPanel);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx=0;c.gridy=0;c.gridwidth=4;
        jPanel.add(new JLabel(new ImageIcon(getClass().getResource("/CCISTool.png"), "Logo"),JLabel.CENTER),c);
        c.gridx=1;c.gridy=1;c.gridwidth=1;;c.weighty=1.0;
        jPanel.add(new JLabel("Database IDs:"),c);
        databaseIDTextField = new JTextField("No data loaded");
        databaseIDTextField.setEnabled(false);
        databaseIDTextField.setDisabledTextColor(Color.BLACK);
        databaseIDTextField.setPreferredSize(new Dimension(150,30));
        c.gridx=2;c.gridy=1;
        jPanel.add(databaseIDTextField,c);
        c.gridx=1;c.gridy=2;
        jPanel.add(new JLabel("Source LEAs:"),c);
        leaListData = new DefaultListModel<>();
        leaList = new JList<>(leaListData);
        leaList.setVisibleRowCount(5);
        leaList.setFixedCellHeight(15);
        leaList.setFixedCellWidth(150);
        leaList.setVisible(true);
        JScrollPane leaListScrollPane = new JScrollPane();
        leaListScrollPane.getViewport().add(leaList);
        c.gridx=2;c.gridy=2;
        jPanel.add(leaListScrollPane,c);
        jFrame.pack();
        jFrame.setVisible(true);

    }

    public String getFileName() {
        FileDialog fileDialog = new FileDialog(jFrame,"FileOpen",FileDialog.LOAD);
        fileDialog.setFile("*.xml");
        fileDialog.setVisible(true);
        return fileDialog.getFile();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand())
        {
            case ("open"):
                fileHeader = new XMLImporter().importXML(getFileName());
                databaseIDTextField.setText(fileHeader.getDatabaseIDs());
                for (Integer sourceLEA : fileHeader.getSourceLEAs()) {
                    leaListData.addElement(sourceLEA);
                }
                break;
            case ("exit"):
                System.exit(0);
                break;
        }
    }
}
