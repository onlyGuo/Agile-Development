package com.aiyi.tool.dev.service.impl;

import com.aiyi.core.beans.Method;
import com.aiyi.core.exception.AccessOAuthException;
import com.aiyi.core.sql.where.C;
import com.aiyi.core.util.MD5;
import com.aiyi.core.util.cache.CacheUtils;
import com.aiyi.tool.dev.dao.UserDao;
import com.aiyi.tool.dev.entity.UserVO;
import com.aiyi.tool.dev.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.UUID;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-16 10:28
 * @Email 719348277@qq.com
 * @Description: 用户业务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public String login(String loginName, String password) {
        UserVO user = userDao.get(Method.where("ACCOUNT", C.EQ, loginName));
        if (null == user){
            user = userDao.get(Method.where("EMAIL", C.EQ, loginName));
        }
        if (null == user){
            throw new ValidationException("用户不存在");
        }
        password = MD5.getMd5(password);
        if (!password.equals(user.getPassword())){
            throw new ValidationException("登录名或密码错误");
        }

        String token = UUID.randomUUID().toString();
        CacheUtils.set("TOKEN_" + token, user.getId(), 1000 * 60);
        return token;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public UserVO checkLogin(String token) {
        if (null == token){
            throw new AccessOAuthException("权限认证失败");
        }
        String userId = CacheUtils.get("TOKEN_" + token);
        if (null == userId){
            throw new AccessOAuthException("权限认证失败");
        }
        UserVO userVO = userDao.get(userId);
        if (null == userVO){
            throw new AccessOAuthException("权限认证失败");
        }
        return userVO;
    }
}