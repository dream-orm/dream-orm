package demo.dso.service;

import demo.dso.mapper.UserMapper;
import demo.model.User;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;

@Component
public class UserService {
    @Inject
    UserMapper userMapper;

    public List<User> getUserList() {
        assert userMapper != null;

        return userMapper.selectList();
    }
}
