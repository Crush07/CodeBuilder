package com.hysea.hyseaappapi.constant;

public class MybatisConstant {

    public static String[] OPERATE = {
            "select",
            "update",
            "delete",
            "insert"
    };

    public static String SELECT_SQL = "\t\tselect #{column} \n" +
            "\t\tfrom #{tableName} \n" +
            "#{where}";

    public static String UPDATE_SQL = "\t\tupdate #{tableName} \n" +
            "#{set}" +
            "#{where}";

    public static String DELETE_SQL = "\t\tdelete from #{tableName} \n" +
            "#{where}";

    public static String INSERT_SQL = "\t\tinsert into #{tableName} \n" +
            "\t\t(#{column})\n" +
            "\t\tvalues(#{values})";

    public static String UPDATE_SET = "\t\t<trim prefix=\"set\" suffixOverrides=\",\">\n" +
            "#{set}" +
            "\t\t</trim>\n";

    public static String UPDATE_SET_IF = "\t\t\t<if test=\"#{javaName} != null\">\n" +
            "\t\t\t\t#{columnName} = #{#{javaName}},\n" +
            "\t\t\t</if>\n";

    public static String SET_IF = "#{set_if}";


    public static String TABLE_NAME = "#{tableName}";

    public static String COLUMN = "#{column}";

    public static String COLUMN_NAME = "#{columnName}";

    public static String JAVA_NAME = "#{javaName}";

    public static String WHERE = "#{where}";

    public static String VALUES = "#{values}";

    public static String SET = "#{set}";

    public static String WHERE_ID = "\t\twhere id = #{id}";

}
