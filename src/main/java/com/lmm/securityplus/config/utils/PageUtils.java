package com.lmm.securityplus.config.utils;/*
 @author gyh
 @create 2020-12-30 12:14
 */

import java.util.ArrayList;
import java.util.List;

public class PageUtils {
    public static<T> MyPage getPage(List<T> data,Integer current,Integer size){
        Integer total = data.size();

        MyPage<T> tMyPage = new MyPage<>();
        tMyPage.setAllData(data);
        if(current>total/size+1){
            current = 1;
        }
        List<T> currentData = new ArrayList<>();
        Integer num = (current-1)*size+size;
        if(data.size()>0){
            if(total<=size){
                num = total;
            }
            if(total%size != 0 && current == total/size+1){
                num = (current-1)*size+total%size;
            }
            for (int i = (current-1)*size; i < num; i++) {
                currentData.add(data.get(i));
            }
        }
        tMyPage = tMyPage.setTotal(total).setData(currentData);
        return  tMyPage;
    }

}
