package com.bmuschko.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class WarehouseRepositoryImpl implements WarehouseRepository {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public WarehouseRepositoryImpl(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void insertItem(Item item) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = createConnection();
            pstmt = connection.prepareStatement("INSERT INTO item (name, price) VALUES (?, ?) RETURNING ID");
            pstmt.setString(1, item.getName());
            pstmt.setBigDecimal(2, item.getPrice());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                item.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new WarehouseException("Unable to insert item", e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(connection);
        }
    }

    @Override
    public Item getItem(Long id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Item item = null;

        try {
            connection = createConnection();
            pstmt = connection.prepareStatement("SELECT id, name, price FROM item WHERE id = ?");
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                item = new Item();
                item.setId(rs.getLong(1));
                item.setName(rs.getString(2));
                item.setPrice(rs.getBigDecimal(3));
            }

            return item;
        } catch (SQLException e) {
            throw new WarehouseException("Unable to insert item", e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(connection);
        }
    }

    private Connection createConnection() {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        try {
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            throw new WarehouseException("Unable to establish connection to database", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }
}
