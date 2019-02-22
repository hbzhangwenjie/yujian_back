package com.yingzi.guanguai.dto;

import lombok.Data;

import java.util.List;
@Data
public class BaseResponse<T> {
    T data;
    List<T> datas;
    String result;
}
