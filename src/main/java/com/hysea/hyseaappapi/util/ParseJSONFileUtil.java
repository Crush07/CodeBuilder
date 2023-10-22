package com.hysea.hyseaappapi.util;

import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

public class ParseJSONFileUtil {

    public static JSONObject getObject(String filePath){
        String contain = "";
        FileInputStream fileInputStream = null;
        try {
            // 1.获取文件指定的文件信息
            fileInputStream = new FileInputStream(filePath);
            // 2.将数据读到字节数组里
            byte[] buff = new byte[1024 * 1024];
            int length = fileInputStream.read(buff);
            // 3.将字节数据转换为字符串
            // 参数一：带转换的字节数组，参数二：起始位置  参数三：转换的长度
            contain = new String(buff, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4，关闭流操作
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSONObject.parseObject(contain);
    }

}
