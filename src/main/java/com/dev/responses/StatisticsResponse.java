package com.dev.responses;

import com.dev.models.StatisticsModel;
import lombok.Data;

@Data
public class StatisticsResponse extends BasicResponse{
    private StatisticsModel statisticsModel;

    public StatisticsResponse(boolean success, Integer errorCode, StatisticsModel statisticsModel) {
        super(success, errorCode);
        this.statisticsModel = statisticsModel;
    }
}
