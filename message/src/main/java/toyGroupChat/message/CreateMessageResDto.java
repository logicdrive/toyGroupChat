package toyGroupChat.message;

import lombok.Getter;
import lombok.ToString;

import toyGroupChat.domain.Message;

@Getter
@ToString
public class CreateMessageResDto {
    private final Long id;

    public CreateMessageResDto(Message message) {
        this.id = message.getId();
    }
}
