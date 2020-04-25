package com.jd.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/18.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class goods {
    @TableId(value = "id" ,type= IdType.AUTO)
    private Integer id;
    private String minPrice;
    private String nowPrice;
    private String title;
    private String goodsUrl;
}
