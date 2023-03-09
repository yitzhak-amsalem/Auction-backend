package com.dev.models;

import com.dev.objects.Product;
import lombok.Data;

@Data
public class ProductModel {
    private String name;
    private String description;
    private String imageLink;
    private UserModel owner;
    public ProductModel(Product product){
        this.name = product.getName();
        this.description = product.getDescription();
        this.imageLink = product.getImageLink();
        this.owner = new UserModel(product.getOwner());
    }
}
