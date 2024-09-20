package com.zoomdev.personalblog;

// 泛类型定义， T是类型参数，可以代表任何类型
// 这允许Response类能够处理不同类型的数据
public class Response<T> {
    // T在这里被用作字段类型，意味着data可以是任何类型
    private T data;
    private boolean success;
    private String errorMsg;

    // 这是一个静态方法，它引入了自己的类型参数K
    // 注意K与类型中的T没有直接关系，是互相独立的
    // static方法不能直接使用类的类型参数T,所以这里约定了新的类型参数
    public static <K> Response<K> newSuccess(K data) {
        // 创建一个新的Response对象
        // <>是钻石操作符，它允许编译器推断泛型类
        // 在这里编译器会推断出Response<K>
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static Response<Void> newFail(String errorMsg) {
        Response<Void> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
