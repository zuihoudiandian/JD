package com.jd.demo.vo;

import lombok.Data;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/22.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class GoodsList {
    private String wxId;
    private Integer pageSize = 5;
    private Integer pageNumber = 1;

}
