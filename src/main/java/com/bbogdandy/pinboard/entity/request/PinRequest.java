package com.bbogdandy.pinboard.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinRequest {
    public long id;

    public long boardId;
    public String pinColor;
    public int x;
    public int y;
    public String content;
}
