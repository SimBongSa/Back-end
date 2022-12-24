package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.websocket.ChatCreateRequestDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.exception.GlobalException;
import com.simbongsa.Backend.repository.ChatRecordRepository;
import com.simbongsa.Backend.repository.ChatRoomRepository;
import com.simbongsa.Backend.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@Transactional
@SpringBootTest
class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatRecordRepository chatRecordRepository;

    Member member1;
    Member member2;

    @BeforeEach
    void before(){
        member1 = Member.builder().
                username("member1").
                name("member1").
                password("1").
                build();
        member2 = Member.builder().
                username("member2").
                name("member2").
                password("1").
                chatRoomIdList("1").
                build();
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    @DisplayName("채팅방 생성 성공")
    void createChatRoom(){
        ChatCreateRequestDto chatCreateRequestDto = new ChatCreateRequestDto();
        chatCreateRequestDto.setRoomName("chatroom1");
        chatCreateRequestDto.setBoardId(1L);
        chatCreateRequestDto.setUserIdList(""+member2.getMemberId());
        chatCreateRequestDto.setUserNameList("member2");

        Long chatRoom = chatService.createChatRoom(member1, chatCreateRequestDto);

        ChatRoom chatRoom1 = chatRoomRepository.findById(chatRoom).orElse(null);

        Assertions.assertThat(chatRoom1).isNotNull();
        Assertions.assertThat(chatRoom1.getRoomName()).isEqualTo("chatroom1");
        Assertions.assertThat(chatRoom1.getUserIdList()).isEqualTo(member2.getMemberId()+" "+ member1.getMemberId());
        Assertions.assertThat(chatRoom1.getUserNameList()).isEqualTo("member2 member1");

        Member member1local = memberRepository.findByMemberId(member1.getMemberId()).orElse(null);
        Member member2local = memberRepository.findByMemberId(member2.getMemberId()).orElse(null);
        Assertions.assertThat(member1local.getChatRoomIdList()).contains(chatRoom1.getChatRoomId()+"");
        Assertions.assertThat(member2local.getChatRoomIdList()).contains(chatRoom1.getChatRoomId()+"");
    }

    @Test
    @DisplayName("채팅방 생성 실패(없는 유저를 상대로 생성)")
    void createChatRoomFail(){
        Member member = memberRepository.findByMemberId(1L).orElse(null);
        if(member != null){
            memberRepository.delete(member);
        }
        ChatCreateRequestDto chatCreateRequestDto = new ChatCreateRequestDto();
        chatCreateRequestDto.setRoomName("chatroom1");
        chatCreateRequestDto.setBoardId(1L);
        chatCreateRequestDto.setUserIdList("1");
        chatCreateRequestDto.setUserNameList("nullMember");

        Assertions.assertThatThrownBy(()->chatService.createChatRoom(member1, chatCreateRequestDto)).isInstanceOf(GlobalException.class);
    }

}
