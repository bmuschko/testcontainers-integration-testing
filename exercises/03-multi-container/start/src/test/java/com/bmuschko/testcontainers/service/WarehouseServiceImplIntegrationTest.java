package com.bmuschko.testcontainers.service;

import com.bmuschko.testcontainers.model.warehouse.Product;
import com.bmuschko.testcontainers.repository.warehouse.db.UsernamePasswordCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
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
    // Create container

    @BeforeEach
    public void setUp() {
        UsernamePasswordCredentials postgreSqlCredentials = new UsernamePasswordCredentials("postgres", "postgres");
        String solrUrl = createSolrUrl();
        createProductsCollection(solrUrl);
        warehouseService = new WarehouseServiceImpl(createPostgreSqlUrl(), postgreSqlCredentials, solrUrl);
    }

    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setName("Clock");
        product.setPrice(new BigDecimal(39.99));
        product.setCategory("Household");
        warehouseService.addProduct(product);
    }

    private String createPostgreSqlUrl() {
        return null;
    }

    private String createSolrUrl() {
        return null;
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
