package www.lvmama.com.controller;

import com.whalin.MemCached.MemCachedClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.lvmama.com.repository.UserRepositoryCustom;
import www.lvmama.com.service.UserService;
import www.lvmama.com.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhonger on 2016/12/19.
 */
@Api(value = "VI")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositoryCustom userRepositoryCustom;
    @Autowired
    private MemCachedClient memCachedClient;
    @ApiOperation("查询用户OnToOn")
    @GetMapping("/OnToOn")
    public String OnToOn() {
        userService.findAllOnToOn(null);
        return "Hello Worl";
    }

    @ApiOperation("查询用户OnToMany")
    @GetMapping("/OnToMany")
    public String OnToMany() {
        userService.findAllOnToMany(null);
        return "Hello Worl";
    }

    @ApiOperation("查询用户本地sql")
    @GetMapping("/nativeSQl")
    public String nativeSQl() throws InvocationTargetException, IllegalAccessException {
        final long total = userRepositoryCustom.getTotal();
        return "Hello Worl";
    }

    @ApiOperation("查询用户本地sql11")
    @GetMapping("/nativeSQl11")
    public String nativeSQl1() throws InvocationTargetException, IllegalAccessException, InterruptedException {
        long total = userService.updateUser();
        return "Hello Worl";
    }


    @ApiOperation("查询用户本地sql11a")
    @GetMapping("/nativeSQl11a")
    public String nativeSQl1a() throws InvocationTargetException, IllegalAccessException {
        long total = userService.updateUser1();
        return "Hello Worl";
    }





    public static void main(String[] args) {
    }

}
