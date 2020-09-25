package com.lgyun.common.tool;

import java.util.Random;

/**
 * @author .
 * @date 2020/9/24.
 * @time 11:33.
 */
public class RandomStringUtil {

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            String randomString = RandomStringUtil.getRandomString(8);
            System.out.println(randomString);
        }
    }
}
