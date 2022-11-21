package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
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
    public String getChatRoom(Long id){       // 채팅방 목록 조회 ( 재접속시 )
        Member member = memberRepository.findById(id).orElseThrow(()-> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getChatRoomIdList();

    }

    public Long createChatRoom(Member member, ChatCreateRequestDto createRequestDto){   // todo : 유효성 검증로직 추가 (멤버리스트) , dto userNameList 수정
        member = memberRepository.findById(member.getMemberId()).orElseThrow(()-> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        String chatRoomIdList = member.getChatRoomIdList();
        ChatRoom save = chatRoomRepository.save(new ChatRoom(createRequestDto));
        if(chatRoomIdList==null){
            member.setChatRoomIdList(save.getChatRoomId().toString());
        }else{
            member.setChatRoomIdList(chatRoomIdList + " " + save.getChatRoomId().toString());
        }
        return save.getChatRoomId();
    }


    public ResponseDto getChatRoomHistory(@PathVariable String id){     // todo : 채팅 기록 조회 구현 ( 재접속시 )

        return ResponseDto.success( null);
    }
}
