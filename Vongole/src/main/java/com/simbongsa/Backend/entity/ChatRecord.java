package com.simbongsa.Backend.entity;

import com.simbongsa.Backend.dto.websocket.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecord extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatRoom chatRoom;

    public ChatRecord(MessageDto messageDto, ChatRoom chatRoom) {
        this.content = messageDto.getContent();
        this.name = messageDto.getUserName();
        this.chatRoom = chatRoom;
    }
}
