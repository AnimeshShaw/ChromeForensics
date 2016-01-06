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

    private JMenu file;
    private JMenu tools;
    private JMenu export;
    private JMenu help;
    private JMenu filter;

    private JMenuItem fileLoadData;
    private JMenuItem fileExit;
    private JMenuItem fileAutoSearch;
    private JMenuItem exportCsv;
    private JMenuItem exportHtml;
    private JMenuItem helpGetHelp;
    private JMenuItem helpAbout;
    private JMenuItem filterGoogle;
    private JMenuItem filterBing;
    private JMenuItem filterYahoo;
    private JMenuItem filterSafesearch;
    private JMenuItem filterDuckDuckgo;
    private JMenuItem filterSearchEngines;
    private JMenuItem executeQuery;
    private JMenuItem dataBrowser;
    private JMenuItem clearQuery;

    private JSeparator fileSep;
    private JSeparator toolsSep, toolsSep2, toolsSep3;
    private JSeparator exportSep;
    private JSeparator helpSep;

    public MainMenuBar() {
        //setHelpMenu(help);
        file = new JMenu("File");
        tools = new JMenu("Tools");
        export = new JMenu("Export");
        help = new JMenu("Help");
        filter = new JMenu("Filter by");

        add(file);
        add(tools);
        add(export);
        add(help);

        fileSep = new JSeparator();
        fileSep.setOrientation(JSeparator.HORIZONTAL);
        fileLoadData = new JMenuItem("Manually Load Chrome Data");
        file.add(fileLoadData);
        fileAutoSearch = new JMenuItem("AutoSearch Chrome Data");
        file.add(fileAutoSearch);
        file.add(fileSep);
        fileExit = new JMenuItem("Exit");
        file.add(fileExit);

        tools.add(filter);
        filterBing = new JMenuItem("Bing");
        filter.add(filterBing);
        filterGoogle = new JMenuItem("Google");
        filter.add(filterGoogle);
        filterYahoo = new JMenuItem("Yahoo");
        filter.add(filterYahoo);
        filterSafesearch = new JMenuItem("Safesearch");
        filter.add(filterSafesearch);
        filterDuckDuckgo = new JMenuItem("DuckDuckgo");
        filter.add(filterDuckDuckgo);
        filterSearchEngines = new JMenuItem("Search Engines");
        filter.add(filterSearchEngines);
        toolsSep = new JSeparator();
        toolsSep.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep);
        executeQuery = new JMenuItem("Custom SQL Query");
        tools.add(executeQuery);
        toolsSep2 = new JSeparator();
        toolsSep2.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep2);
        dataBrowser = new JMenuItem("Data Browser");
        tools.add(dataBrowser);
        toolsSep3 = new JSeparator();
        toolsSep3.setOrientation(JSeparator.HORIZONTAL);
        tools.add(toolsSep3);
        clearQuery = new JMenuItem("Clear Query");
        tools.add(clearQuery);

        exportSep = new JSeparator();
        exportSep.setOrientation(JSeparator.HORIZONTAL);
        exportCsv = new JMenuItem("Export As CSV");
        export.add(exportCsv);
        export.add(exportSep);
        exportHtml = new JMenuItem("Export As HTML");
        export.add(exportHtml);

        helpSep = new JSeparator();
        helpSep.setOrientation(JSeparator.HORIZONTAL);
        helpGetHelp = new JMenuItem("Get Help!");
        help.add(helpGetHelp);
        help.add(helpSep);
        helpAbout = new JMenuItem("About ChromeForensics");
        help.add(helpAbout);

        fileExit.addActionListener(actionEvent -> {
            /**
             * Not sure if this is the correct method. If not, bite me!!!
             *
             * But using System.exit(0) is not cool.
             */
            ChromeForensicsGui.getInstance().dispose();
            //System.exit(0);
        });

    }
}
