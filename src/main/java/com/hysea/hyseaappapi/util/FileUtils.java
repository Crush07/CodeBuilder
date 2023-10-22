package com.hysea.hyseaappapi.util;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class FileUtils {

    public static String getFileString(String path){
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            // size  为字串的长度 ，这里一次性读完

            int size=fileInputStream.available();

            byte[] buffer=new byte[size];

            fileInputStream.read(buffer);

            fileInputStream.close();

            return new String(buffer,"utf-8");
        }catch (Exception e){
            return "";
        }
    }

    public static JSONObject getJSONObject(String path){
        return JSONObject.parseObject(getFileString(path));
    }

}
