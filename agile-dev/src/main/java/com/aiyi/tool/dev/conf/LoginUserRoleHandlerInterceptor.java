package com.aiyi.tool.dev.conf;

import com.aiyi.core.exception.AccessOAuthException;
import com.aiyi.core.exception.ServiceInvokeException;
import com.aiyi.core.util.thread.ThreadUtil;
import com.aiyi.tool.dev.entity.UserVO;
import com.aiyi.tool.dev.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Spring Security 登录配置
 * @author gsk
 */
@Component
public class LoginUserRoleHandlerInterceptor implements HandlerInterceptor {

    protected static final Logger logger = LoggerFactory.getLogger(LoginUserRoleHandlerInterceptor.class);

    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //跨域 Header
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        String reqUrl = request.getRequestURI();
        if (reqUrl.contains(".") &&
                reqUrl.substring(reqUrl.lastIndexOf(".")).equalsIgnoreCase(".html")){
            return true;
        }
        if (reqUrl.contains("/login") || reqUrl.contains("/logout") || reqUrl.contains("/doLogin")){
            return true;
        }
        String token = request.getHeader("auth_token");
        if (null == token){
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie: cookies){
                if (cookie.getName().equals("auth_token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (null == token){
            token = request.getParameter("token");
        }
        if(null == token){
            String header = request.getHeader("Content-Type");
            if (null != header && header.toUpperCase().contains("APPLICATION/JSON")){
                throw new AccessOAuthException("请先登录");
            }else{
                try {
                    response.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        UserVO userVO = userService.checkLogin(token);

        initContext(request, userVO, response);
        ThreadUtil.setToken(token);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        return;
    }

    /**
     * 初始化本次请求上下文
     * @param request
     *          请求方法
     * @param user
     *          当前用户
     */

    private void initContext(HttpServletRequest request, UserVO user, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("ctx", request.getContextPath());

        ThreadUtil.setUserEntity(user);
        ThreadUtil.setUserName(user.getAccount());
        ThreadUtil.setUserId(user.getId());

        String requestId = request.getHeader("requestId");
        if (StringUtils.isEmpty(requestId))
        {
            requestId = UUID.randomUUID().toString();
        }
        ThreadUtil.setRequestId(requestId);
        // 跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //跨域 Header
        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        logger.info("requestURI:[{}], requestID:[{}], requestUser:[{}], cuskAddr:[{}]", request.getRequestURI(),
                requestId, user.getAccount(), request.getRemoteAddr());
    }
}
