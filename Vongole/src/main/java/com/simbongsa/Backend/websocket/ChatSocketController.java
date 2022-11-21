package com.simbongsa.Backend.websocket;

import com.simbongsa.Backend.dto.websocket.MessageDto;
import com.simbongsa.Backend.entity.ChatRoom;
import com.simbongsa.Backend.exception.ErrorCode;
import com.simbongsa.Backend.exception.GlobalException;
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

		if(message.getAction().equals(WebsocketMethod.MESSAGE.getMethod())){
			ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).orElse(null);
			if(chatRoom==null){
				simpMessagingTemplate.convertAndSend("/topic/greetings/" + id ,
						new MessageDto(WebsocketMethod.ERROR.getMethod(), null, null,"존재하지 않는 채팅방 입니다."));
				return;
			}
			String[] s = chatRoom.getUserIdList().split(" ");
			for (String s1 : s) {
				log.trace("socketHandle MESSAGE send to : (id){}",s1);
				simpMessagingTemplate.convertAndSend("/topic/greetings/" + s1 ,
						new MessageDto(message.getAction(), message.getChatRoomId(), message.getUserName(),message.getContent()));
			}

		}else{
			simpMessagingTemplate.convertAndSend("/topic/greetings/" + id ,
					new MessageDto(WebsocketMethod.ERROR.getMethod(), null, null,"알 수 없는 요청 입니다."));
		}
	}
}
