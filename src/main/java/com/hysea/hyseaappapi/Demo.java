package com.hysea.hyseaappapi;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.hysea.hyseaappapi.util.DiffHandleUtils;
import com.hysea.hyseaappapi.util.FileCoypUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Demo {

    private static int sum;

    public static void main(String[] args) throws Exception{

        //对比 F:\n1.txt和 F:\n2.txt 两个文件，获得不同点
        List<String> diffString = DiffHandleUtils.diffString("D:\\study\\java_project\\buildCode\\project\\entity\\HyBlog.java","D:\\study\\java_project\\buildCode\\project\\entity\\HyBlog1.java");

        //在 D:\diff\ 目录下生成一个 diff.html 文件，打开便可看到两个文件的对比
        String droducDirPath = "D:\\diff.html";
        DiffHandleUtils.generateDiffHtml(diffString,droducDirPath);

        //把所需的 js和 css 从 resource 资源目录复制到 droducDirPath 目录下
        FileCoypUtils.copyfile(droducDirPath);

        System.out.println("对比完成，请打开 "+droducDirPath+"diff.html 查看");


    }
}
