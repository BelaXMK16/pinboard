package com.bbogdandy.pinboard.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBoardRequest {

    @JsonProperty("isPublic")  // keeps frontend JSON field the same
    private boolean publicBoard;

    private String title;
    private String description;
    private String type;          // normal, calendar, kanban
    private String startingDate;  // optional, used for calendar boards
}
