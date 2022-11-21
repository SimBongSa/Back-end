package com.simbongsa.Backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class ChatCreateRequestDto {

    private String userIdList;
    private String userNameList;
    private String roomName;
}
