package com.cqjtu.bysj.util;

import java.util.Random;

public class GetRandomFileName {

    public GetRandomFileName() {
    }

    //根据随机字符串生成文件名
    public  String getRandomFileName(String fileName, int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        String randomStr = sb.toString();
        StringBuilder sb1 = new StringBuilder();
        int index = fileName.lastIndexOf(".");

        String prefix = fileName.substring(0, index);
        String suffix = fileName.substring(index+1, fileName.length());
        sb1.append(prefix);
        sb1.append("-");
        sb1.append(randomStr);
        sb1.append(".");
        sb1.append(suffix);

        return sb1.toString();
    }

}
