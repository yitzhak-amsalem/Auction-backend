package com.dev.objects;

import lombok.Data;

import java.util.Map;

@Data
public class Auction {
    private final Boolean isOpen;
    private final User publisher;
    private final Map<User, Integer> offers;
}
