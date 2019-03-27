package com.cqjtu.bysj.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//工具类，获取request里面的json数据，返回map,key为参数名，value为参数值
public  class JsonParser {

    private JsonParser() {

    }

    public static Map<String, String> jsonDataParser(HttpServletRequest request) {
        String params= null;
        try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            if (jsonObject != null) {
                params = jsonObject.toJSONString();
            } else {
                params = "params_is_null";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        if (Objects.equals(params, "params_is_null")) {
            map.put("paramName", "paramValue");
        } else {

        int first = StringUtils.ordinalIndexOf(params, "{", 2);
        String param2 = params.substring(first + 1, params.length()-2);
        String[] arr = param2.split(",");
        for (String str : arr) {
            map.put(str.substring(StringUtils.ordinalIndexOf(str, "\"", 1) + 1, StringUtils.ordinalIndexOf(str, "\"", 2)),
                    str.substring(StringUtils.ordinalIndexOf(str, "\"", 3) + 1, StringUtils.ordinalIndexOf(str, "\"", 4)));
        }
        }

        return map;

    }
}
