package com.example.demopl.action;

import com.example.demopl.core.Config;
import com.example.demopl.util.Util;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ChapterExtractor extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PropertiesComponent properties = PropertiesComponent.getInstance();
        String rgeText = JOptionPane.showInputDialog("使用正则解析章节:",properties.getValue("reader_chapterRge"));
        if (Util.isEmpty(rgeText)) return;
        properties.setValue("reader_chapterRge", rgeText);
        Map<String, Integer> map = extractChapters(rgeText);
        if (map.isEmpty()) {
            Messages.showInfoMessage("Fail 未识别到有效章节", "失败");
            return;
        }
        Config.chapters = map;
        Messages.showInfoMessage("找到章节，共：" + map.size(), "成功");

    }

    public static Map<String, Integer> extractChapters(String regex) {
        Map<String, Integer> result = new LinkedHashMap<>(); // 行号 -> 匹配文本

        BufferedReader file = FileSelector.getFileReader();
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
            Messages.showInfoMessage("File 章节解析失败", "ERROR");
        }

        return result;
    }


}
