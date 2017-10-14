package www.lvmama.com.repository;



import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import www.lvmama.com.vo.UserVO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/10.
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private EntityManager em;
    @Override
    public List<UserVO> findUserAll() throws InvocationTargetException, IllegalAccessException {
        String sql = "select u.id,u.age,u.`name`,u.id_Card as idCard,c.cardcode from user u \n" +
                "inner JOIN card c on u.id_card = c.id";
        Query query = em.createNativeQuery(sql);
        List<Map<String,Object>> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<UserVO> userVOList = new ArrayList<>();
        for (Map<String,Object> userVO : list) {
            UserVO u = new UserVO();
            BeanUtils.populate(u,userVO);
            userVOList.add(u);
        }
        return userVOList;
    }

    @Override
    public long  getTotal() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

//        Subquery<UserEntity> subquery = cq.subquery(UserEntity.class);
//        Root<UserEntity> from = subquery.from(UserEntity.class);
//        Root<UserEntity> root = cq.from(subquery.getResultType());
//        subquery.groupBy(from.get("name")).select(from);



        return 0;
    }

}
