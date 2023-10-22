
package com.hysea.hyseaappapi;

import com.hysea.hyseaappapi.constant.DirectoryConstant;
import com.hysea.hyseaappapi.constant.FunctionConstant;
import com.hysea.hyseaappapi.constant.MapperConstant;
import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;
import com.hysea.hyseaappapi.service.TableService;
import com.hysea.hyseaappapi.service.impl.TableServiceImpl;
import com.hysea.hyseaappapi.util.CodingBuilderUtils;
import com.hysea.hyseaappapi.util.CodingStandardUtils;
import com.hysea.hyseaappapi.util.Matchers;
import com.hysea.hyseaappapi.util.TemplateUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectByWhereMain {

    public static void main(String[] args) {

        TableService tableService = new TableServiceImpl();
        boolean isYes = true;
        while (isYes) {
            List<Table> jar = tableService.getAllTableInformation();

            Scanner sc = new Scanner(System.in);


            for (int i = 0; i < jar.size(); i++) {
                System.out.println((i + 1) + " " + jar.get(i).getTableName());
            }
            System.out.println("请输入表对应的数字");
            int tNum = sc.nextInt();

            Table table = jar.get(tNum - 1);

            String tableName = CodingStandardUtils.camelCase(table.getTableName(), true);

            List<Column> columnList = table.getColumnList();

            for (int i = 0; i < columnList.size(); i++) {
                columnList.get(i).setJavaName(CodingStandardUtils.camelCase(columnList.get(i).getColumnName(), false));
                columnList.get(i).setColumnType(CodingStandardUtils.toJavaType(columnList.get(i).getColumnType()));

                System.out.println((i + 1) + " " + columnList.get(i).getColumnName());
            }

            System.out.println("请输入select字段对应的数字','隔开");
            String selectSplit = sc.next();
            String[] selectArray = selectSplit.split(",");
            long selectNum = 0;
            for (int i = 0; i < selectArray.length; i++) {
                selectNum += Math.pow(2, columnList.size() - Long.parseLong(selectArray[i]));
            }

            System.out.println("请输入where字段对应的数字','隔开");
            String whereSplit = sc.next();
            String[] whereArray = whereSplit.split(",");
            long whereNum = 0;
            for (int i = 0; i < whereArray.length; i++) {
                whereNum += Math.pow(2, columnList.size() - Long.parseLong(whereArray[i]));
            }

            //生成mapper
            String path = DirectoryConstant.MAPPER + "/" + tableName + "Mapper.xml";

            String template = "";

            FileInputStream fileInputStream = null;
            try {
                // 1.获取文件指定的文件信息
                fileInputStream = new FileInputStream(path);
                // 2.将数据读到字节数组里
                byte[] buff = new byte[1024 * 1024];
                int length = fileInputStream.read(buff);
                // 3.将字节数据转换为字符串
                // 参数一：带转换的字节数组，参数二：起始位置  参数三：转换的长度
                template = new String(buff, 0, length);
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

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(path));
                template = template.replace("\t<resultMap id=", MapperConstant.CONTENT + "\n\t<resultMap id=");

                System.out.println("是否list?(y/n)");
                String isListStr = sc.next();
                if (isListStr.equals("y")) {
                    template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + CodingBuilderUtils.buildSelectAllBySequence(table, selectNum, whereNum, true));
                } else {
                    template = template.replace(MapperConstant.CONTENT, MapperConstant.CONTENT + CodingBuilderUtils.buildSelectAllBySequence(table, selectNum, whereNum, false));
                }

                template = template.replace(MapperConstant.CONTENT, "");


                out.write(template);
                out.close();
                System.out.println("是否继续?(y/n)");
                String temp = sc.next();
                if (!"y".equals(temp)) {
                    isYes = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("退出");

    }

}

