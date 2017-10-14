package www.lvmama.com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "`order`")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 8341690874380070955L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_no")
    private Long orderNo;

    @Column(name="googs_name")
    private String googsName;

    @Column(name="googs_amout")
    private Double googsAmout;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserEntity userEntity;


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoogsName() {
        return googsName;
    }

    public void setGoogsName(String googsName) {
        this.googsName = googsName;
    }

    public Double getGoogsAmout() {
        return googsAmout;
    }

    public void setGoogsAmout(Double googsAmout) {
        this.googsAmout = googsAmout;
    }
}
