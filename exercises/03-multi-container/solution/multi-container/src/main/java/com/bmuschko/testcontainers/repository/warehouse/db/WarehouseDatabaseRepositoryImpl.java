package com.bmuschko.testcontainers.repository.warehouse.db;

import com.bmuschko.testcontainers.model.warehouse.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class WarehouseDatabaseRepositoryImpl implements WarehouseDatabaseRepository {
    private final String jdbcUrl;
    private final UsernamePasswordCredentials credentials;

    public WarehouseDatabaseRepositoryImpl(String jdbcUrl, UsernamePasswordCredentials credentials) {
        this.jdbcUrl = jdbcUrl;
        this.credentials = credentials;
    }

    @Override
    public void insertProduct(Product product) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = createConnection();
            pstmt = connection.prepareStatement("INSERT INTO item (name, price) VALUES (?, ?) RETURNING ID");
            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                product.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new WarehouseDatabaseException("Unable to insert item", e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(connection);
        }
    }

    @Override
    public Product getProduct(Long id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Product product = null;

        try {
            connection = createConnection();
            pstmt = connection.prepareStatement("SELECT id, name, price FROM item WHERE id = ?");
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                product = new Product();
                product.setId(rs.getLong(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getBigDecimal(3));
            }

            return product;
        } catch (SQLException e) {
            throw new WarehouseDatabaseException("Unable to insert item", e);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(connection);
        }
    }

    private Connection createConnection() {
        Properties props = new Properties();
        props.setProperty("user", credentials.getUsername());
        props.setProperty("password", credentials.getPassword());

        try {
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            throw new WarehouseDatabaseException("Unable to establish connection to database", e);
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
