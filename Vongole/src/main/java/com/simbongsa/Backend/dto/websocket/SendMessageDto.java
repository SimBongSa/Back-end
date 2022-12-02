package com.simbongsa.Backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageDto {

    private String action;
    private Long chatRoomId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;

}
