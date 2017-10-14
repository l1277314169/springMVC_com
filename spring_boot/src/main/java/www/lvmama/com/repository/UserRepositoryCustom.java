package www.lvmama.com.repository;

import www.lvmama.com.vo.UserVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public interface UserRepositoryCustom {

    List<UserVO> findUserAll() throws InvocationTargetException, IllegalAccessException;

    long getTotal();
}
