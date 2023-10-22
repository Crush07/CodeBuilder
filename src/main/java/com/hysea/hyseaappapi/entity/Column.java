package com.hysea.hyseaappapi.entity;

import lombok.Data;

@Data
public class Column {

    private String columnName;

    private String javaName;

    private String mybatisName;

    private String quoteMybatisName;

    private String insertName;

    private String functionJavaName;

    private String columnType;

    private String dataType;

    private String characterMaximumLength;

    private String isNullable;

    private String columnDefault;

    private String columnComment;

    private Boolean isPrimaryKey;

}
