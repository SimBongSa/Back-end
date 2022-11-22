package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @GetMapping("/chatroom")    //  채팅방 목록 조회
    public ResponseDto getChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails){   // todo : 마지막 채팅 1줄도 같이 포함해서 반환 , 채팅방 이름이 대화 상대일 경우 로직

        log.info("member [ {} ] called getChatRoom", userDetails.getMember().getMemberId());
        return ResponseDto.success( chatService.getChatRoom(userDetails.getMember().getMemberId()));
    }


    @GetMapping("/chatroom/{id}/history")
    public ResponseDto getChatRoomHistory(@PathVariable String id){     // todo : 채팅 기록 조회 ( 재접속시 ) - 사용자 인증, 인가 정보 필요 @AuthenticationPrincipal UserDetailsImpl userDetails,
        // todo : 구현
        return chatService.getChatRoomHistory(id);
    }

    @PostMapping("/chatroom")   // 채팅방 생성
    public ResponseDto createChatRoom(@RequestBody ChatCreateRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("member [ {} {} ] called createChatRoom", userDetails.getMember().getMemberId(),userDetails.getMember().getNickname());
        return ResponseDto.success(chatService.createChatRoom(userDetails.getMember(), dto));
    }

    @DeleteMapping("/chatroom/{id}")
    public ResponseDto deleteChatRoom(@PathVariable String id){       // todo : 채팅방 삭제 - 사용자 인증, 인가 정보 필요 @AuthenticationPrincipal UserDetailsImpl userDetails,
        // todo : 구현
        log.info("member [ 수정필요(인증정보 부분) ] called deleteChatRoom");
        return ResponseDto.success(null);
    }

    @PostMapping("/chatroom/{id}")
    public ResponseDto addMemberToChatRoom(@PathVariable String id){       // todo : 채팅방 멤버 추가 - 사용자 인증, 인가 정보 필요 @AuthenticationPrincipal UserDetailsImpl userDetails,
        // todo : 구현
        log.info("member [ 수정필요(인증정보 부분) ] called deleteChatRoom");
        return ResponseDto.success(null);
    }


}


