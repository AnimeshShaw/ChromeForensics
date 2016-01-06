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

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Dimension;

public class ChromeForensicsGui {

    private JFrame frame;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public ChromeForensicsGui() {
        frame = new JFrame("Chrome Forensics v1.0");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setContentPane(new MainPanel(WIDTH, HEIGHT));
        frame.setJMenuBar(new MainMenuBar());
        frame.setVisible(true);
        frame.pack();
    }

    public static void main(String[] args) {
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

        SwingUtilities.invokeLater(ChromeForensicsGui::new);
    }
}
