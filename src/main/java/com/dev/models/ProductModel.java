package com.dev.models;

import com.dev.objects.Products;
import lombok.Data;

@Data
public class ProductModel {
    private String name;
    private String imageLink;
    private UserModel owner;
    public ProductModel(Products products){
        this.name = products.getName();
        this.imageLink = products.getImageLink();
        this.owner = new UserModel(products.getOwner());
    }
}
