package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.types.PinType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinDTO extends Board {
    private Long id;
    private String ownerName;
    private long ownerId;
    private long boardId;
    private int x;
    private int y;
    private String content;
    private PinType pinType;

    public PinDTO(Pin pin) {
        this.id = pin.getId();
        this.ownerName = pin.getOwner().getUsername();
        this.ownerId = pin.getOwner().getId();
        this.boardId = pin.getBoard().getId();
        this.x = pin.getX();
        this.y = pin.getY();
        this.content = pin.getContent();
        this.pinType = pin.getPinType();
    }
}
