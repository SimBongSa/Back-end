package com.simbongsa.Backend.websocket;

import com.simbongsa.Backend.dto.websocket.MessageDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatSocketController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ChatRoomRepository chatRoomRepository;

	@MessageMapping("/hello/{id}")
	public void socketHandle(@DestinationVariable("id") String id, MessageDto message) throws Exception {

		log.info("socketHandle: id={}, MessageDto={}",id,message);

		if(message.getAction().equals("MESSAGE")){
			ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).orElse(null);	// todo : null 예외처리
			String[] s = chatRoom.getUserIdList().split(" ");
			for (String s1 : s) {
				log.trace("socketHandle MESSAGE send to : (id){}",s1);
				simpMessagingTemplate.convertAndSend("/topic/greetings/" + s1 ,
						new MessageDto(message.getAction(), message.getChatRoomId(), message.getUserName(),message.getContent()));
			}

		}else{
			// todo : 예외처리 " -알수없는 요청- "
		}
	}
}
