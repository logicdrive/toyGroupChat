package toyGroupChat.domain.message.message;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "messages",
    path = "messages"
)
public interface MessageRepository
    extends PagingAndSortingRepository<Message, Long> {
    Optional<Message> findByMessageId(Long messageId);
    List<Message> findAllByRoomId(Long roomId);
}
