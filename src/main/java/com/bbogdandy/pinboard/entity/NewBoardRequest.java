package com.bbogdandy.pinboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBoardRequest {
    public String name;
    public boolean isPublic;

}
