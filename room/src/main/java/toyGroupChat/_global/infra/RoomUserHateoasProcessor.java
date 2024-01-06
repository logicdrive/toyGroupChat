package toyGroupChat._global.infra;

import toyGroupChat.domain.RoomUser;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

// REST 응답시에 추가적인 참조 URL들을 포함시키기 위해서
@Component
public class RoomUserHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<RoomUser>> {

    @Override
    public EntityModel<RoomUser> process(EntityModel<RoomUser> model) {
        return model;
    }
}
