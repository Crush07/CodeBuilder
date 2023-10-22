package com.hysea.hyseaappapi.util;

import java.util.StringJoiner;

/**
 * @author hysea
 * 拼接union sql
 */
public class BuildUnionSqlUtils {

    public static void main(String[] args) {
        String str1 = "161479\n" +
                "161481\n" +
                "161483\n" +
                "161489\n" +
                "161491\n" +
                "161493\n" +
                "169585\n" +
                "169586\n" +
                "169587";
        String[] list1 = str1.split("\n");

        String str2 = "197\n" +
                "139\n" +
                "139\n" +
                "111\n" +
                "118\n" +
                "130\n" +
                "188\n" +
                "188\n" +
                "198";
        String[] list2 = str2.split("\n");

        String sql = "(select atc.* from ae_chapter_topic act\n" +
                "right join ae_topic atc on atc.id = act.topic_id\n" +
                "where chapter_id = ?\n" +
                "and atc.quality = 0\n" +
                "and atc.topic_type not like '%选%'\n" +
                "limit 0,?\n" +
                ")";
        StringJoiner stringJoiner = new StringJoiner("\nunion\n");
        for(int i = 0;i < list1.length;i++){
            stringJoiner.add(sql.replaceFirst("\\?",list1[i]).replaceFirst("\\?",list2[i]));
        }
    }

}
