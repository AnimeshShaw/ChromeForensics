/*
 * ChromeForensics v1.0
 * Copyright (C) 2016 Psycho_Coder <Animesh Shaw>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.letshackit.chromeforensics.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.letshackit.chromeforensics.core.export.ExportType;
import net.letshackit.chromeforensics.core.export.TSVExport;

/**
 *
 * @author Psycho_Coder
 */
public class ExportDialog extends JDialog {

    private final ExportType exportType;
    private ArrayList<JCheckBox> chkGrp;
    private boolean allSelected;

    private final JPanel panel;
    private JButton close, exportBut, browse, selectAll;
    private JTextArea txtArea;
    private JScrollPane scrollPane;
    private JLabel choose, loc;
    private JTextField locField;
    private JCheckBox exKST, exBM, exCookies, exFav, exCSQ, exSDB, exVS, exMVS,
            exAllUrls, exLogins, exDown;
    private JFileChooser fc;

    public ExportDialog(ExportType type) {
        this.exportType = type;
        panel = new JPanel();
        allSelected = false;
        MainPanel.getInstance().getTabbedPaneComponentDetails();
        initDialog();
    }

    private void initDialog() {
        setSize(new Dimension(650, 400));
        setLocationRelativeTo(null);
        setResizable(false);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setTitle("Data Export Dialog");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setContentPane(panel);

        panel.setLayout(null);

        choose = new JLabel("Choose what to export");
        choose.setBounds(30, 15, 200, 25);
        choose.setFont(new Font("Calibri", Font.PLAIN, 18));
        panel.add(choose);

        chkGrp = new ArrayList<>();
        exKST = new JCheckBox("Keyword Searches", false);
        exKST.setBounds(50, 50, 150, 20);
        panel.add(exKST);
        chkGrp.add(exKST);
        exBM = new JCheckBox("Bookmarks", false);
        exBM.setBounds(50, 80, 150, 20);
        panel.add(exBM);
        chkGrp.add(exBM);
        exCookies = new JCheckBox("Cookies", false);
        exCookies.setBounds(50, 110, 150, 20);
        panel.add(exCookies);
        chkGrp.add(exCookies);
        exFav = new JCheckBox("Favicons", false);
        chkGrp.add(exFav);
        exCSQ = new JCheckBox("Custom Queries", false);
        exCSQ.setBounds(50, 140, 150, 20);
        panel.add(exCSQ);
        chkGrp.add(exCSQ);
        exAllUrls = new JCheckBox("URLs", false);
        exAllUrls.setBounds(50, 170, 150, 20);
        panel.add(exAllUrls);
        chkGrp.add(exAllUrls);
        exSDB = new JCheckBox("Data Browser", false);
        exSDB.setBounds(50, 200, 150, 20);
        panel.add(exSDB);
        chkGrp.add(exSDB);
        exVS = new JCheckBox("Visited Sites", false);
        exVS.setBounds(50, 230, 150, 20);
        panel.add(exVS);
        chkGrp.add(exVS);
        exMVS = new JCheckBox("Most Visited Sites", false);
        exMVS.setBounds(50, 260, 150, 20);
        panel.add(exMVS);
        chkGrp.add(exMVS);
        exLogins = new JCheckBox("Logins", false);
        exLogins.setBounds(50, 290, 150, 20);
        panel.add(exLogins);
        chkGrp.add(exLogins);
        exDown = new JCheckBox("Downloads", false);
        exDown.setBounds(50, 320, 150, 20);
        panel.add(exDown);
        chkGrp.add(exDown);

        loc = new JLabel("Browse directory to save the exports");
        loc.setBounds(250, 30, 350, 30);
        loc.setFont(new Font("Calibri", Font.PLAIN, 16));
        panel.add(loc);

        locField = new JTextField(20);
        locField.setEditable(false);
        locField.setBounds(250, 60, 320, 25);
        panel.add(locField);

        browse = new JButton("...");
        browse.setBounds(580, 60, 40, 25);
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int retVal = fc.showOpenDialog(panel);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    locField.setText(file.toString());
                }
            }
        });
        panel.add(browse);

        txtArea = new JTextArea();
        scrollPane = new JScrollPane(txtArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(250, 90, 370, 180);
        panel.add(scrollPane);

        selectAll = new JButton("Select All");
        selectAll.setBounds(250, 280, 100, 40);
        selectAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (allSelected) {
                    selectAll.setText("Select All");
                    allSelected = false;
                    for (JCheckBox cb : chkGrp) {
                        cb.setSelected(false);
                    }
                } else {
                    selectAll.setText("Select None");
                    allSelected = true;
                    for (JCheckBox cb : chkGrp) {
                        cb.setSelected(true);
                    }
                }
            }
        });
        panel.add(selectAll);

        exportBut = new JButton("Begin Export");
        exportBut.setBounds(370, 280, 100, 40);
        exportBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (exportType == ExportType.TSV) {
                    TSVExport tsv = new TSVExport();
                    for (JCheckBox cb : chkGrp) {
                        if (cb.isSelected()) {

                        }
                    }
                }
                if (exportType == ExportType.HTML) {

                }
            }
        });
        panel.add(exportBut);

        close = new JButton("Close");
        close.setBounds(490, 280, 100, 40);
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        panel.add(close);

    }

}
