package com.meandi.common.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Validated
public class LoginDto implements Serializable {
    @NotEmpty(message = "昵称不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
