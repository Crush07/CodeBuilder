package com.hysea.hyseaappapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matchers {


    public static List<MatcherIndex> getIndexByRegex(String regex, String str){
        List<MatcherIndex> res = new ArrayList<>();
        Matcher slashMatcher = Pattern.compile(regex).matcher(str);
        while (slashMatcher.find()) {
            try {
                int start = slashMatcher.start();
                int end = slashMatcher.end();
                MatcherIndex matcherIndex = new MatcherIndex(start,end);
                res.add(matcherIndex);
            } catch (Exception e) {
            }
        }
        return res;
    }
    public static int getStringCountByRegex(String regex, String str){
        int res = 0;
        Matcher slashMatcher = Pattern.compile(regex).matcher(str);
        while (slashMatcher.find()) {
            try {
                int start = slashMatcher.start();
                int end = slashMatcher.end();
                MatcherIndex matcherIndex = new MatcherIndex(start,end);
                res++;
            } catch (Exception e) {
            }
        }
        return res;
    }
    public static List<Integer> getBeginIndexByRegex(String regex, String str){
        List<Integer> res = new ArrayList<>();
        Matcher slashMatcher = Pattern.compile(regex).matcher(str);
        while (slashMatcher.find()) {
            try {
                int start = slashMatcher.start();
                res.add(start);
            } catch (Exception e) {
            }
        }
        return res;
    }
    public static List<String> getStringByRegex(String regex, String str){
        List<String> res = new ArrayList<>();
        Matcher slashMatcher = Pattern.compile(regex).matcher(str);
        while (slashMatcher.find()) {
            try {
                res.add(str.substring(slashMatcher.start(),slashMatcher.end()));
            } catch (Exception e) {
            }
        }
        return res;
    }
    public static List<MatcherIndex> getSymbolScopeByRegex(String str1){
        String str = str1;
        List<MatcherIndex> mathLimit = Matchers.getIndexByRegex("[（(\\[][^,，]{1,5}[,，][^,，]{1,5}[)）\\]]", str);
        int i;
        StringBuilder sb = new StringBuilder(str);
        for(i = 0;i < mathLimit.size();i++){
            sb.setCharAt(mathLimit.get(i).getStart(),'(');
            sb.setCharAt(mathLimit.get(i).getEnd()-1,')');
        }
        str = sb.toString();

        List<MatcherIndex> symbolByRegex = new ArrayList<>();

        List<MatcherIndex> t = Matchers.getIndexByRegex("[\"][^\"]+[\"]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[“][^”]+[”]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[{][^}]+[}]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[\\[][^]]+[\\]]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[【][^】]+[】]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[(][^)]+[)]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[（][^）]+[）]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[<][^>]+[>]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("[《][^》]+[》]", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }
        t = Matchers.getIndexByRegex("<[  ]*[tT][bB][oO][dD][yY][  ]*>((?!<[  ]*/[  ]*[tT][bB][oO][dD][yY][  ]*>).)*<[  ]*/[  ]*[tT][bB][oO][dD][yY][  ]*>", str);
        for(i = 0;i < t.size();i++){
            symbolByRegex.add(t.get(i));
        }

        return symbolByRegex;//存在问题：出现“xx“xxxx”xxx”会出错只能找到“xx“xxxx”
    }
}
