package toyGroupChat.domain.message.controller.resDtos;

import java.util.List;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class MessagesWithCheckResDto {
    private final List<MessageWithCheckResDto> messages;

    public MessagesWithCheckResDto(List<MessageWithCheckResDto> messages) {
        this.messages = messages;
    }
}