package net.letshackit.chromeforensics.gui.tools;

import javax.swing.*;
import java.awt.*;

public final class FileViewer extends JPanel {

    private static FileViewer fileViewer = new FileViewer();

    private FileViewer() {
        initComponents();
    }

    public static FileViewer getInstance() {
        return fileViewer;
    }

    public void initComponents() {
        setBackground(Color.black);
    }

}
