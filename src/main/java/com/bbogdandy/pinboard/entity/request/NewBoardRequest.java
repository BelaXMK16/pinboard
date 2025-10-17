package com.bbogdandy.pinboard.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBoardRequest {

    @JsonProperty("isPublic")
    private boolean publicBoard;

    private String title;
    private String description;
    private String type;
    private String startingDate;
    private List<String> columns;

}
