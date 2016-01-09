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

import net.letshackit.chromeforensics.core.ChromeForensics;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainPanel extends JPanel {

    private static MainPanel mainPanel = new MainPanel();

    private int WIDTH = 1000;
    private int HEIGHT = 600;

    private Map<Integer, JPanel> tabbedPanelDetails;

    protected JToolBar toolBar;

    protected JButton autoLoadData;
    protected JButton manuallyLoadData;
    protected JButton exitButton;
    protected JButton exportCsv;
    protected JButton exportHtml;
    protected JButton helpButton;
    protected JButton aboutButton;

    protected JPanel visits;
    protected JPanel urls;
    protected JPanel downloads;
    protected JPanel keywords;
    protected JPanel favicons;
    protected JPanel customQuery;

    protected JTable visitsTable;

    protected JTabbedPane tabbedPane;

    protected ChromeForensics cf;

    /**
     * MainPanel constructor to initialize the components.
     */
    private MainPanel() {
        initComponents();
    }

    /**
     * MainPanel constructor to initialize the components.
     *
     * @param WIDTH  Width of the JPanel
     * @param HEIGHT Height of the JPanel
     */
    public MainPanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        initComponents();
    }

    /**
     * An instance of this MainPanel. The MainPanel uses the Singleton pattern.
     *
     * @return returns a singleton instance of this Component
     */
    public static MainPanel getInstance() {
        return mainPanel;
    }

    /**
     * Function that initializes the components and other functionality.
     */
    private void initComponents() {
        /* Base Initialization */
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        cf = new ChromeForensics();

        /* Toolbar Code Started */
        initToolBar();
        add(toolBar, BorderLayout.NORTH);
        /*Toolbar Code ends*/

        /* JTabbedPane Code Started*/
        initTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        /* JTabbedPane Code Ended*/

        add(new FilterPanel(), BorderLayout.SOUTH);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 40));

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
        exitButton.addActionListener(actionEvent -> ChromeForensicsGui.getInstance().dispose());
        toolBar.add(exitButton);
    }

    private void initTabbedPane() {
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
        tabbedPane.addTab("Data Browser", new SQLiteDataBrowser());

        tabbedPanelDetails = new HashMap<>();
        tabbedPanelDetails.put(1, visits);
    }

    /**
     * Get the Width of the MainPanel.
     *
     * @return returns the height of the Component.
     */
    public final int getWidth() {
        return WIDTH;
    }

    /**
     *  Get the Height of the MainPanel.
     *
     * @return returns the height of the Component.
     */
    public final int getHeight() {
        return HEIGHT;
    }

    /**
     * Returns the selected index of the tab.
     *
     * @return int
     */
    public int getSelectedTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    /**
     * Filter Panel to be added to the main GUI to filter results according to users requirements.
     *
     * @author Psycho_Coder
     */
    final class FilterPanel extends JPanel {

        public FilterPanel() {
            setPreferredSize(new Dimension(getWidth(), 40));
            setBackground(Color.LIGHT_GRAY);
        }
    }
}