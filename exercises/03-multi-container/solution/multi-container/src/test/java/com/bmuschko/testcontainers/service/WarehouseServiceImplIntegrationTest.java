package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.model.warehouse.Product;
import com.bmuschko.testcontainers.repository.warehouse.db.UsernamePasswordCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.SolrContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Testcontainers
public class WarehouseServiceImplIntegrationTest {
    private WarehouseService warehouseService;

    @Container
    private final JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.12")
            .withInitScript("warehouse.sql")
            .withDatabaseName("warehouse");

    @Container
    private final SolrContainer solrContainer = new SolrContainer(DockerImageName.parse("solr:8.9.0"));

    @BeforeEach
    public void setUp() {
        UsernamePasswordCredentials postgreSqlCredentials = new UsernamePasswordCredentials(postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        String solrUrl = createSolrUrl();
        createProductsCollection(solrUrl);
        warehouseService = new WarehouseServiceImpl(postgreSQLContainer.getJdbcUrl(), postgreSqlCredentials, solrUrl);
    }

    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setName("Clock");
        product.setPrice(new BigDecimal(39.99));
        product.setCategory("Household");
        warehouseService.addProduct(product);
    }

    private String createSolrUrl() {
        return "http://" + solrContainer.getContainerIpAddress() + ":" + solrContainer.getSolrPort() + "/solr";
    }

    private void createProductsCollection(String solrBaseUrl) {
        String createCollectionURL = solrBaseUrl + "/admin/collections?action=CREATE&name=products&numShards=1";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(createCollectionURL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Cannot set up products collection in Solr: " + new String(response.body().getBytes()));
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Cannot set up products collection in Solr");
        }
    }
}
