package com.jd.demo.utils;

import com.jd.demo.model.goods;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/3/18.
 * PS: Not easy to write code, please indicate.
 */
public class jd {
    public static goods jdPrice(String goodsUrl,String wx) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    // 使用ConcurrentMap存储cookie信息，因为数据在内存中，所以只在程序运行阶段有效，程序结束后即清空
                    private ConcurrentMap<String, List<Cookie>> storage = new ConcurrentHashMap();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        String host = url.host();
                        if (cookies != null && !cookies.isEmpty()) {
                            storage.put(host, cookies);
                        }
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        String host = url.host();
                        List<Cookie> list = storage.get(host);
                        return list == null ? new ArrayList<Cookie>(): list;
                    }
                })
                .build();
        Request request = new Request.Builder()
                .url("http://p.zwjhl.com/price.aspx?url="+goodsUrl)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36")
                .header("Cookie","lsjgcxToken=6E50054E357C53F305D2DD93260D1BCAE0C6325C3BFED53F237459ADD79BA0A5F60A03DD2FA566909D7723C885068E2681A4BE36CB2B40E84E203BF3DAD6C385")
                .build();
        Response response = client.newCall(request).execute();
        goods goods=new goods();
        if (response.isSuccessful()) {
            StringBuilder stringBuilder = new StringBuilder(response.body().string());
            int i0 = stringBuilder.indexOf("bold;\">");
            int i1 = stringBuilder.indexOf("<div class=\"bigwidth\">");
            String substring1=null;
            if (i0!=-1&&i1!=-1){
                 substring1 = stringBuilder.substring(i0, i1);
            }else {
                if (!wx.isEmpty()){
                    OkHttpClient wxClient = new OkHttpClient();
                    Request wxRequest = new Request.Builder()
                            .url(wx+"?text=cookie拉闸")
                            .build();
                    wxClient.newCall(wxRequest).execute();
                }
                return null;
            }
            String title = substring1.substring(substring1.indexOf(";\">")+5, substring1.indexOf("</")).trim();
            Matcher matcher = compile("<span style=\"margin: 20px;color: #fff;\">最後  </span>").matcher(stringBuilder);
            if (matcher.find()) {
                String substring = stringBuilder.substring(stringBuilder.lastIndexOf("<font class=\"bigwordprice\">"), stringBuilder.lastIndexOf("\n" +
                        "                        </span>"));
                String[] split = substring.split("\\s+");
//            for(String s : split){
//                System.out.println(s);
//            }
                String lastprice = split[2].substring(0, split[2].indexOf("<"));
                String time = split[5].substring(split[5].indexOf("(")+1, split[5].indexOf(")"));
//                System.out.println("最低价格:"+lastprice+"         时间"+time);
//                System.out.println(split[7]);
//                System.out.println("success");
                goods.setMinPrice("最低价格:"+lastprice+"-"+time);
                goods.setNowPrice(split[7]);
                goods.setTitle(title);
            } else {
                if (!wx.isEmpty()){
                    OkHttpClient wxClient = new OkHttpClient();
                    Request wxRequest = new Request.Builder()
                            .url(wx+"?text=cookie拉闸")
                            .build();
                    wxClient.newCall(wxRequest).execute();
                }else {
                    OkHttpClient wxClient = new OkHttpClient();
                    Request wxRequest = new Request.Builder()
                            .url("https://sc.ftqq.com/SCU88647Taeaff406cb1c64f62c7b9a39e1b917f05e660cd73b75a.send"+"?text=cookie拉闸")
                            .build();
                    wxClient.newCall(wxRequest).execute();
                }
            }
        }
        return goods;
    }



}
