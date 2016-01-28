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

public class MainMenuBar extends JMenuBar {

    protected JMenu file;
    protected JMenu tools;
    protected JMenu export;
    protected JMenu help;
    protected JMenu filter;

    protected JMenuItem fileLoadData;
    protected JMenuItem fileExit;
    protected JMenuItem fileAutoSearch;
    protected JMenuItem exportCsv;
    protected JMenuItem exportHtml;
    protected JMenuItem helpGetHelp;
    protected JMenuItem helpAbout;
    protected JMenuItem filterGoogle;
    protected JMenuItem filterBing;
    protected JMenuItem filterYahoo;
    protected JMenuItem filterDuckDuckgo;
    protected JMenuItem filterSearchEngines;
    protected JMenuItem executeQuery;
    protected JMenuItem dataBrowser;
    protected JMenuItem clearQuery;

    protected JSeparator fileSep;
    protected JSeparator toolsSep, toolsSep2, toolsSep3;
    protected JSeparator exportSep;
    protected JSeparator helpSep;

    public MainMenuBar() {
        file = new JMenu("File");
        tools = new JMenu("Tools");
        export = new JMenu("Export");
        help = new JMenu("Help");
        filter = new JMenu("Filter by");
        filter.setIcon(Utils.createImageIcon("images/filter_small.png", "Filter Data"));

        add(file);
        add(tools);
        add(export);
        add(help);

        fileSep = new JSeparator();
        fileSep.setOrientation(JSeparator.HORIZONTAL);
        fileLoadData = new JMenuItem("Manually Load Chrome Data");
        fileLoadData.setIcon(Utils.createImageIcon("images/loaddata_small.png", "Load Data"));
        fileLoadData.setToolTipText("Manually locate the chrome data files folder.");
        file.add(fileLoadData);
        fileAutoSearch = new JMenuItem("AutoSearch Chrome Data");
        fileAutoSearch.setIcon(Utils.createImageIcon("images/autosearch_small.png", "Auto Search andLoad Data"));
        fileAutoSearch.setToolTipText("Automatically search and load chrome files.");
        file.add(fileAutoSearch);
        file.add(fileSep);
        fileExit = new JMenuItem("Exit");
        fileExit.setIcon(Utils.createImageIcon("images/exit_small.png", "Exit"));
        fileExit.setToolTipText("Exit Application.");
        file.add(fileExit);

        tools.add(filter);
        filterBing = new JMenuItem("Bing");
        filterBing.setIcon(Utils.createImageIcon("images/bing_small.png", "Bing"));
        filter.add(filterBing);
        filterGoogle = new JMenuItem("Google");
        filterGoogle.setIcon(Utils.createImageIcon("images/google_small.png", "Google"));
        filter.add(filterGoogle);
        filterYahoo = new JMenuItem("Yahoo");
        filterYahoo.setIcon(Utils.createImageIcon("images/yahoo_small.png", "Yahoo"));
        filter.add(filterYahoo);
        filterDuckDuckgo = new JMenuItem("DuckDuckgo");
        filterDuckDuckgo.setIcon(Utils.createImageIcon("images/duckduckgo_small.png", "DuckDuckgo"));
        filter.add(filterDuckDuckgo);
        filterSearchEngines = new JMenuItem("Search Engines");
        filterSearchEngines.setIcon(Utils.createImageIcon("images/searchengine_small.png", "Other Search Engines"));
        filter.add(filterSearchEngines);
        toolsSep = new JSeparator();
        toolsSep.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep);
        executeQuery = new JMenuItem("Custom SQL Query");
        executeQuery.setIcon(Utils.createImageIcon("images/sql_small.png", "Custom SQL Query."));
        executeQuery.setToolTipText("Run Custom SQL Query.");
        tools.add(executeQuery);
        toolsSep2 = new JSeparator();
        toolsSep2.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep2);
        dataBrowser = new JMenuItem("Data Browser");
        dataBrowser.setIcon(Utils.createImageIcon("images/databrowse_small.png", "SQLite Data Browser"));
        dataBrowser.setToolTipText("SQLite Data Browser");
        tools.add(dataBrowser);
        toolsSep3 = new JSeparator();
        toolsSep3.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep3);
        clearQuery = new JMenuItem("Clear Query");
        clearQuery.setIcon(Utils.createImageIcon("images/clear_small.png", "Clear result set."));
        clearQuery.setToolTipText("Clear the result set.");
        tools.add(clearQuery);

        exportSep = new JSeparator();
        exportSep.setOrientation(JSeparator.HORIZONTAL);
        exportCsv = new JMenuItem("Export As CSV");
        exportCsv.setIcon(Utils.createImageIcon("images/csv_small.png", "Export results to CSV"));
        exportCsv.setToolTipText("Export Results to CSV");
        export.add(exportCsv);
        export.add(exportSep);
        exportHtml = new JMenuItem("Export As HTML");
        exportHtml.setIcon(Utils.createImageIcon("images/html_small.png", "Export results to HTML"));
        exportHtml.setToolTipText("Export results to HTML.");
        export.add(exportHtml);

        helpSep = new JSeparator();
        helpSep.setOrientation(JSeparator.HORIZONTAL);
        helpGetHelp = new JMenuItem("Get Help!");
        helpGetHelp.setIcon(Utils.createImageIcon("images/help_small.png", "Get Help!"));
        helpGetHelp.setToolTipText("Get help!");
        help.add(helpGetHelp);
        help.add(helpSep);
        helpAbout = new JMenuItem("About ChromeForensics!");
        helpAbout.setIcon(Utils.createImageIcon("images/about_small.png", "About this tool"));
        help.add(helpAbout);

        fileExit.addActionListener(actionEvent -> {
            ChromeForensicsGui.getInstance().dispose();
        });

        dataBrowser.addActionListener(actionEvent -> {
            MainPanel.getInstance().setSelectedTabIndex(6);
        });
    }
}
