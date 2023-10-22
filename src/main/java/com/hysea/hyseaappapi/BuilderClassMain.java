package com.hysea.hyseaappapi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hysea.hyseaappapi.constant.EntityConstant;
import com.hysea.hyseaappapi.util.CodingStandardUtils;
import com.hysea.hyseaappapi.util.FileUtils;
import com.hysea.hyseaappapi.util.FormatUtils;
import com.hysea.hyseaappapi.util.SqlGeneratorUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuilderClassMain {

    public static String poEntityPrefix;
    public static JSONArray buildClassAliasMap;

    public static void main(String[] args) {

        JSONObject buildCodeConfigObject = FileUtils.getJSONObject("src/main/resources/buildCode.json");
        String jsonInputPath = buildCodeConfigObject.getString("jsonInputPath");
        String classOutputPoPath = buildCodeConfigObject.getString("classOutputPoPath");
        poEntityPrefix = buildCodeConfigObject.getString("poEntityPrefix");
        String classOutputPoPackage = buildCodeConfigObject.getString("classOutputPoPackage");
        buildClassAliasMap = buildCodeConfigObject.getJSONArray("buildClassAliasMap");
        String[] list = new File(jsonInputPath).list();

        for (int i = 0; i < list.length; i++) {

            String beanName = CodingStandardUtils.camelCase(poEntityPrefix,true) +
                    CodingStandardUtils.camelCase(list[i].replaceAll("[.][a-zA-Z0-9]+", ""),true);
            try {

                List<String> nameList = new ArrayList<>();
                nameList.add(beanName);

                List<StringBuilder> sbList = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                sbList.add(sb);

                String string = FileUtils.getFileString(jsonInputPath + "/" + list[i]);

                function(string,false,nameList,beanName,beanName,sbList,sb,0);

                for(int j = 0;j < sbList.size();j++){
                    BufferedWriter out = new BufferedWriter(new FileWriter(classOutputPoPath + "/" + nameList.get(j) + ".java"));

                    String template = EntityConstant.CLASS_CONTENT.replace(EntityConstant.PACKAGE,classOutputPoPackage);

                    template = template.replace(EntityConstant.CLASS_NAME,nameList.get(j))
                            .replace(EntityConstant.CONTENT,sbList.get(j).toString());

                    out.write(FormatUtils.format(template));
                    out.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SqlGeneratorUtils.buildSqlByClass();

    }

    public static void function(String string, boolean isArray,List<String> nameList, String name, String parentName, List<StringBuilder> sbList, StringBuilder sb, int th) throws Exception{

        name = CodingStandardUtils.camelCase(name,false);
        if(!isArray){
            JSONObject jsonObject = JSONObject.parseObject(string);
            if(jsonObject.isEmpty()){
                return;
            }
            jsonObject.put("id","id");
            jsonObject.put("createTime",1);
            jsonObject.put("updateTime",1);

            Set<String> keys = jsonObject.keySet();
            String line = "\n\tprivate ? ?;\n";
            for(String key : keys){
                Object o = jsonObject.get(key);
                if(o.getClass().getName().equals(String.class.getName())){
                    sb.append(line.replaceFirst("[?]","String").replaceFirst("[?]",key));
                }else if(o.getClass().getName().equals(Integer.class.getName())){
                    if(key.contains("Time")){
                        sb.append(line.replaceFirst("[?]","Long").replaceFirst("[?]",key));
                    }else{
                        sb.append(line.replaceFirst("[?]","Integer").replaceFirst("[?]",key));
                    }
                }else if(o.getClass().getName().equals(BigDecimal.class.getName())){
                    sb.append(line.replaceFirst("[?]","Double").replaceFirst("[?]",key));
                }else if(o.getClass().getName().equals(Boolean.class.getName())){
                    sb.append(line.replaceFirst("[?]","Boolean").replaceFirst("[?]",key));
                }else if(o.getClass().getName().equals(JSONArray.class.getName())){
                    function(o.toString(),true,nameList,key,name,sbList,sb,th+1);
                }else if(o.getClass().getName().equals(JSONObject.class.getName())){

                    String newName = CodingStandardUtils.camelCase(poEntityPrefix,true) +
                            CodingStandardUtils.camelCase(key,true);
                    nameList.add(newName);
                    StringBuilder newSb = new StringBuilder();
                    sbList.add(newSb);
                    function(o.toString(),false,nameList,newName,name,sbList,newSb,th);

                    sb.append(line.replaceFirst("[?]", "String").replaceFirst("[?]",key+"Id"));
                }
            }
        }else{
            JSONArray jsonArray = JSONArray.parseArray(string);
            if(jsonArray.isEmpty()){
                return;
            }
            String line = "\n\tprivate ? ?;\n";
            for(int i = 0;i < jsonArray.size();i++){
                if(jsonArray.get(i).getClass().getName().equals(String.class.getName()) ||
                        jsonArray.get(i).getClass().getName().equals(Integer.class.getName()) ||
                        jsonArray.get(i).getClass().getName().equals(BigDecimal.class.getName()) ||
                        jsonArray.get(i).getClass().getName().equals(Boolean.class.getName())){
                    sb.append(line.replaceFirst("[?]","String").replaceFirst("[?]",name+"JSONString"));
                }else if(jsonArray.get(i).getClass().getName().equals(JSONArray.class.getName())){

                    function(jsonArray.get(i).toString(),true,nameList,name,parentName,sbList,sb,th+1);

                }else if(jsonArray.get(i).getClass().getName().equals(JSONObject.class.getName())){

                    String newName = CodingStandardUtils.camelCase(poEntityPrefix,true) +
                            CodingStandardUtils.camelCase(name,true);
                    nameList.add(newName);
                    StringBuilder newSb = new StringBuilder();
                    sbList.add(newSb);
                    function(jsonArray.getString(i),false,nameList,newName,parentName,sbList,newSb,th);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(CodingStandardUtils.camelCase(parentName.replaceAll("^"+poEntityPrefix,""),false)+"Id","id");
                    jsonObject.put(CodingStandardUtils.camelCase(name.replaceAll("^"+poEntityPrefix,""),false)+"Id","id");
                    String newName1 = "";

                    int j;
                    for(j = 0;j < buildClassAliasMap.size();j++){
                        if(buildClassAliasMap.getJSONObject(j).getString("entity1").equals(CodingStandardUtils.camelCase(parentName.replaceAll("^"+poEntityPrefix,""),false)) &&
                                buildClassAliasMap.getJSONObject(j).getString("entity2").equals(CodingStandardUtils.camelCase(name,false))){
                            newName1 = CodingStandardUtils.camelCase(poEntityPrefix,true) +
                                    CodingStandardUtils.camelCase(buildClassAliasMap.getJSONObject(j).getString("alias"),true);
                            break;
                        }
                    }
                    if(j == buildClassAliasMap.size()){
                        newName1 = CodingStandardUtils.camelCase(poEntityPrefix,true) +
                                CodingStandardUtils.camelCase(parentName.replaceAll("^"+poEntityPrefix,""),true) +
                                CodingStandardUtils.camelCase(name,true);
                    }
                    nameList.add(newName1);
                    newSb = new StringBuilder();
                    sbList.add(newSb);
                    function(JSONObject.toJSONString(jsonObject),false,nameList,newName1,parentName,sbList,newSb,th);

                }
                break;
            }
        }
    }


}
