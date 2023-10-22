package com.hysea.hyseaappapi.util;

import java.util.*;

public class FormatUtils {

    public static String format(String string){
        string = string.replace("\t","");

        int tab = 0;
        for(int i = 0;i < string.length();i++){
            if(string.charAt(i) == '{'){
                tab++;
            }
            if(string.charAt(i) == '}'){
                tab--;
            }
            if(string.charAt(i) == '\n'){
                StringBuilder tabStr = new StringBuilder();
                if(i+1 < string.length() && string.charAt(i+1) == '}'){
                    for(int j = 0;j < tab - 1;j++){
                        tabStr.append("\t");
                    }
                }else{
                    for(int j = 0;j < tab;j++){
                        tabStr.append("\t");
                    }
                }
                string = string.substring(0,i+1) + tabStr + string.substring(i+1);
            }
        }

        return string;

    }

    public static String formatXML(String string){

        List<String> stringByRegex = Matchers.getStringByRegex("<[^ >!?]+", string);
        HashMap<String,Integer> tagMap = new HashMap<>();
        List<Integer> tagTabCount = new ArrayList<>();
        List<Boolean> isCloseTag = new ArrayList<>();
        for(int i = 0;i < stringByRegex.size();i++){
            String tag = stringByRegex.get(i).replace("/", "").replace("<", "");
            if(tagMap.containsKey(tag)){
                tagTabCount.add(tagMap.get(tag));
                isCloseTag.add(true);
                tagMap.remove(tag);
            }else{
                tagTabCount.add(tagMap.size());
                isCloseTag.add(false);
                tagMap.put(tag,tagMap.size());
            }
        }

        List<Integer> tabCount = new ArrayList<>();
        List<String> line = Matchers.getStringByRegex("\n[^\n]*", string);
        boolean isContain = false;
        for(int i = 0,j = 0;i < line.size();i++){
            if(j < stringByRegex.size() && line.get(i).contains(stringByRegex.get(j))){
                isContain = true;
                tabCount.add(tagTabCount.get(j));
                j++;
            }else{
                if(!isContain || j >= stringByRegex.size() || j == 0){
                    tabCount.add(0);
                }else{
                    if(!isCloseTag.get(j-1)){
                        tabCount.add(tagTabCount.get(j-1)+1);
                    }else{
                        tabCount.add(tagTabCount.get(j-1));
                    }
                }
            }
        }

        string = string.replace("\t","").replace("\n","#@#");

        for(int i = 0;i < tabCount.size();i++){
            StringBuilder s = new StringBuilder("\n");
            for(int j = 0;j < tabCount.get(i);j++){
                s.append("\t");
            }
            string = string.replaceFirst("#@#",s.toString());
        }
        return string;
    }


}
