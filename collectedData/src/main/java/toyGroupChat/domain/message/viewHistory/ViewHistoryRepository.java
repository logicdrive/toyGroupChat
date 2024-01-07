package toyGroupChat.domain.message.viewHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "viewHistories",
    path = "viewHistories"
)
public interface ViewHistoryRepository
    extends PagingAndSortingRepository<ViewHistory, Long> {
    List<ViewHistory> findByMessageId(Long messageId);
    Optional<ViewHistory> findByMessageIdAndUserId(Long messageId, Long userId);
}
