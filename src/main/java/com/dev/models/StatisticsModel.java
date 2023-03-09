package com.dev.models;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StatisticsModel {
    private Integer numOfUsers;
    private Integer numOfOffers;
    private Integer numOfAuction;
}
