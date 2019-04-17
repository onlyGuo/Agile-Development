package com.aiyi.tool.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-12 18:57
 * @Email 719348277@qq.com
 * @Description: 主页
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public void home(HttpServletResponse response){
        try {
            response.sendRedirect("/project/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}