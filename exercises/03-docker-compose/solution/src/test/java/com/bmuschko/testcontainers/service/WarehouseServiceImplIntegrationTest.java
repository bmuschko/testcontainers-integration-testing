package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.repository.warehouse.UsernamePasswordCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.SolrContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.math.BigDecimal;

import static com.bmuschko.testcontainers.repository.warehouse.ProductIndexRepositoryImpl.PRODUCTS_COLLECTION;
import static org.testcontainers.containers.Container.ExecResult;

@Testcontainers
public class WarehouseServiceImplIntegrationTest {
    private WarehouseService warehouseService;

    @Container
    private final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:9.6.12")
            .withInitScript("warehouse.sql")
            .withDatabaseName("warehouse");

    @Container
    private final SolrContainer solrContainer = new SolrContainer(DockerImageName.parse("solr:8.9.0"));

    @BeforeEach
    public void setUp() {
        UsernamePasswordCredentials postgreSqlCredentials = new UsernamePasswordCredentials(postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        createProductionsCollection();
        warehouseService = new WarehouseServiceImpl(postgreSQLContainer.getJdbcUrl(), postgreSqlCredentials, createSolrUrl());
    }

    @Test
    public void testInsertItem() {
        warehouseService.addProduct("Clock", new BigDecimal(39.99), "Household");
    }

    private String createSolrUrl() {
        return "http://" + solrContainer.getContainerIpAddress() + ":" + solrContainer.getSolrPort() + "/solr";
    }

    private void createProductionsCollection() {
        try {
            ExecResult execResult = solrContainer.execInContainer("solr", "create", "-c", PRODUCTS_COLLECTION);

            if (execResult.getExitCode() != 0) {
                throw new IllegalStateException("Cannot set up products collection in Solr");
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Cannot set up products collection in Solr");
        }
    }
}
