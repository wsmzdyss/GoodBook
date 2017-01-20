package com.philip.goodbook.model;

/**
 * Created by philip.zhang on 2017/1/16.
 */

public class Category implements Comparable<Category> {

    /**
     * id : 242
     * catalog : 中国文学
     */

    private String id;
    private String catalog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public int compareTo(Category category) {
        return this.getId().compareTo(category.getId());
    }
}
