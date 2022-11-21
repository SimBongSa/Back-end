package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.ChatRoomRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    public String getChatRoom(Long id){       // todo : 채팅방 목록 조회 ( 재접속시 )
        Member member = memberRepository.findById(id).orElse(null);// todo :  orElseThrow 사용 예외처리
        if(member==null){
            return null;
        }else{
            return member.getChatRoomIdList();
        }
    }

    public Long createChatRoom(Member member, ChatCreateRequestDto createRequestDto){   // todo : 유효성 검증로직 추가 (멤버리스트)
        String chatRoomIdList = member.getChatRoomIdList();
        ChatRoom save = chatRoomRepository.save(new ChatRoom(createRequestDto));
        return save.getChatRoomId();
    }


    public ResponseDto getChatRoomHistory(@PathVariable String id){     // todo : 채팅 기록 조회 ( 재접속시 )

        return ResponseDto.success( null);
    }
}
