package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInfoDTO {
    @Setter
    private Long id;
    @Setter
    private boolean isPublic;
    @Setter
    private String title;
    @Setter
    private String description;


    public BoardInfoDTO(Board board) {
        this.id = board.getId();
        this.isPublic = board.isPublic();
        this.title = board.getTitle();
        this.description = board.getDescription();
    }
}
