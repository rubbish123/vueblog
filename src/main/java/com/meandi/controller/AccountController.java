package com.meandi.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meandi.common.dto.LoginDto;
import com.meandi.common.lang.Result;
import com.meandi.pojo.User;
import com.meandi.service.UserService;
import com.meandi.utils.JwtUtils;
import io.swagger.annotations.Authorization;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        if(user==null){
            return Result.fail("用户不存在");
        }
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        //密码没问题，生成jwt
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        //表示Authorization可以暴露给外部访问
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );
    }

    //需要认证过才能登出
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        //shiro登出
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
