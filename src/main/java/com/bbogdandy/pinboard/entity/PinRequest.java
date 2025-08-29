package com.bbogdandy.pinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinRequest {
    public long boardId;
    public int x;
    public int y;
    public String content;
}
