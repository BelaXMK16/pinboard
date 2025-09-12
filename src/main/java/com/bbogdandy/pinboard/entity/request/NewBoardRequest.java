package com.bbogdandy.pinboard.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBoardRequest {
    public boolean isPublic;
    public String title;
    public String description;
    public String type; //calendar,kanban
    public String startingDate;

}
