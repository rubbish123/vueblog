package com.meandi.shiro;

import cn.hutool.json.JSONUtil;
import com.meandi.common.lang.Result;
import com.meandi.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    //生成token 登录
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request= (HttpServletRequest) servletRequest;
        //从请求头中获取jwt
        String jwt = request.getHeader("Authorization");
        //没有jwt就直接返回
        if(jwt==null||"".equals(jwt)){
            return null;
        }
        return new JwtToken(jwt);
    }

    //没登录的时候走这个方法
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        //从请求头中获取jwt
        String jwt = request.getHeader("Authorization");
        //没有jwt就直接返回
        if(jwt==null||"".equals(jwt)){
            return true;
        }else {
            //校验JWT
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if(claim==null|| jwtUtils.isTokenExpired(claim.getExpiration())){
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
            //执行登录
            return executeLogin(servletRequest,servletResponse);
        }
    }

    //重写登陆失败执行的方法，将失败信息传给前端
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse= (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(throwable.getMessage());

        String json= JSONUtil.toJsonStr(result);

        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ioException) {

        }

        return false;
    }

    //跨域处理
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //复制来的，之后再了解
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
