package www.lvmama.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import www.lvmama.com.entity.CardEntity;
import www.lvmama.com.entity.UserEntity;
import www.lvmama.com.repository.UserRepository;
import www.lvmama.com.service.UserService;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhonger on 2016/12/20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getUserList() {
        UserEntity user = new UserEntity();
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAllOnToOn(String name) {
        Specification<UserEntity> specification = getSpecification(name);
        List<UserEntity> list = userRepository.findAll(specification);
        for (UserEntity userEntity : list) {

        }
        return list;
    }

    @Override
    public List<UserEntity> findAllOnToMany(String name) {
        Specification<UserEntity> specification = getSpecification(name);
        List<UserEntity> list = userRepository.findAll(specification);
        for (UserEntity userEntity : list) {

        }
        return list;
    }

    @Override
    @Transactional
    public int updateUser() throws InterruptedException {
        UserEntity one = userRepository.findOne(1);
        System.out.println(one.getAge());
        UserEntity bb = userRepository.findOne(1);
        System.out.println(bb.getAge());
        return 0;
    }

    @Override
    @Transactional
    public int updateUser1() {
        UserEntity one = userRepository.findOne(1);
        one.setAge(32);
        userRepository.save(one);
        return 0;
    }

    public void show(){
        System.out.println("aa");
    }

    private Specification<UserEntity> getSpecification(final String name) {
        return new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                root.fetch("cardEntity", JoinType.INNER);
//                root.fetch("orderEntity", JoinType.LEFT);
                List<Predicate> list = new ArrayList<>();
                Predicate predicatem = cb.equal(root.get("cardEntity").get("cardcode"), "123132");//where 里面加另一张表的 条件的数据
                list.add(predicatem);
                if (!StringUtils.isEmpty(name)) {
                    Predicate predicate = cb.equal(root.get("name"), name);
                    list.add(predicate);
                }
                query.where(list.toArray(new Predicate[list.size()]));

                return null;
            }
        };

    }

    ;
}
