package toyGroupChat.domain.message.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import toyGroupChat.domain.message.controller.reqDtos.MessageWithCheckReqDto;
import toyGroupChat.domain.message.controller.reqDtos.MessagesWithCheckReqDto;
import toyGroupChat.domain.message.controller.resDtos.MessageWithCheckResDto;
import toyGroupChat.domain.message.controller.resDtos.MessagesWithCheckResDto;
import toyGroupChat.domain.message.exceptions.MessageNotFoundException;
import toyGroupChat.domain.message.message.Message;
import toyGroupChat.domain.message.message.MessageRepository;
import toyGroupChat.domain.message.viewHistory.ViewHistory;
import toyGroupChat.domain.message.viewHistory.ViewHistoryRepository;
import toyGroupChat.domain.room.roomUser.RoomUser;
import toyGroupChat.domain.room.roomUser.RoomUserRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final RoomUserRepository roomUserRepository;
    
    // 그룹 채팅 방 내부의 모든 메세지를 조회 횟수와 함께 받기 위해서
    public MessagesWithCheckResDto messagesWithCheck(MessagesWithCheckReqDto createMessageReqDto, Long userId) {
        List<Message> roomMessages = this.messageRepository.findAllByRoomId(createMessageReqDto.getRoomId());
        List<RoomUser> roomUsers = this.roomUserRepository.findAllByRoomId(createMessageReqDto.getRoomId());

        List<MessageWithCheckResDto> MessageWithCheckResDtos = new ArrayList<>();
        for(Message roomMessage : roomMessages) {
            if(!this.viewHistoryRepository.findByMessageIdAndUserId(roomMessage.getMessageId(), userId).isPresent())
            {
                this.viewHistoryRepository.save(
                    ViewHistory.builder()
                        .messageId(roomMessage.getMessageId())
                        .userId(userId)
                        .build()
                );
            }

            List<ViewHistory> messageViewHistories = this.viewHistoryRepository.findByMessageId(roomMessage.getMessageId());
            MessageWithCheckResDtos.add(new MessageWithCheckResDto(roomMessage, roomUsers.size() - messageViewHistories.size()));
        }
    
        return new MessagesWithCheckResDto(MessageWithCheckResDtos);
    }

    // 조회 횟수와 함께 메세지를 받기 위해서
    public MessageWithCheckResDto messageWithCheck(MessageWithCheckReqDto messageWithCheckReqDto, Long userId) {
        Optional<Message> messageOptional = this.messageRepository.findByMessageId(messageWithCheckReqDto.getMessageId());
        if(!messageOptional.isPresent()) throw new MessageNotFoundException();
        Message searchedMessage = messageOptional.get();
        
        if(!this.viewHistoryRepository.findByMessageIdAndUserId(searchedMessage.getMessageId(), userId).isPresent())
        {
            this.viewHistoryRepository.save(
                ViewHistory.builder()
                    .messageId(searchedMessage.getMessageId())
                    .userId(userId)
                    .build()
            );
        }

        List<ViewHistory> messageViewHistories = this.viewHistoryRepository.findByMessageId(searchedMessage.getMessageId());
        List<RoomUser> roomUsers = this.roomUserRepository.findAllByRoomId(searchedMessage.getRoomId());

        return new MessageWithCheckResDto(searchedMessage, roomUsers.size() - messageViewHistories.size());
    }
}
