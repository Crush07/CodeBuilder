package com.hysea.hyseaappapi.constant;

public class MapperConstant {

    public static String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";

    public static String MAPPER_CONTENT = "<mapper namespace=\"#{daoPackage}.#{className}Mapper\">\n\n"
            + "#{$content$}\n"
            + "</mapper>\n";

    public static String OPERATE_CONTENT = "\t<#{operate} id=\"#{id}\" resultMap=\"#{resultMap}\">\n"
            + "#{sql}\n"
            + "\t</#{operate}>\n\n";


    public static String RESULT_MAP_CONTENT = "\t<resultMap id=\"#{className}\" type=\"#{poPackage}.#{className}\">\n"
            + "#{$content$}"
            + "\t</resultMap>";

    public static String RESULT_MAP_LINE = "\t\t<result column=\"#{dataBaseColumnName}\" property=\"#{columnName}\"/>\n";

    public static String RESULT_MAP = "#{resultMap}";

    public static String SQL = "#{sql}";

    public static String ID = "#{id}";

    public static String CONTENT = "#{$content$}";

    public static String CLASS_NAME = "#{className}";

    public static String PO_PACKAGE = "#{poPackage}";

    public static String DAO_PACKAGE = "#{daoPackage}";

    public static String DATABASE_COLUMN_NAME = "#{dataBaseColumnName}";

    public static String COLUMN_NAME = "#{columnName}";

    public static String OPERATE = "#{operate}";
}
