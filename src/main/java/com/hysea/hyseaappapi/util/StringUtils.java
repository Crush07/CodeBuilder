package com.hysea.hyseaappapi.util;

import java.util.List;
import java.util.StringJoiner;

public class StringUtils {

    /**
     * 删除长串里的子串（增强版）
     * 删除由一个特定的分隔符分隔的排列的字符串其中一个元素
     * eg:
     * source : 123;321;465
     * sub: 123
     * delimiter : ;
     * return 321;456
     * @param source 长字符串
     * @param sub 待删除子串
     * @param delimiter 分割符或分割字符串
     * @return 如果删除成功返回被删除的字符串，如果删除失败返回null
     *
     * 注意：传参不要传空值
     */
    public static String deleteStr(String source,String sub,String delimiter){
        if(source.contains(sub)){
            return source.replace(sub,"").replaceAll("("+delimiter+")+",delimiter).replaceAll("^"+delimiter,"");
        }else{
            return null;
        }
    }

    /**
     * 获得由分隔符分隔的元素个数
     * eg:
     * source : 123;123;123;45
     * delimiter : ;
     * return : 4
     * @param source 长字符串
     * @param delimiter 分隔符
     * @return 元素个数
     */
    public static int elementCount(String source,String delimiter){

        if("".equals(source)){
            return 0;
        }

        int stringCountByRegex = Matchers.getStringCountByRegex(delimiter, source.replaceAll(delimiter + "$", "").replaceAll("^" + delimiter,""));

        return stringCountByRegex + 1;
    }

    /**
     * 获得由分隔符分隔的元素个数
     * eg:
     * source : 123;123;123;45
     * delimiter : ;
     * return : 4
     * @param source 长字符串
     * @param delimiter 分隔符
     * @return 元素个数
     */
    public static String[] element(String source,String delimiter){

        if("".equals(source)){
            return new String[]{};
        }

        List<String> stringByRegex = Matchers.getStringByRegex(delimiter, source.replaceAll(delimiter + "$", "").replaceAll("^" + delimiter, ""));

        return (String[])stringByRegex.toArray();
    }

    /**
     * 将整型list数组转换为字符串以便存入数据库
     * @param lists 整型数组
     * @return 由delimiter分隔的字符串
     */
    public static String integerListToString(List<Integer> lists,String delimiter){
        StringJoiner result = new StringJoiner(delimiter);
        for (Integer list : lists) {
            result.add(list.toString());
        }
        return result.toString();
    }

    /**
     * 将字符串list数组转换为字符串以便存入数据库
     * @param lists 字符串数组
     * @return 由delimiter分隔的字符串
     */
    public static String stringListToString(List<String> lists,String delimiter){
        StringJoiner result = new StringJoiner(delimiter);
        for (String list : lists) {
            result.add(list);
        }
        return result.toString();
    }

}
