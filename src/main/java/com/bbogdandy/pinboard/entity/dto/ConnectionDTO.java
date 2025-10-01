package com.bbogdandy.pinboard.entity.dto;

public class ConnectionDTO {
    public final long id;
    public final long fromId;
    public final long toId;
    public final String color;

    public ConnectionDTO(long id, long fromId, long toId, String color) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.color = color;
    }

}
