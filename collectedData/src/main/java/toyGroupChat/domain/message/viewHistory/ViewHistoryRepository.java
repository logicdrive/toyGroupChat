package toyGroupChat.domain.message.viewHistory;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "viewHistories",
    path = "viewHistories"
)
public interface ViewHistoryRepository
    extends PagingAndSortingRepository<ViewHistory, Long> {
}
