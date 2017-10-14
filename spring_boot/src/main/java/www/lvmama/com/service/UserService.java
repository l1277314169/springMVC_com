package www.lvmama.com.service;

import org.apache.catalina.User;
import www.lvmama.com.entity.UserEntity;

import java.util.List;

/**
 * Created by liuhonger on 2016/12/19.
 */
public interface UserService {

  public UserEntity getUserList();

  public List<UserEntity> findAllOnToOn(String name);

  public List<UserEntity> findAllOnToMany(String name);

  public int updateUser() throws InterruptedException;
  public int updateUser1();

}
