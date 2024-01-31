package com.ramble.hikvisionsdkintegration.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Project     hikvision-sdk-integration
 * Package     com.ramble.hikvisionsdkintegration.dto
 * Class       GlobalResponseEntity
 * Date        2023/3/14 11:49
 * Author      MingliangChen
 * Email       cnaylor@163.com
 * Description
 */

@Data
@Accessors(chain = true)
public class GlobalResponseEntity<T> implements Serializable {

    private Boolean success = true;
    private String code = "000000";
    private String message = "request successfully";
    private T data;

    public GlobalResponseEntity() {
        super();
    }

    public GlobalResponseEntity(T data) {
        this.data = data;
    }

    public GlobalResponseEntity(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public GlobalResponseEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public GlobalResponseEntity(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public GlobalResponseEntity(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GlobalResponseEntity<?> badRequest(String code, String message) {
        return new GlobalResponseEntity<>(false, code, message);
    }

    public static GlobalResponseEntity<?> badRequest() {
        return new GlobalResponseEntity<>(false, "404", "无法找到您请求的资源");
    }

    public static GlobalResponseEntity<String> error(String msg) {
        return new GlobalResponseEntity<>(false, "500", msg);
    }

    public static GlobalResponseEntity<String> success(String data) {
        return new GlobalResponseEntity<>(data);
    }

    public static  GlobalResponseEntity<Object> success(Object data) {
        return new GlobalResponseEntity<Object>(data);
    }

}


