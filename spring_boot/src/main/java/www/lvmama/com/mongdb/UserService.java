package www.lvmama.com.mongdb;

import www.lvmama.com.entity.UserEntity;
import www.lvmama.com.entity.UserVO;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface UserService {

    public void saveUser(UserVO userVO);

    public UserVO findUserByUserName(String userName);

    public List<UserVO> findUserAll();

    public int updateUser(UserVO userVO);

    public void deleteUserById(Long id);
}
