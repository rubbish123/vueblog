package com.meandi.service.impl;

import com.meandi.pojo.Blog;
import com.meandi.mapper.BlogMapper;
import com.meandi.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Meandi
 * @since 2020-12-10
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
