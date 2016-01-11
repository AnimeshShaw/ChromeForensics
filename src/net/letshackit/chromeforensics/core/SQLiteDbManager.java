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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Psycho_Coder
 */
public class SQLiteDbManager extends BaseDbModel {

    private String sDriver;
    private String sConnUrl;

    private String dbPath;

    public SQLiteDbManager() {
        sDriver = "org.sqlite.JDBC";
    }

    public SQLiteDbManager(String dbPath) throws FileNotFoundException {
        this.dbPath = dbPath;
        sDriver = "org.sqlite.JDBC";
        sConnUrl = "jdbc:sqlite:" + dbPath;

        initialize(sDriver, sConnUrl);
    }

    public void initialize() {
        assert getDbPath() != null;
        initialize(sDriver, sConnUrl);
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
        sConnUrl = "jdbc:sqlite:" + dbPath;
    }

    public void close() throws SQLException {
        closeConnection();
        closeStatement();
    }

    public String getDbPath() {
        return dbPath;
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

    public Vector<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        Vector<String> colNames = new Vector<>();
        int colCount = rsmd.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            colNames.add(rsmd.getColumnName(i));
        }
        return colNames;
    }
}
