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


public class MainPanel extends JPanel {

    private int HEIGHT = 600;
    private int WIDTH = 800;

    private JToolBar toolBar;

    private JButton autoLoadData;
    private JButton manuallyLoadData;

    private JPanel visits;

    private JTabbedPane tabbedPane;

    /**
     * MainPanel constructor to initialize the components.
     *
     * @param WIDTH Width of the JPanel
     * @param HEIGHT Height of the JPanel
     */
    public MainPanel(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        initComponents();
    }

    /**
     * Function that initializes the
     */
    private void initComponents() {
        toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        manuallyLoadData = new JButton("Manually Load");
        toolBar.add(manuallyLoadData);
        autoLoadData = new JButton("Auto Load");
        toolBar.add(autoLoadData);

        add(toolBar, BorderLayout.NORTH);

        visits = new JPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Visits", visits);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public int getWidth() {
        return WIDTH;
    }

    public void setWidth(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void setHeight(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
}