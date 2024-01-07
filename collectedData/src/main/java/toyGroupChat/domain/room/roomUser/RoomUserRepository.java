package toyGroupChat.domain.room.roomUser;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "roomUsers",
    path = "roomUsers"
)
public interface RoomUserRepository
    extends PagingAndSortingRepository<RoomUser, Long> {
    Optional<RoomUser> findByRoomId(Long roomId);
}
