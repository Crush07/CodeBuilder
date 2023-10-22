
package com.hysea.hyseaappapi;

import com.alibaba.fastjson.JSONArray;
import com.hysea.hyseaappapi.constant.DirectoryConstant;
import com.hysea.hyseaappapi.constant.EntityConstant;
import com.hysea.hyseaappapi.constant.FunctionConstant;
import com.hysea.hyseaappapi.constant.MapperConstant;
import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;
import com.hysea.hyseaappapi.service.TableService;
import com.hysea.hyseaappapi.service.impl.TableServiceImpl;
import com.hysea.hyseaappapi.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class BuilderDaoByMapperMain {

    public static void main(String[] args) {
        builder();
    }

    public static void builder(){

        TableService tableService = new TableServiceImpl();
        List<Table> tableList = tableService.getAllTableInformation();

        String path = DirectoryConstant.MAPPER;
        String[] list = new File(path).list();

        for (int i = 0; i < list.length; i++) {

            String tableName = list[i].replace("Mapper.xml", "");

            String daoPath = DirectoryConstant.DAO + "/" + tableName + "Mapper.java";

            String contain = FileUtils.getFileString(path + "/" + list[i]);

            StringBuilder body = new StringBuilder();

            List<String> stringByRegex = Matchers.getStringByRegex("(select|update|insert|delete) id=\"[^\"]*", contain);


            for (int j = 0; j < stringByRegex.size(); j++) {

                String method = FunctionConstant.METHOD_LINE;

                String id = stringByRegex.get(j).replaceAll(".*\"", "");

                StringBuilder object = new StringBuilder();

                if (Matchers.getStringCountByRegex("^(select|delete)", stringByRegex.get(j)) == 1) {
                    String param = getParamByFunctionNameAndTableName(tableList, id, tableName);
                    if (Matchers.getStringCountByRegex("^selectCount.*", id) == 1) {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, "Integer");
                        method = method.replace(FunctionConstant.OBJECT, param);
                    } else if (Matchers.getStringCountByRegex("^select.*ByPage", id) == 1) {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, "IPage<" + tableName + ">");
                        method = method.replace(FunctionConstant.OBJECT, "IPage<" + tableName + "> page");
                    } else if (Matchers.getStringCountByRegex("^select.*ById", id) == 1) {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, tableName);
                        method = method.replace(FunctionConstant.OBJECT, param);
                    } else if (Matchers.getStringCountByRegex("^select.*", id) == 1) {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, "List<" + tableName + ">");
                        method = method.replace(FunctionConstant.OBJECT, param);
                    } else if (Matchers.getStringCountByRegex("^delete.*By.*", id) == 1) {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, "Integer");
                        method = method.replace(FunctionConstant.OBJECT, param);
                    } else {
                        method = method.replace(FunctionConstant.ID, id);
                        method = method.replace(FunctionConstant.RETURN_VALUE, tableName);
                        method = method.replace(FunctionConstant.OBJECT, param);
                    }
                } else {
                    method = method.replace(FunctionConstant.ID, id);
                    method = method.replace(FunctionConstant.RETURN_VALUE, "Integer");

                    if (Matchers.getStringCountByRegex("^insert.*", id) == 1) {
                        object.append(tableName)
                                .append(" ")
                                .append(CodingStandardUtils.camelCase(tableName, false));
                    } else if (Matchers.getStringCountByRegex("^batchInsert.*", id) == 1) {
                        object.append("@Param(\"")
                                .append(CodingStandardUtils.camelCase(tableName, false))
                                .append("List\") List<")
                                .append(tableName)
                                .append("> ")
                                .append(CodingStandardUtils.camelCase(tableName, false))
                                .append("List");
                    } else if (Matchers.getStringCountByRegex("^update.*By.*", id) == 1) {
                        String where = id.replaceAll(".*By", "");
                        object.append(tableName)
                                .append(" ")
                                .append(CodingStandardUtils.camelCase(tableName, false));
                    }
                    method = method.replace(FunctionConstant.OBJECT, object.toString());
                }


                body.append("\t").append(method);

            }

            String res = FunctionConstant.BODY.replace(FunctionConstant.CONTAIN, body.toString())
                    .replace(FunctionConstant.TABLE_NAME, tableName);

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(daoPath));

                out.write(res);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static String getParamByFunctionNameAndTableName(List<Table> tableList, String functionName, String tableName){

        StringJoiner param = new StringJoiner(", ");
        for(int i = 0;i < tableList.size();i++){
            if(tableName.equals(tableList.get(i).getClassName())){
                String[] ands = functionName.replaceAll("(insert|delete|update|select|Count|All|By|" + tableName + ")", "").split("And");
                for(int j = 0;j < ands.length;j++){
                    for(int k = 0;k < tableList.get(i).getColumnList().size();k++){
                        if(ands[j].equals(tableList.get(i).getColumnList().get(k).getFunctionJavaName())){
                            param.add(tableList.get(i).getColumnList().get(k).getColumnType() + " " + tableList.get(i).getColumnList().get(k).getJavaName());
                        }
                    }
                }
            }
        }


        return param.toString();

    }

}

