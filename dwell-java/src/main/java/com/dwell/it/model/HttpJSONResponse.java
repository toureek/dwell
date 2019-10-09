package com.dwell.it.model;

public class HttpJSONResponse {
    /**
     * API基本返回结构如下：
     * code = 0 请求返回正常；
     * -1 后端处理的纯错误提示语句异常 --> 只用去关注message
     * 400 后段处理的错误信息返回  -->  返回数据 还有data信息
     * 500 后端处理的异常信息返回  -->  只用去关注message
     * <p>
     * {
     * code    : 0,
     * message : "xxxx",
     * data    : [object1,
     * object2,
     * object3,
     * ...]
     * }
     */

    private Integer code;  // 业务状态码

    private String message;  // 返回消息

    private Object data;  // 返回的数据集合

    // Constructors
    public HttpJSONResponse() {
    }

    public HttpJSONResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpJSONResponse(Object data) {
        this.data = data;
        this.code = 0;
        this.message = "success";
    }

    public static HttpJSONResponse build(Integer code, String msg, Object data) {
        return new HttpJSONResponse(code, msg, data);
    }

    public static HttpJSONResponse ok(Object data) {
        return new HttpJSONResponse(data);
    }

    public static HttpJSONResponse ok() {
        return new HttpJSONResponse(null);
    }

    public static HttpJSONResponse successMessage(String successMsg) {
        return new HttpJSONResponse(0, successMsg, null);
    }

    public static HttpJSONResponse errorMessage(String errorMsg) {
        return new HttpJSONResponse(-1, errorMsg, null);
    }

    public static HttpJSONResponse errorResponseData(Object data) {
        return new HttpJSONResponse(400, "error", data);
    }

    public static HttpJSONResponse errorException(String errorMsg) {
        return new HttpJSONResponse(500, errorMsg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpJSONResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}