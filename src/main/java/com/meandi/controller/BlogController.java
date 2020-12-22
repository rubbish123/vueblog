package com.meandi.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meandi.common.lang.Result;
import com.meandi.pojo.Blog;
import com.meandi.service.BlogService;
import com.meandi.shiro.AccountProfile;
import com.meandi.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Meandi
 * @since 2020-12-10
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    //分页，默认第一页
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page<Blog> page = new Page<>(currentPage, 5);
        IPage<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("create_time"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            return Result.fail("该博客已删除");
        }
        return Result.succ(blog);
    }

    //认证之后才能编辑
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        //已有id，当前是在修改已发布的
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            if (!temp.getUserId().equals(ShiroUtils.getProfile().getId())) {
                return Result.fail("没有权限编辑");
            }
        } else { //添加博客
            temp=new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreateTime(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtils.copyProperties(blog,temp,"id","userId",
                "createTime","status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }
}

