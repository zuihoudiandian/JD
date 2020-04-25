package com.jd.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.demo.mapper.goodsMapper;
import com.jd.demo.mapper.userMapper;
import com.jd.demo.model.goods;
import com.jd.demo.model.user;
import com.jd.demo.vo.GoodsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/19.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class goodsServiceImpl extends ServiceImpl<goodsMapper, goods> {
    @Autowired
    goodsMapper goodsMapperd;
    @Autowired
    userMapper userMapper;

    public boolean saveGoods(goods goods){
       return this.save(goods);
    }

    public Map getGoodsList(GoodsList goodsList) {
        if(goodsList.getWxId().isEmpty()){
            goodsList.setWxId("https://sc.ftqq.com/SCU88647Taeaff406cb1c64f62c7b9a39e1b917f05e660cd73b75a.send");
        }
        QueryWrapper<user> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("wx_id",goodsList.getWxId());
        List<user> users = userMapper.selectList(userQueryWrapper);
        Map<String,Object> goodsMap = new HashMap();
        List<goods> queryList = new ArrayList<>();
        for (user user : users) {
            goods byId = this.getById(user.getGoodsId());
            queryList.add(byId);
        }
        List<goods> collect = queryList.stream().skip((goodsList.getPageNumber() - 1) * goodsList.getPageSize()).limit(goodsList.getPageSize()).collect(Collectors.toList());
        goodsMap.put("list",collect);
        goodsMap.put("totalCount", queryList.size());
        return goodsMap;
    }

}
