/*
 * ChromeForensics v1.0
 * Copyright (C) 2016 Psycho_Coder <Animesh Shaw>.
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Psycho_Coder
 */
public class TSVExport implements IExporter {

    final static Logger logger = LogManager.getLogger(TSVExport.class);

    /**
     *
     * @param table
     * @param saveLoc
     * @return
     */
    @Override
    public boolean export(JTable table, File saveLoc) {
        return export(table.getModel(), saveLoc);
    }

    /**
     *
     * @param modal
     * @param saveLoc
     * @return
     */
    @Override
    public boolean export(TableModel modal, File saveLoc) {
        int rowCount = modal.getRowCount();
        int colCount = modal.getColumnCount();

        try (BufferedWriter writer = Files.newBufferedWriter(saveLoc.toPath(), StandardCharsets.UTF_8)) {
            for (int col = 0; col < colCount; col++) {
                writer.write(modal.getColumnName(col));
                writer.write("\t");
            }
            writer.newLine();
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    writer.write((String) modal.getValueAt(row, col));
                    writer.write("\t");
                }
                writer.newLine();
            }
            logger.info("Export to TSV successful!");
            return true;
        } catch (IOException ex) {
            logger.debug("Exception Occured during export to TSV.", ex);
        }
        return false;
    }
}
