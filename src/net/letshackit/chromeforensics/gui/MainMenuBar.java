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

    private JSeparator fileSep;
    private JSeparator toolsSep, toolsSep2;

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
        fileLoadData = new JMenuItem("Load Chrome Data");
        file.add(fileLoadData);
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
    }
}
