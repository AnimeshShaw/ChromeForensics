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

package net.letshackit.chromeforensics.core;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Psycho_Coder
 */
public class SQLiteDbManager extends BaseDbModel {

    private final String sDriver;
    private final String sConnUrl;

    public SQLiteDbManager(String dbPath) throws FileNotFoundException {
        sDriver = "org.sqlite.JDBC";
        if (Files.exists(Paths.get(dbPath))) {
            sConnUrl = "jdbc:sqlite:" + dbPath;
        } else {
            throw new FileNotFoundException("The database file at the given location wasn't found.");
        }
        initialize(sDriver, sConnUrl);
    }

    public ArrayList<String> getTables() throws SQLException {
        String TABLE_NAME = "TABLE_NAME";
        String[] TABLE_TYPES = {"TABLE"};
        ArrayList<String> tableList = new ArrayList<>();

        DatabaseMetaData dbmd = this.getConnection().getMetaData();
        ResultSet tables = dbmd.getTables(null, null, null, TABLE_TYPES);

        while (tables.next()) {
            tableList.add(tables.getString(TABLE_NAME));
        }

        return tableList;
    }

    public ArrayList<String> getColumnList(String tableName) {
        return null;
    }

}
