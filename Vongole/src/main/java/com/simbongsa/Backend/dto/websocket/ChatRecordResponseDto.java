package com.simbongsa.Backend.dto.websocket;

import com.simbongsa.Backend.entity.ChatRecord;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class ChatRecordResponseDto {
    private Long id;
    private String content;
    private String name;
    private String createdAt;

    public ChatRecordResponseDto(ChatRecord record) {
        this.id = record.getId();
        this.content = record.getContent();
        this.name = record.getName();
        this.createdAt = record.getCreatedAt().toString();
    }
}
