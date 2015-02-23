package org.ppcis.ccistool;

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

    JFrame jFrame;
    JPanel jPanel;
    JMenuBar jMenuBar;
    JMenu fileMenu;
    JMenuItem fileOpen;
    JMenuItem fileExit;

    public Gui() {
        jFrame = new JFrame("CCIS Tool");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel = new JPanel();
        jMenuBar = new JMenuBar();
        {
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);
            {
                fileOpen = new JMenuItem("Import...");
                fileOpen.setMnemonic('I');
                fileOpen.addActionListener(this);
            }
            fileMenu.add(fileOpen);
            fileMenu.addSeparator();
            {
                fileExit = new JMenuItem("Exit");
                fileExit.setMnemonic('X');
                fileExit.addActionListener(this);
            }
            fileMenu.add(fileExit);
        }
        jMenuBar.add(fileMenu);

        jFrame.setContentPane(jPanel);
        jFrame.setJMenuBar(jMenuBar);

        jFrame.setSize(400, 400);
        jFrame.setVisible(true);

    }

    public String getFileName() {
        FileDialog fileDialog = new FileDialog(jFrame,"Test",FileDialog.LOAD);
        fileDialog.setFile("*.xml");
        fileDialog.setVisible(true);
        return fileDialog.getFile();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        switch(actionEvent.getActionCommand())
        {
            case ("Import..."):
                new XMLImporter().experimental(getFileName());
                break;
            case ("Exit"):
                System.exit(0);
                break;
        }
    }
}
