package com.xyxy.mall.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProfile implements Serializable {

    private String userid;

    private String username;

}
