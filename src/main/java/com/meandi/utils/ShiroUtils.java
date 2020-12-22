package com.meandi.utils;

import com.meandi.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

//工具类，获取当前登录用户的profile，用于修改博客时的判断
public class ShiroUtils {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
