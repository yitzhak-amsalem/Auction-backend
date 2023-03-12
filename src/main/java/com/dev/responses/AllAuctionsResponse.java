package com.dev.responses;

import com.dev.models.AuctionModel;
import lombok.Data;

import java.util.List;
@Data
public class AllAuctionsResponse extends BasicResponse{
    List<AuctionModel> allAuctions;

    public AllAuctionsResponse(boolean success, Integer errorCode, List<AuctionModel> allAuctions) {
        super(success, errorCode);
        this.allAuctions = allAuctions;
    }
}
