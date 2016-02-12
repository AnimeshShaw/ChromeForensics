/*
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
package net.letshackit.chromeforensics.core.db;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class stores all the instance of opened or alive database connection
 * which is likely to be reused later.
 *
 * @author Psycho_Coder
 */
public class DBConnectionPool {

    private static HashMap<File, SQLiteDbModel> connPoolMap;
    private static int size;

    /**
     * Defined Singleton pattern for the class.
     */
    private static final DBConnectionPool dbConnPool = new DBConnectionPool();

    private DBConnectionPool() {
        connPoolMap = new HashMap<>();
        size = 0;
    }

    /**
     * Returns a Single instance for this class.
     *
     * @return single instance of this class
     */
    public static DBConnectionPool getInstance() {
        return dbConnPool;
    }

    /**
     * Adds a new entry to the Connection Pool HashMap whenever a new database
     * is opened.
     *
     * @param dbPath path to the SQLite database file
     * @param dbModel
     * {@link net.letshackit.chromeforensics.core.db.SQLiteDbModel} instance for
     * the database file.
     */
    public void add(File dbPath, SQLiteDbModel dbModel) {
        connPoolMap.put(dbPath, dbModel);
        size += 1;
    }

    /**
     * Checks if for a particular db file an open connection exists or not.
     *
     * @param dbPath file whose connection status is to be checked.
     * @return returns true if an opened connection is found.
     */
    public boolean isConnectionOpened(File dbPath) {
        return connPoolMap.containsKey(dbPath);
    }

    public SQLiteDbModel getConnection(File dbPath) {
        return connPoolMap.get(dbPath);
    }

    /**
     *
     * @return
     */
    public Iterator<SQLiteDbModel> getAllConnections() {
        return connPoolMap.values().iterator();
    }

    /**
     * Returns the number of opened connections.
     *
     * @return no. of opened db connections
     */
    public int getSize() {
        return connPoolMap.size();
    }

    /**
     * Closes all the opened connections by iterating the
     * {@link net.letshackit.chromeforensics.core.db.DBConnectionPool}
     * instances.
     */
    public void closeAll() {
        Iterator<SQLiteDbModel> itr = connPoolMap.values().iterator();
        while (itr.hasNext()) {
            try {
                itr.next().close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
