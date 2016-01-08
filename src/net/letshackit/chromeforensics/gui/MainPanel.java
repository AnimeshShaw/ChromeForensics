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
import net.letshackit.chromeforensics.core.SQLiteDbManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class MainPanel extends JPanel {

    private final int HEIGHT;
    private final int WIDTH;

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
     *
     * @param WIDTH Width of the JPanel
     * @param HEIGHT Height of the JPanel
     */
    public MainPanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        cf = new ChromeForensics();
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
        exitButton.addActionListener(actionEvent -> ChromeForensicsGui.getInstance().dispose());
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
        tabbedPane.addTab("Data Browser", new SQLiteDataBrowser());
        add(tabbedPane, BorderLayout.CENTER);

        add(new FilterPanel(), BorderLayout.SOUTH);
    }

    /**
     * @return
     */
    public final int getWidth() {
        return WIDTH;
    }

    /**
     *
     * @return
     */
    public final int getHeight() {
        return HEIGHT;
    }

    final class SQLiteDataBrowser extends JPanel {

        private JList showTablesList;
        private JLabel loadDbLabel;
        private JTextField loadedDbPath;
        private JPanel loadDbPanel;
        private JScrollPane showTablesListScroller;
        private JScrollPane tableScrollPane;
        private JButton browseDb;
        private JTable table;
        private final DefaultTableModel defaultTableModel;

        public SQLiteDataBrowser() {
            SQLiteDbManager dbManager = new SQLiteDbManager();

            setLayout(new BorderLayout());

            showTablesList = new JList();
            showTablesList.setLayoutOrientation(JList.VERTICAL_WRAP);
            showTablesList.setSelectedIndex(ListSelectionModel.SINGLE_SELECTION);
            showTablesList.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            showTablesList.setDragEnabled(false);
            showTablesList.setFixedCellWidth(150);
            showTablesList.setVisibleRowCount(-1);
            showTablesList.setEnabled(false);

            showTablesListScroller = new JScrollPane(showTablesList);
            showTablesListScroller.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK),
                    "List of Tables"));
            showTablesListScroller.setPreferredSize(new Dimension(160, this.getHeight()));

            add(showTablesListScroller, BorderLayout.EAST);

            loadDbPanel = new JPanel(new FlowLayout());
            loadDbPanel.setBackground(new Color(0xe8e8e8));
            loadDbPanel.setPreferredSize(new Dimension(getWidth(), 40));

            loadDbLabel = new JLabel("Load SQLite Database (.sqlite|.sqlite3|.db|.db3)");

            loadedDbPath = new JTextField("Click browse to choose the database file.", 70);
            loadedDbPath.setForeground(Color.GRAY);
            loadedDbPath.setFont(new Font("Times New Roman", Font.ITALIC, 13));
            loadedDbPath.setEditable(false);

            browseDb = new JButton("Browse");
            browseDb.addActionListener(actionEvent -> {
                JFileChooser fc = new JFileChooser(cf.getChromeDataPath().toFile());
                int retVal = fc.showOpenDialog(SQLiteDataBrowser.this);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    Path dbPath = fc.getSelectedFile().toPath();
                    if (Utils.checkIfSQLiteDb(dbPath.toString())) {
                        loadedDbPath.setText(dbPath.toString());
                        try {
                            dbManager.setDbPath(dbPath);
                            dbManager.initialize();
                            showTablesList.setListData(dbManager.getTables().toArray());
                            showTablesList.setEnabled(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(SQLiteDataBrowser.this, "The Selected file is not in SQLite Format",
                                "File Format Error", JOptionPane.ERROR_MESSAGE);
                        loadedDbPath.setText("Click browse to choose the database file.");
                    }
                }
            });

            loadDbPanel.add(loadDbLabel);
            loadDbPanel.add(loadedDbPath);
            loadDbPanel.add(browseDb);

            class DataBrowserTableModal extends DefaultTableModel {

                public DataBrowserTableModal() {
                }

                public DataBrowserTableModal(Object[][] tableData, Object[] colNames) {
                    super(tableData, colNames);
                }

                @Override
                public void setDataVector(Object[][] tableData, Object[] colNames) {
                    super.setDataVector(tableData, colNames);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            }

            DataBrowserTableModal tableModal = new DataBrowserTableModal();
            defaultTableModel = tableModal;

            table = new JTable();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setModel(defaultTableModel);

            showTablesList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    JList list = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        String tableName = list.getSelectedValue().toString();

                        new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                try {
                                    ResultSet rs = dbManager.executeQuery("SELECT * from " + tableName);
                                    Vector<String> columnNames = dbManager.getColumnNames(rs);
                                    Vector<Vector<Object>> tableData = new Vector<>();
                                    while (rs.next()) {
                                        Vector<Object> vector = new Vector<>();

                                        for (int i = 1; i <= columnNames.size(); i++) {
                                            vector.add(rs.getObject(i));
                                        }
                                        tableData.add(vector);
                                    }
                                    defaultTableModel.setDataVector(tableData, columnNames);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                    }
                }
            });

            tableScrollPane = new JScrollPane(table);
            tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            tableScrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
            add(tableScrollPane, BorderLayout.CENTER);

            add(loadDbPanel, BorderLayout.NORTH);
        }
    }

    /**
     * Filter Panel to be added to the main GUI to filter results according to users requirements.
     *
     * @author Psycho_Coder
     */
    final class FilterPanel extends JPanel {

        public FilterPanel() {
            setPreferredSize(new Dimension(getWidth(), 60));
            setBackground(Color.LIGHT_GRAY);
        }
    }
}