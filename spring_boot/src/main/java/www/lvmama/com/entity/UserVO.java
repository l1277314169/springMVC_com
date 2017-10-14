package www.lvmama.com.entity;

import org.bson.codecs.IntegerCodec;
import org.bson.types.ObjectId;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Created by liuhonger on 2016/12/19.N
 */
@Document(collection="tt")
public class UserVO implements Serializable{

    private static int a = 0;

    private ObjectId id;

    private Integer userId;

    private String name;

    private Integer age;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    class A implements Runnable{

        @Override
        public void run() {

        }
    }

    public static void main(String[] args)throws MessagingException {



    }
}
