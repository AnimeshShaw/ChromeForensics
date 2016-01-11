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

public class ChromeForensicsGui extends JFrame {

    private static ChromeForensicsGui cfGui = new ChromeForensicsGui();

    private final int WIDTH;
    private final int HEIGHT;

    private ChromeForensicsGui() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height - 40;

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = MainPanel.getInstance();

        setTitle("Chrome Forensics v1.0");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(mainPanel);
        setJMenuBar(new MainMenuBar());
        setIconImage(Utils.createImageIcon("resources/images/chrome_forensics.png", "Chrome Forensics").getImage());
        pack();
    }

    public static ChromeForensicsGui getInstance() {
        return cfGui;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> getInstance().setVisible(true));
    }
}
