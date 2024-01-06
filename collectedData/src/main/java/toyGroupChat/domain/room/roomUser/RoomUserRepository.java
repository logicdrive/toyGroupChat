package toyGroupChat.domain.room.roomUser;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "roomUsers",
    path = "roomUsers"
)
public interface RoomUserRepository
    extends PagingAndSortingRepository<RoomUser, Long> {
}
