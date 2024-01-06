package toyGroupChat.domain.file;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "files",
    path = "files"
)
public interface FileRepository
    extends PagingAndSortingRepository<File, Long> {
    Optional<File> findByFileId(Long fileId);
}
