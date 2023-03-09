package com.dev.responses;

import com.dev.models.StatisticsModel;
import lombok.Data;

@Data
public class StatisticsResponse extends BasicResponse{
    private StatisticsModel statisticsModel;
}
