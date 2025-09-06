package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.Board;
import com.bbogdandy.pinboard.model.Pin;
import com.bbogdandy.pinboard.model.UserInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoExtendedDTO extends UserInfoDTO{
    @Getter
    @Setter
    private List<PinDTO> pins;

    @Getter
    @Setter
    private List<BoardInfoDTO> boards;


    public UserInfoExtendedDTO(UserInfo user,long askerId) {
        //TODO: ha NEM ugyanaz a user aki lekérte az információt az adott felhasználóról akkor ne rakja bele a nem nyilvanos board-okat csak ha
        super(user);
        pins = new ArrayList<>();
        for(Pin pin : user.getPins()){
            pins.add(new PinDTO(pin));
        }
        boards = new ArrayList<>();
        for(Board board : user.getBoards()){
            if(askerId == this.getId() || board.isPublic()){
            boards.add(new BoardInfoDTO(board));}
        }
    }
}
