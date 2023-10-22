package com.hysea.hyseaappapi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

public class BuilderCodeMain {

    public static void main(String[] args) {

        TableService tableService = new TableServiceImpl();
        List<Table> tableList = tableService.getAllTableInformation();
        JSONObject buildCodeConfigObject = FileUtils.getJSONObject("src/main/resources/buildCode.json");
        String poPackage = buildCodeConfigObject.getString("poPackage");
        String daoPackage = buildCodeConfigObject.getString("daoPackage");

        for (int i = 0; i < tableList.size(); i++) {

            Table table = tableList.get(i);

            String tableName = CodingStandardUtils.camelCase(table.getTableName(), true);

            List<Column> columnList = table.getColumnList();

            //生成entity
            String path = DirectoryConstant.ENTITY + "/" + tableName + ".java";

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(path));
                StringBuilder content = new StringBuilder();
                String template = EntityConstant.CLASS_CONTENT.replace(EntityConstant.CLASS_NAME, tableName).replace(EntityConstant.PACKAGE,poPackage);

                for (Column column : columnList) {

                    content.append(EntityConstant.NOTES_LINE.replace(EntityConstant.NOTE, column.getColumnComment().replace("\n", "\n\t * ")))
                            .append("\n")
                            .append(EntityConstant.ATTRIBUTE_LINE.replace(EntityConstant.TYPE, column.getColumnType()).replace(EntityConstant.ATTRIBUTE_NAME, column.getJavaName()))
                            .append("\n\n");

                }

                template = template.replace(EntityConstant.CONTENT, content.toString());

                out.write(template);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //生成mapper
            path = DirectoryConstant.MAPPER + "/" + tableName + "Mapper.xml";

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(path));
                String template = MapperConstant.MAPPER_CONTENT.replace(MapperConstant.CLASS_NAME, tableName).replace(MapperConstant.DAO_PACKAGE,daoPackage);

                String resultMap = MapperConstant.RESULT_MAP_CONTENT.replace(MapperConstant.CLASS_NAME, tableName).replace(MapperConstant.PO_PACKAGE,poPackage);
                StringBuilder resultMapContent = new StringBuilder();
                for (Column column : columnList) {
                    resultMapContent.append(MapperConstant.RESULT_MAP_LINE.replace(MapperConstant.DATABASE_COLUMN_NAME, column.getColumnName()).replace(MapperConstant.COLUMN_NAME, column.getJavaName()));
                }
                resultMap = resultMap.replace(MapperConstant.CONTENT, resultMapContent.toString());
                template = MapperConstant.HEAD + template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + "\n" + resultMap);

                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildSelect(i, new ArrayList<>(), new ArrayList<>()));
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildInsert(i));
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildBatchInsert(i));
                ArrayList<Integer> whereIndex = new ArrayList<>();

                //byId
                whereIndex.add(0);
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildUpdate(i, whereIndex));
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildDelete(i, whereIndex));
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildSelect(i, new ArrayList<>(), whereIndex));
                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildSelectCount(i, whereIndex));
//                template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + TemplateUtils.buildSelectByPage(i, new ArrayList<>(), new ArrayList<>()));

                String str = "[" +
                        " {" +
                        "     \"table\":\"xx\"," +
                        "     \"select\":[" +
                        "         \"xx\"," +
                        "         \"xx\"" +
                        "     ]," +
                        "     \"=\":[" +
                        "         \"xx\"" +
                        "     ]" +
                        " }," +
                        " {" +
                        "     \"table\":\"xx\"," +
                        "     \"select\":[" +
                        "         \"xx\"," +
                        "         \"xx\"" +
                        "     ]," +
                        "     \"=\":[" +
                        "         \"xx\"" +
                        "     ]" +
                        " }" +
                        "]";
                JSONArray param = JSONArray.parseArray(str);
//                template = template.replace(MapperConstant.CONTENT,MapperConstant.CONTENT + TemplateUtils.buildComplexSelect(param));

                template = template.replace(MapperConstant.CONTENT, "");

                template = FormatUtils.formatXML(template);

                out.write(template);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        BuilderDaoByMapperMain.builder();

        System.out.println("生成完毕");
    }
}

