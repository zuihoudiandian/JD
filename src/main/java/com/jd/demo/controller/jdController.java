package com.jd.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jd.demo.Result.ResultDTO;
import com.jd.demo.exception.CustomizeErrorCode;
import com.jd.demo.mapper.goodsMapper;
import com.jd.demo.mapper.userMapper;
import com.jd.demo.model.goods;
import com.jd.demo.model.user;
import com.jd.demo.service.goodsServiceImpl;
import com.jd.demo.service.userServiceImpl;
import com.jd.demo.utils.jd;
import com.jd.demo.vo.GoodsList;
import com.jd.demo.vo.formVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/18.
 * PS: Not easy to write code, please indicate.
 */
@Controller
public class jdController {

    @Autowired
    userServiceImpl userService;
    @Autowired
    goodsServiceImpl goodsService;
    @Autowired
    goodsMapper goodsMapper;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/query")
    @ResponseBody
    public ResultDTO query(String url) {
        try {
            QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
            QueryWrapper<goods> queryWrapper = goodsQueryWrapper.eq("goods_url", url);
            goods sqlGoods = goodsMapper.selectOne(queryWrapper);
            if (sqlGoods==null){
                goods goods = jd.jdPrice(url, null);
                return ResultDTO.okOf(goods);
            }
            return ResultDTO.okOf(sqlGoods);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_URL);
        }
    }
    @PostMapping("/save")
    @ResponseBody
    public ResultDTO save(@RequestBody formVo formVo) {
        if (userService.saveTask(formVo)){
         return ResultDTO.okOf("保存成功!");
     }
     return ResultDTO.okOf("请求成功!");
    }
    @GetMapping("/getGoods")
    @ResponseBody
    public ResultDTO getGoods(GoodsList goodsList){
        Map<String, Object> result = new HashMap<>();
        Map goodsList1 = goodsService.getGoodsList(goodsList);
        result.put("ROWS",goodsList1.get("list") );
        result.put("TOTAL",goodsList1.get("totalCount"));
        return  ResultDTO.okOf(result);
    }
    @PostMapping("/del")
    @ResponseBody
    public ResultDTO delById(String id){
        if (goodsService.removeById(id)){
            QueryWrapper<user> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("goods_id",id);
            boolean remove = userService.remove(userQueryWrapper);
            if (remove){
                return ResultDTO.okOf("删除成功");
            }
        }
        return ResultDTO.okOf("请求成功!");
    };
}
