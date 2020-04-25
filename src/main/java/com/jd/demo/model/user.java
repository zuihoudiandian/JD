package com.jd.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/18.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class user {
    //url 价格，期望价格，weID
    @TableId(value = "id" ,type= IdType.AUTO)
    private Integer id;
    private String url;
    private int price;
    private String wxId;
    private Integer goodsId;




}
