package net.letshackit.chromeforensics.gui.tools;

import net.letshackit.chromeforensics.gui.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class FileViewer extends JPanel {

    private static FileViewer fileViewer = new FileViewer();

    private JPanel loadPanel;
    private JPanel fileMetadata;
    private JLabel loadFileLabel;
    private JTextField loadedFileLoc;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JScrollPane metadataScroller;
    private JButton browseDb;
    private JFileChooser fc;
    private JTable metadataTable;

    private File lastFolderLocation;

    private FileViewer() {
        initComponents();
    }

    public static FileViewer getInstance() {
        return fileViewer;
    }

    public void initComponents() {
        setLayout(new BorderLayout());

        fileMetadata = new JPanel(new BorderLayout());
        fileMetadata.setPreferredSize(new Dimension(300, getHeight()));
        fileMetadata.setBackground(Color.BLACK);

        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(new Object[]{"Attribute Name", "Value"});
        metadataTable = new JTable(tableModel);
        metadataScroller = new JScrollPane(metadataTable);
        metadataScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        metadataScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        metadataScroller.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.WHITE),
                "File Metadata Attributes", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Cambria", Font.ITALIC, 14), Color.WHITE));

        fileMetadata.add(metadataScroller, BorderLayout.CENTER);
        add(fileMetadata, BorderLayout.WEST);

        loadPanel = new JPanel(new FlowLayout());
        loadPanel.setBackground(new Color(0xe8e8e8));

        loadFileLabel = new JLabel("Load File: ");
        loadFileLabel.setToolTipText("Reads file as it would in a text editor.");

        loadedFileLoc = new JTextField("Click browse to choose the file.", 60);
        loadedFileLoc.setForeground(Color.GRAY);
        loadedFileLoc.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        loadedFileLoc.setEditable(false);

        textArea = new JTextArea(10, 20);
        textArea.setLineWrap(false);
        textArea.setEditable(false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        lastFolderLocation = new File(Utils.getUserHome());
        fc = new JFileChooser(lastFolderLocation);

        browseDb = new JButton("Browse");
        browseDb.addActionListener(actionEvent -> {

            int retVal = fc.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {

                File fileLoc = fc.getSelectedFile();
                loadedFileLoc.setText(fileLoc.toString());
                lastFolderLocation = fc.getCurrentDirectory();

                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try (BufferedReader reader = Files.newBufferedReader(fileLoc.toPath(),
                                StandardCharsets.ISO_8859_1)) {
                            textArea.setText("");
                            reader.lines().map(s -> s + "\n").forEach(textArea::append);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });

        loadPanel.add(loadFileLabel);
        loadPanel.add(loadedFileLoc);
        loadPanel.add(browseDb);

        add(loadPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}