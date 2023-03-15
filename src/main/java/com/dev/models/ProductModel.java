package com.dev.models;

import com.dev.objects.Product;
import lombok.Data;

@Data
public class ProductModel {
    private Integer productID;
    private String name;
    private String description;
    private String imageLink;
    private int price;
    private UserModel owner;
    public ProductModel(Product product){
        this.productID = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.imageLink = product.getImageLink();
        this.price = product.getPrice();
        this.owner = new UserModel(product.getOwner());
    }
}
