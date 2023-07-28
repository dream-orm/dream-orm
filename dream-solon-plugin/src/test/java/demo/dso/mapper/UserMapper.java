package demo.dso.mapper;

import com.moxa.dream.system.annotation.Mapper;
import demo.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> selectList();
}
