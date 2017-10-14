package www.lvmama.com.mongdb;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import www.lvmama.com.entity.UserEntity;
import www.lvmama.com.entity.UserVO;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
@Component("userServiceMong")
public class UserServiceImpl implements UserService {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(UserVO userVO) {
        mongoTemplate.insert(userVO);
    }

    @Override
    public UserVO findUserByUserName(String userName) {
        Query query=new Query(Criteria.where("name").is(userName));
        UserVO user =  mongoTemplate.findOne(query , UserVO.class);
        return user;
    }


    @Override
    public List<UserVO> findUserAll() {
        return mongoTemplate.findAll(UserVO.class);
    }

    @Override
    public int updateUser(UserVO userVO) {
        Query query = new Query(Criteria.where("userId").is(userVO.getUserId()));
        Update update = new Update().set("name",userVO.getName()).set("age",userVO.getAge());
        //更新查询返回结果集的第一条
        WriteResult result =mongoTemplate.updateFirst(query,update,UserVO.class);
        //更新查询返回结果集的所有
//        mongoTemplate.updateMulti(query,update,UserEntity.class);
        if(result!=null)
            return result.getN();
        else
            return 0;
    }

    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,UserEntity.class);

    }
}
