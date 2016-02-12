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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.letshackit.chromeforensics.core.Utils;

public class ChromeForensicsGui extends JFrame {

    private static final ChromeForensicsGui cfGui = new ChromeForensicsGui();

    private final int WIDTH;
    private final int HEIGHT;

    private ChromeForensicsGui() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width - 100;
        HEIGHT = screenSize.height - 100;

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = MainPanel.getInstance();

        setTitle("Chrome Forensics v1.0");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(mainPanel);
        setJMenuBar(new MainMenuBar());
        setIconImage(Utils.createImageIcon("images/chrome_forensics.png", "Chrome Forensics").getImage());
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?",
                        "Exit ChromeForensics ?", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {                    
                    dispose();
                }
            }
        });
        pack();
    }

    public static ChromeForensicsGui getInstance() {
        return cfGui;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> getInstance().setVisible(true));
    }
}
