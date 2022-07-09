package com.atguigu.ggkt.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result() {
    }

    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = new Result<T>();
        if (body != null) {
            result.setData(body);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 操作成功
     *
     * @param data baseCategory1List
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        return build(data, 200, "成功");
    }

    public static <T> Result<T> ok(T data, String message) {
        return build(data, 200, message);
    }

    public static <T> Result<T> ok(String message) {
        return build(null, 200, message);
    }


    public static <T> Result<T> fail(T data) {
        return build(data, 201, "失败");
    }

    public static <T> Result<T> fail(T data, String message) {
        return build(data, 201, message);
    }

    public static <T> Result<T> fail(String message) {
        return build(null, 201, message);
    }


    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}