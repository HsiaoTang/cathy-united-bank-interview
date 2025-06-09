package com.cub.interview.cathyunitedbankinterview.bean.dto.response;

import lombok.Data;

@Data
public class DefaultResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> DefaultResponse<T> success(T data) {
        DefaultResponse<T> res = new DefaultResponse<>();
        res.setSuccess(true);
        res.setMessage("OK");
        res.setData(data);
        return res;
    }

    public static <T> DefaultResponse<T> fail(String message) {
        DefaultResponse<T> res = new DefaultResponse<>();
        res.setSuccess(false);
        res.setMessage(message);
        res.setData(null);
        return res;
    }
}
