package com.jd.demo.task;

import com.jd.demo.model.goods;
import com.jd.demo.model.user;
import com.jd.demo.service.goodsServiceImpl;
import com.jd.demo.service.userServiceImpl;
import com.jd.demo.utils.jd;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/19.
 * PS: Not easy to write code, please indicate.
 */
@Component
public class DailyTasks {
    @Autowired
    userServiceImpl userService;
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void queryTask() throws Exception {
        List<user> userList = userService.getUserList();
        // 根据URL去重
        ArrayList<user> UserCollect = userList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(user::getUrl))), ArrayList::new)
        );
        for (user user : UserCollect) {
            goods goods = jd.jdPrice(user.getUrl(), user.getWxId());
            if (!goods.getNowPrice().isEmpty()){
                String[] split = goods.getNowPrice().split("：");
                String[] split1 = split[1].split("\\.");
                Integer nowPrice = Integer.parseInt(split1[0]);
                if (user.getPrice()>=nowPrice){
                    //通知微信
                    OkHttpClient wxClient = new OkHttpClient();
                    Request wxRequest = new Request.Builder()
                            .url(user.getWxId()+"?text=速度去买,"+goods.getTitle()+"已经降价为"+nowPrice+"元")
                            .build();
                    wxClient.newCall(wxRequest).execute();
                }
            }

        }
    }

}
