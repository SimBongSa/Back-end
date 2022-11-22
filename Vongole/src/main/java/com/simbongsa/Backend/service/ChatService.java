package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import com.simbongsa.Backend.dto.websocket.ChatRoomDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.repository.ChatRoomRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    public List getChatRoom(Long id){       // 채팅방 목록 조회 ( 재접속시 )
        Member member = memberRepository.findById(id).orElseThrow(()-> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        List list = new ArrayList<ChatRoom>();

        String chatRoomIdList = member.getChatRoomIdList();
        String[] s = chatRoomIdList.split(" ");

        for (String s1 : s) {
            log.debug("유저 [{}] 채팅방 목록조회 중 ID={}",member.getName(), s1);
            list.add(new ChatRoomDto(chatRoomRepository.findById(Long.parseLong(s1)).orElseThrow(() -> new GlobalException(ErrorCode.NO_SUCH_CHATROOM))));
        }
        return list;
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
