package toyGroupChat._global.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import toyGroupChat.domain.User;

// REST 응답시에 추가적인 참조 URL들을 포함시키기 위해서
@Component
public class UserHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        return model;
    }
}
