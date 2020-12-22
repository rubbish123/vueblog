package com.meandi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import cn.hutool.crypto.SecureUtil;

@SpringBootTest
class VueblogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        System.out.println(SecureUtil.md5("831143"));
    }

}
