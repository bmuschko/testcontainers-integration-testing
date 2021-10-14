package com.bmuschko.testcontainers.repository.warehouse;

import org.apache.solr.client.solrj.beans.Field;

public class Product {
    @Field
    private String name;

    @Field
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
