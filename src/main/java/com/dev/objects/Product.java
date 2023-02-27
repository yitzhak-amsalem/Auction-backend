package com.dev.objects;

import lombok.Data;

@Data
public class Product {
    private final String name;
    private final String description;
    private final String imageLink;
    private final Integer price;

    private final User owner;
}
