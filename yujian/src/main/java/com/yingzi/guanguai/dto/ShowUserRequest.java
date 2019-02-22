package com.yingzi.guanguai.dto;

import lombok.Data;

@Data
public class ShowUserRequest extends BaseRequest{
    private int pageNo;
    private Byte gender;
}
