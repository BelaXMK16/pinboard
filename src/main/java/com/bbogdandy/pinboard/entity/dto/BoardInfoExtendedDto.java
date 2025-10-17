package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInfoExtendedDto extends BoardInfoDTO{

    @Setter
    private String ownerName;
    @Setter
    private String ownerEmail;
    @Setter
    private List<PinDTO> pins;
    private List<String> columns;

    public void addPin(PinDTO pin){
        pins.add(pin);
    }


    public BoardInfoExtendedDto(Board board) {
        super(board);

        this.ownerName = board.getOwner().getUsername();
        this.ownerEmail = board.getOwner().getEmail();
        this.pins = new ArrayList<>();
        this.columns = board.getColumnsAsList();
        for (Pin pin : board.getPins()) {
            PinDTO pinDTO = new PinDTO(pin);
            pins.add(pinDTO);
        }
    }
}
