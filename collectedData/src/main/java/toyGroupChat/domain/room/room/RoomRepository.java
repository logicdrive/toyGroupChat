package toyGroupChat.domain.room.room;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "rooms",
    path = "rooms"
)
public interface RoomRepository
    extends PagingAndSortingRepository<Room, Long> {
    Optional<Room> findByRoomId(Long roomId);
    Optional<Room> findBySharedCode(String sharedCode);
}
