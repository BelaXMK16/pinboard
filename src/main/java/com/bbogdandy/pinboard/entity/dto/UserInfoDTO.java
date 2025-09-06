package com.bbogdandy.pinboard.entity.dto;

import com.bbogdandy.pinboard.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO  {
    private long id;
    private String username;
    private String email;

    public UserInfoDTO(UserInfo user) {
        username = user.getUsername();
        email = user.getEmail();
        id = user.getId();
    }
}
