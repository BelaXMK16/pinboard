package com.bbogdandy.pinboard.entity.request;

import lombok.Getter;

public class PinConnectionRequest {
    @Getter
    private Long fromId;
    @Getter
    private Long toId;
    @Getter
    private String color;

}