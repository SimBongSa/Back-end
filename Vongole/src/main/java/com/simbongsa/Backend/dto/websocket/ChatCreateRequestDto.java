package com.simbongsa.Backend.dto.websocket;

import com.simbongsa.Backend.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class ChatCreateRequestDto {

    private String userIdList;
    private String userNameList;
    private String roomName;

    public void addUser(Member member){
        userIdList = userIdList + " " + member.getMemberId().toString();
        userNameList = userNameList + " " + member.getName();
    }
}
