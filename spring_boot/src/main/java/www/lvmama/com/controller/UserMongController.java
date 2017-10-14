//package www.lvmama.com.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.*;
//import www.lvmama.com.entity.UserEntity;
//import www.lvmama.com.entity.UserVO;
//import www.lvmama.com.mongdb.UserService;
//import www.lvmama.com.mongdb.UserServiceImpl;
//
//import java.util.List;
//
//
///**
// * Created by Administrator on 2017/5/15.
// */
//@Api(value = "mong")
//@RestController
//public class UserMongController {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(UserMongController.class);
//
//    @Autowired
//    @Qualifier("userServiceMong")
//    private UserService userService;
//
//    @ApiOperation("新增用户")
//    @PostMapping("/addUser")
//    public void addUser(@RequestBody UserVO userVO){
//        userService.saveUser(userVO);
//    }
//    @ApiOperation("修改用户")
//    @PutMapping("/updateUser")
//    public void updateUser(@RequestBody UserVO userVO){
//        userService.updateUser(userVO);
//    }
//    @ApiOperation("查询一个用户")
//    @GetMapping("/findByUserName/{userName}")
//    public void findByUserName(@PathVariable("userName") String userName){
//        LOGGER.error("Excetion occurred",userName);
//        LOGGER.info("Excetion occurred",userName);
//        LOGGER.info("dd"+userName);
//            int a = 10/0;
//
//
//
//
//        UserVO userByUserName = userService.findUserByUserName(userName);
//        System.out.println(userByUserName);
//
//    }
//    @ApiOperation("查询用户")
//    @GetMapping("/findUserAll")
//    public void findUserAll(){
//        List<UserVO> userAll = userService.findUserAll();
//        System.out.println(userAll);
//    }
//
//
//}
