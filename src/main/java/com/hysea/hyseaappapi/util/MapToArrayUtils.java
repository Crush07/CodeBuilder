package com.hysea.hyseaappapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author hysea
 */
public class MapToArrayUtils {

    public static void main(String[] args) {
        String str = "\n" +
                "        JieZhiCeShuiJiQuSheShiTiaoZhengXiShu_map.put(0,1.0);\n" +
                "        JieZhiCeShuiJiQuSheShiTiaoZhengXiShu_map.put(1,0.7);";
        deal(str);
    }

    private static void deal(String str){
        String caller = "";
        List<String> array = new ArrayList<>();
        int level = 1;

        List<String> stringByRegex = Matchers.getStringByRegex("[^;]*;", str);
        removeSpace(stringByRegex);
        for(int i = 0;i < stringByRegex.size();i++){
            String temp = caller;
            caller = getCaller(stringByRegex.get(i));
            if(caller == null){
                caller = temp;
            }
        }
        Integer sort = 0;

        for(int i = 0;i < stringByRegex.size();i++){
            Integer putContainKey = getPutContainKey(stringByRegex.get(i));
            if(putContainKey == null){
                array.clear();
                break;
            }
            if(Integer.valueOf(0).equals(sort)){
                array.add(getPutContainValue(stringByRegex.get(i)));
                sort = putContainKey;
            }else {
                if(Integer.valueOf((putContainKey - 1)).equals(sort)){
                    array.add(getPutContainValue(stringByRegex.get(i)));
                    sort = putContainKey;
                }else{
                    array.clear();
                    break;
                }
            }
        }

        if(level == 1){
            String temp = "Double[] ? = {?};";
            String s = StringUtils.stringListToString(array, ", ");
            temp = temp.replaceFirst("[?]",caller);
            temp = temp.replaceFirst("[?]",s);
            System.out.println(temp);
        }

    }

    /**
     * 删除行代码前面的空字符串
     * @param list 行代码列表
     */
    private static void removeSpace(List<String> list){
        for(int i = 0;i < list.size();i++){
            list.set(i,list.get(i).replaceAll("^[\n\t ]*",""));
            list.set(i,list.get(i).replaceAll("[\n\t ]*$",""));
        }
    }

    /**
     * 获取调用者
     * @param str 行代码
     * @return 调用者
     */
    private static String getCaller(String str){
        List<String> stringByRegex = Matchers.getStringByRegex("^[^.]*", str);
        if(stringByRegex.size() == 0){
            return null;
        }else{
            return stringByRegex.get(0);
        }
    }

    /**
     * 获取put内容
     * @param str 行代码
     * @return 内容
     */
    private static String getPutContain(String str){
        List<String> stringByRegex = Matchers.getStringByRegex("put[(][^,]*,[^)]*[)]", str);
        if(stringByRegex.size() == 0){
            return null;
        }else{
            return stringByRegex.get(0);
        }
    }

    /**
     * 获取put的key
     * @param str 行代码
     * @return key
     */
    private static Integer getPutContainKey(String str){
        String putContain = getPutContain(str);
        if(putContain != null){
            putContain = putContain.replace("put(","");
            String[] split = putContain.split(",");
            return Integer.parseInt(split[0]);
        }else{
            return null;
        }
    }

    /**
     * 获取put的value
     * @param str 行代码
     * @return value
     */
    private static String getPutContainValue(String str){
        String putContain = getPutContain(str);
        if(putContain != null){
            putContain = putContain.replace(")","");
            String[] split = putContain.split(",");
            return split[1];
        }else{
            return null;
        }
    }

}
