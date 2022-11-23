package com.simbongsa.Backend.dto.websocket;

import com.simbongsa.Backend.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long chatRoomId;
    private String userIdList;
    private String userNameList;
    private String roomName;

    public ChatRoomDto(ChatRoom entity) {
        this.chatRoomId = entity.getChatRoomId();
        this.userIdList = entity.getUserIdList();
        this.userNameList = entity.getUserNameList();
        this.roomName = entity.getRoomName();
    }
}
