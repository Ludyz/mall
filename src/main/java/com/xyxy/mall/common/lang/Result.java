package com.xyxy.mall.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果集
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;//200是正常，非200表示异常

    private String msg;
    private Object data;

    public static Result success(Object data){
        return new Result(200,"操作成功",data);
    }

    public static Result success(int code,String msg,Object data){
        return new Result(code,msg,data);
    }

    public static Result fail(String msg){
        return new Result(400,msg,null);
    }

    public static Result fail(String msg,Object data){
        return new Result(400,msg,data);
    }

    public static Result fail(int code,String msg,Object data){
        return new Result(code,msg,data);
    }
}
