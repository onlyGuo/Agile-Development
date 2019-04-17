package com.aiyi.tool.dev.controller;

import com.aiyi.tool.dev.entity.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-12 18:57
 * @Email 719348277@qq.com
 * @Description: 登录页面控制
 */
@Controller
public class LoginController {


    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("doLogin")
    public String doLogin(@RequestBody UserVO userVO){
        System.out.printf(userVO.toString());
        return null;
    }

}