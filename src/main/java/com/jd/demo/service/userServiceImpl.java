package com.jd.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.demo.exception.CustomizeErrorCode;
import com.jd.demo.exception.CustomizeException;
import com.jd.demo.mapper.userMapper;
import com.jd.demo.model.goods;
import com.jd.demo.model.user;
import com.jd.demo.utils.jd;
import com.jd.demo.vo.formVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/19.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class userServiceImpl extends ServiceImpl<userMapper, user> {
    @Autowired
    goodsServiceImpl goodsService;
    public boolean saveTask(formVo formVo) {
        if (formVo.getWxId().isEmpty()){
            formVo.setWxId("https://sc.ftqq.com/SCU88647Taeaff406cb1c64f62c7b9a39e1b917f05e660cd73b75a.send");
        }
        goods goods;
        try {
            goods = jd.jdPrice(formVo.getUrl(), formVo.getWxId());
            goods.setGoodsUrl(formVo.getUrl());
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_URL);
        }
        user user = new user();
        if (goodsService.saveGoods(goods)){
            BeanUtils.copyProperties(formVo, user);
            user.setGoodsId(goods.getId());
        }
        return this.save(user);
    };

    public List<user> getUserList(){
        return this.list();
    }
}
