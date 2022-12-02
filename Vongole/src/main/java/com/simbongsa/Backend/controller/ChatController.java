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

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @GetMapping("/chatroom")    //  채팅방 목록 조회
    public ResponseDto getChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails){   // todo : 마지막 채팅 1줄도 같이 포함해서 반환 , 채팅방 이름이 대화 상대일 경우 로직

        log.info("member [ {} ] called getChatRoom", userDetails.getMember().getMemberId());
        List chatRoom = chatService.getChatRoom(userDetails.getMember().getMemberId());
        if(chatRoom==null){
            return ResponseDto.success("참여하고 있는 채팅방이 없습니다.");   // todo : 성호님한테 그냥 data 에 null 넘기면 안되는지 물어보기, 또는 빈 리스트 ?
        }
        return ResponseDto.success(chatRoom);
    }


    @GetMapping("/chatroom/{id}/history")   // 채팅 기록 조회
    public ResponseDto getChatRoomHistory(@PathVariable String id){
        return chatService.getChatRoomHistory(id);
    }

    @PostMapping("/chatroom")   // 채팅방 생성
    public ResponseDto createChatRoom(@RequestBody ChatCreateRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        log.info("member [ {} {} ] called createChatRoom", userDetails.getMember().getMemberId(),userDetails.getMember().getName());
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
