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
package net.letshackit.chromeforensics.core.export;

import java.io.File;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Psycho_Coder
 */
public interface IExporter {

    /**
     *
     * @param table
     * @param saveLoc
     * @return
     */
    public boolean export(JTable table, File saveLoc);

    /**
     *
     * @param table
     * @param saveLoc
     * @return
     */
    public boolean export(TableModel table, File saveLoc);

}
