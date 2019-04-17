package com.aiyi.tool.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-12 18:57
 * @Email 719348277@qq.com
 * @Description: 项目页
 */
@Controller
@RequestMapping("project")
public class ProjectController {

    @RequestMapping("/")
    public String project(){
        return "project/index";
    }

}