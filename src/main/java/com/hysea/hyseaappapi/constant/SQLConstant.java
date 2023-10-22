package com.hysea.hyseaappapi.constant;

public class SQLConstant {

    public static String INSERT =
            "\t<insert id=\"?\" keyProperty=\"?\" useGeneratedKeys=\"true\">\n" +
            "\tinsert into ? (?)\n" +
            "\tvalues\n" +
            "\t(?)\n" +
            "\t</insert>\n\n";

    public static String BATCH_INSERT =
            "\t<insert id=\"?\" keyProperty=\"?\" useGeneratedKeys=\"true\">\n" +
            "\tinsert into ? (?)\n" +
            "\tvalues\n" +
            "\t<foreach collection=\"?\" item=\"?\" index=\"index\" separator=\",\">\n" +
            "\t(?)\n" +
            "\t</foreach>\n" +
            "\t</insert>\n\n";

    public static String UPDATE =
            "\t<update id=\"?\">\n" +
            "\tupdate ?\n" +
            "\t<trim prefix=\"set\" suffixOverrides=\",\">\n" +
            "?\n" +
            "\t</trim>\n" +
            "?\n" +
            "\t</update>\n\n";

    public static String DELETE =
            "\t<delete id=\"?\">\n" +
            "\tdelete from ?\n" +
            "?\n" +
            "\t</delete>\n\n";

    public static String SELECT =
            "\t<select id=\"?\" resultMap=\"?\">\n" +
            "\tselect ? \n" +
            "\tfrom ? \n" +
            "?\n" +
            "\t</select>\n\n";

    public static String SELECT_SQL =
            "\tselect ? \n" +
            "\tfrom ? \n" +
            "?\n";

}
