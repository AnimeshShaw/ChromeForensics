/*
 * Copyright 2015 Psycho_Coder <Animesh Shaw>.
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
package net.letshackit.chromeforensics.gui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.letshackit.chromeforensics.core.Utils;
import net.letshackit.chromeforensics.core.db.DBConnectionPool;
import net.letshackit.chromeforensics.core.db.SQLiteDbModel;

public final class SQLiteDataBrowser extends JPanel {

    private final DefaultTableModel tableModel;
    private JList showTablesList;
    private JLabel loadDbLabel;
    private JLabel loadDbRecords;
    private JLabel loadDbRecordsCount;
    private JTextField loadedDbPath;
    private JPanel loadDbPanel;
    private JScrollPane showTablesListScroller;
    private JScrollPane tableScrollPane;
    private JButton browseDb;
    private JTable table;
    private JFileChooser fc;
    private File lastFolderLocation;

    private SQLiteDbModel dbModel;

    public SQLiteDataBrowser() {
        DBConnectionPool dbConnPool = DBConnectionPool.getInstance();
        setLayout(new BorderLayout());

        showTablesList = new JList();
        showTablesList.setLayoutOrientation(JList.VERTICAL_WRAP);
        showTablesList.setSelectedIndex(ListSelectionModel.SINGLE_SELECTION);
        showTablesList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

        loadDbLabel = new JLabel("Load SQLite Database: ");
        loadDbLabel.setToolTipText("Possible extensions being .sqlite|.sqlite3|.db|.db3");

        loadedDbPath = new JTextField("Click browse to choose the database file.", 40);
        loadedDbPath.setForeground(Color.GRAY);
        loadedDbPath.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        loadedDbPath.setEditable(false);

        lastFolderLocation = new File(Utils.getUserHome());
        fc = new JFileChooser(lastFolderLocation);

        browseDb = new JButton("Browse");
        browseDb.addActionListener(actionEvent -> {
            int retVal = fc.showOpenDialog(SQLiteDataBrowser.this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File dbPath = fc.getSelectedFile();

                if (!dbConnPool.isConnectionOpened(dbPath)) {
                    dbModel = new SQLiteDbModel();
                    dbConnPool.add(dbPath, dbModel);
                    dbModel.setDbPath(dbPath.toString());
                    dbModel.initialize();
                } else {                    
                    dbModel = dbConnPool.getConnection(dbPath);
                }

                if (Utils.checkIfSQLiteDb(dbPath)) {
                    loadedDbPath.setText(dbPath.toString());
                    lastFolderLocation = fc.getCurrentDirectory();
                    new SwingWorker<Void, Void>() {

                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                Vector<String> tableList = dbModel.getTables();
                                if (tableList != null) {
                                    showTablesList.setListData(tableList);
                                } else {
                                    JOptionPane.showMessageDialog(SQLiteDataBrowser.this,
                                            "No tables are present in the selected database",
                                            "No Records fetched", JOptionPane.WARNING_MESSAGE);
                                }
                                showTablesList.setEnabled(true);
                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }

                            return null;
                        }
                    }.execute();
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

        loadDbRecords = new JLabel("Records Fetched (Rows x Cols): ");
        loadDbRecords.setFont(new Font("Times New Roman", Font.ITALIC, 12));
        loadDbPanel.add(loadDbRecords);

        loadDbRecordsCount = new JLabel();
        loadDbRecordsCount.setFont(new Font("Times New Roman", Font.ITALIC, 12));
        loadDbPanel.add(loadDbRecordsCount);

        tableModel = new DataBrowserTableModal();

        table = new JTable();
        table.setModel(tableModel);

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
                                Vector<String> columnNames;
                                Vector<Vector<Object>> tableData;
                                try (ResultSet rs = dbModel.executeQuery("SELECT * from " + tableName)) {
                                    columnNames = dbModel.getColumnNames(rs);
                                    tableData = new Vector<>();
                                    while (rs.next()) {
                                        Vector<Object> vector = new Vector<>();

                                        for (int i = 1; i <= columnNames.size(); i++) {
                                            vector.add(rs.getObject(i));
                                        }
                                        tableData.add(vector);
                                    }
                                }
                                tableModel.setDataVector(tableData, columnNames);
                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }

                            loadDbRecordsCount.setText(tableModel.getRowCount() + " x "
                                    + tableModel.getColumnCount());

                            if (tableModel.getColumnCount() <= 5) {
                                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                            } else {
                                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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

        DBTableFilterPanel filterPanel = new DBTableFilterPanel();
        table.setRowSorter(filterPanel.getRowSorter());
        add(filterPanel, BorderLayout.SOUTH);
    }

    /**
     * TableModel to be used for the Browser JTable.
     */
    final class DataBrowserTableModal extends DefaultTableModel {

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

    /**
     * Filter Panel to enable searching and
     */
    final class DBTableFilterPanel extends JPanel {

        private TableRowSorter<TableModel> rowSorter;

        private JLabel label;

        private JTextField filterField;

        private JCheckBox regexEnabled;

        public DBTableFilterPanel() {
            initComponents();
        }

        public void initComponents() {
            setPreferredSize(new Dimension(getWidth(), 60));
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), "Filter Panel"));
            setLayout(new FlowLayout());

            rowSorter = new TableRowSorter<>(tableModel);
            rowSorter.setSortsOnUpdates(true);

            label = new JLabel("Enter Query: ");
            label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            add(label);

            regexEnabled = new JCheckBox("Use Regex");
            regexEnabled.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            regexEnabled.setBorderPaintedFlat(true);

            filterField = new JTextField(50);
            filterField.setForeground(Color.GRAY);
            filterField.setFont(new Font("Times New Roman", Font.ITALIC, 13));

            filterField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent documentEvent) {
                    String data = filterField.getText().trim();
                    if (data.isEmpty()) {
                        rowSorter.setRowFilter(null);
                    } else {
                        try {
                            if (regexEnabled.isSelected()) {
                                rowSorter.setRowFilter(RowFilter.regexFilter(data));
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + data));
                            }
                        } catch (PatternSyntaxException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent documentEvent) {
                    String data = filterField.getText().trim();
                    if (data.isEmpty()) {
                        rowSorter.setRowFilter(null);
                    } else {
                        try {
                            if (regexEnabled.isSelected()) {
                                rowSorter.setRowFilter(RowFilter.regexFilter(data));
                            } else {
                                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + data));
                            }
                        } catch (PatternSyntaxException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent documentEvent) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            add(filterField);
            add(regexEnabled);
        }

        public TableRowSorter<TableModel> getRowSorter() {
            return rowSorter;
        }
    }
}
