package com.dev.responses;

import com.dev.responses.BasicResponse;
import lombok.Data;

@Data
public class UpdateCreditResponse extends BasicResponse {
    Double amount;

    public UpdateCreditResponse(boolean success, Integer errorCode, Double amount) {
        super(success, errorCode);
        this.amount = amount;
    }
}
