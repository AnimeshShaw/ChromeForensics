/*
 * Copyright 2016 Animesh Shaw ( a.k.a. Psycho_Coder).
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

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {

    private int HEIGHT = 600;
    private int WIDTH = 800;

    private JToolBar toolBar;

    private JButton autoLoadData;
    private JButton manuallyLoadData;
    private JButton exitButton;
    private JButton exportCsv;
    private JButton exportHtml;
    private JButton helpButton;
    private JButton aboutButton;

    private JPanel visits;
    private JPanel urls;
    private JPanel downloads;
    private JPanel keywords;
    private JPanel favicons;
    private JPanel customQuery;

    private JTable visitsTable;

    private JTabbedPane tabbedPane;

    /**
     * MainPanel constructor to initialize the components.
     *
     * @param WIDTH Width of the JPanel
     * @param HEIGHT Height of the JPanel
     */
    public MainPanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        initComponents();
    }

    /**
     * Function that initializes the components and other functionality.
     */
    private void initComponents() {
        /* Toolbar Code Started */
        toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 50));

        manuallyLoadData = new JButton();
        manuallyLoadData.setIcon(Utils.createImageIcon("images/loaddata.png", "Load Data"));
        manuallyLoadData.setToolTipText("Manually locate the chrome data files folder.");
        toolBar.add(manuallyLoadData);

        autoLoadData = new JButton();
        autoLoadData.setIcon(Utils.createImageIcon("images/autosearch.png", "Auto Search and Load Data"));
        autoLoadData.setToolTipText("Automatically search and load chrome files.");
        toolBar.add(autoLoadData);

        toolBar.add(new JToolBar.Separator());

        exportCsv = new JButton("Export to");
        exportCsv.setIcon(Utils.createImageIcon("images/csv.png", "Export results to CSV"));
        exportCsv.setToolTipText("Export Results to CSV");
        exportCsv.setHorizontalTextPosition(SwingConstants.LEFT);
        toolBar.add(exportCsv);

        exportHtml = new JButton("Export to");
        exportHtml.setIcon(Utils.createImageIcon("images/html.png", "Export results to HTML"));
        exportHtml.setToolTipText("Export results to HTML.");
        exportHtml.setHorizontalTextPosition(SwingConstants.LEFT);
        toolBar.add(exportHtml);

        toolBar.add(new JToolBar.Separator());

        helpButton = new JButton();
        helpButton.setIcon(Utils.createImageIcon("images/help.png", "Need Help? Click Me!"));
        helpButton.setToolTipText("Need Help? Click Me!");
        toolBar.add(helpButton);

        aboutButton = new JButton();
        aboutButton.setIcon(Utils.createImageIcon("images/about.png", "About this tool!"));
        aboutButton.setToolTipText("About this tool!");
        toolBar.add(aboutButton);

        toolBar.add(new JToolBar.Separator());

        exitButton = new JButton();
        exitButton.setIcon(Utils.createImageIcon("images/exit.png", "Exit Application."));
        exitButton.setToolTipText("Exit Application");
        exitButton.addActionListener(actionEvent -> {
            ChromeForensicsGui.getInstance().dispose();
        });
        toolBar.add(exitButton);

        add(toolBar, BorderLayout.NORTH);
        /*Toolbar Code ends*/

        visits = new JPanel();
        urls = new JPanel();
        downloads = new JPanel();
        keywords = new JPanel();
        favicons = new JPanel();
        customQuery = new JPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Visits", visits);
        tabbedPane.addTab("URLs", urls);
        tabbedPane.addTab("Downloads", downloads);
        tabbedPane.addTab("Keyword Searches", keywords);
        tabbedPane.addTab("Favicons", favicons);
        tabbedPane.addTab("Run Custom Query", customQuery);
        add(tabbedPane, BorderLayout.CENTER);

        add(new FilterPanel(), BorderLayout.SOUTH);
    }

    /**
     * @return
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return HEIGHT;
    }
}