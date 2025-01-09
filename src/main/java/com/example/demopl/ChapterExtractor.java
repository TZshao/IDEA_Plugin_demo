package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.Messages;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ChapterExtractor {

    public static Map<String, Integer> extractChapters(String regex) {
        Map<String, Integer> result = new LinkedHashMap<>(); // 行号 -> 匹配文本

        RandomAccessFile file = FileSelector.getFile();
        if (file == null) Messages.showInfoMessage("File 先选择文件", "ERROR");

        String filePath = PropertiesComponent.getInstance().getValue("reader_selectedFilePath");
        if (filePath == null) return result;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // 编译正则表达式
            Pattern pattern = Pattern.compile(regex);
            // 按行读取文件
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                Matcher matcher = pattern.matcher(line);

                // 检查是否匹配
                if (matcher.find()) {
                    result.put(line, lineNumber); // 记录行号和匹配内容
                }
            }
        } catch (IOException e) {
            Messages.showInfoMessage("File 章节解释失败", "ERROR");
        }

        return result;
    }
}
