package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoom {
    public ChatRoom(ChatCreateRequestDto dto) {
        this.userIdList = dto.getUserIdList();
        this.userNameList = dto.getUserNameList();
        this.roomName = dto.getRoomName();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @Column
    private String userIdList;

    @Column
    private String userNameList;

    @Column
    private String roomName;

}
