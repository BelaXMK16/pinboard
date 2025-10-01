package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.KanbanBoard;
import com.bbogdandy.pinboard.model.Pin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @Setter
    private LocalDate startDate;
    @Setter
    private String boardType;

    public BoardInfoDTO(Board board) {
        this.id = board.getId();
        this.boardType = board.echoType();
        this.isPublic = board.isPublic();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.startDate = board.getDate();
    }
}
