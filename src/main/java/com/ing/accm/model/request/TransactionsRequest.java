package com.ing.accm.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ing.accm.util.ZonedDateTimeDeserializer;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TransactionsRequest {

    private Long accountId;

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime beginWith;
}
