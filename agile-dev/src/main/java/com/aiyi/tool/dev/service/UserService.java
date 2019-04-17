package com.aiyi.tool.dev.service;

import com.aiyi.tool.dev.entity.UserVO;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-16 10:20
 * @Email 719348277@qq.com
 * @Description: 用户业务层
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginName
     *      登录名
     * @param password
     *      登录密码
     * @return
     *      用户令牌
     */
    String login(String loginName, String password);

    /**
     * 退出登录
     * @param token
     *      用户令牌
     */
    void logout(String token);

    /**
     * 验证登录
     * @param token
     *      用户令牌
     * @return
     *      用户实体
     */
    UserVO checkLogin(String token);
}