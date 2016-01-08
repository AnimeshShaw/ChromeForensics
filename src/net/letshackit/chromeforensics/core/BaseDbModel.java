package net.letshackit.chromeforensics.core;

import java.sql.*;

/**
 * @author Psycho_Coder
 */
abstract class BaseDbModel {

    public String driver = null;
    public String connUrl = null;
    public int connTimeout = 30;
    public Connection conn = null;
    public Statement stmt = null;

    /**
     * Default constructor
     */
    public BaseDbModel() {
    }

    /**
     * @param driver
     * @param connUrl
     */
    public BaseDbModel(String driver, String connUrl) {
        initialize(driver, connUrl);
    }

    /**
     * @param driver
     * @param connUrl
     * @param connTimeout
     */
    public BaseDbModel(String driver, String connUrl, int connTimeout) {
        initialize(driver, connUrl, connTimeout);
    }

    /**
     * @param driver
     * @param connUrl
     */
    public void initialize(String driver, String connUrl) {
        setDriver(driver);
        setConnectionUrl(connUrl);
        try {
            setConnection();
            setStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param driver
     * @param connUrl
     * @param connTimeout
     */
    public void initialize(String driver, String connUrl, int connTimeout) {
        setDriver(driver);
        setConnectionUrl(connUrl);
        setConnectionTimeout(connTimeout);
        try {
            setConnection();
            setStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Driver : " + driver + " Connection Url : " + connUrl;
    }

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void setConnection() throws ClassNotFoundException, SQLException {
        assert driver != null && connUrl != null;
        Class.forName(driver);
        conn = DriverManager.getConnection(connUrl);
    }

    /**
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     *
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param connTimeout
     */
    public void setConnectionTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    /**
     * @return
     */
    public int getConnectionTimeout() {
        return connTimeout;
    }

    /**
     * @return
     */
    public String getConnectionUrl() {
        return connUrl;
    }

    /**
     * @param connUrl
     */
    public void setConnectionUrl(String connUrl) {
        this.connUrl = connUrl;
    }

    /**
     * @param driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setStatement() throws SQLException, ClassNotFoundException {
        if (conn != null) {
            setConnection();
        }
        stmt = conn.createStatement();
        stmt.setQueryTimeout(connTimeout);
    }

    /**
     * @return
     */
    public Statement getStatement() {
        return stmt;
    }

    /**
     * @param sqlQuery
     * @throws SQLException
     */
    public void executeStatement(String sqlQuery) throws SQLException {
        stmt.executeUpdate(sqlQuery);
    }

    /**
     * @param sqlQueryArray
     * @throws SQLException
     */
    public void executeStatement(String[] sqlQueryArray) throws SQLException {
        for (String sql : sqlQueryArray) {
            executeStatement(sql);
        }
    }

    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        return stmt.executeQuery(sqlQuery);
    }
}