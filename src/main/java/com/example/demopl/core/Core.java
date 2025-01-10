package com.example.demopl.core;

import com.example.demopl.util.Util;
import com.example.demopl.action.FileSelector;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shaoyh
 * @version V1.0
 */
public class Core {

    /**
     * 替换Mark区内容
     */
    public static boolean replace(Editor editor, Project project, String content, boolean isCheck) {
        Document document = editor.getDocument();
        CharSequence text = document.getCharsSequence();
        String startMarker = Config.startMark;
        String endMarker = Config.endMark;

        // 使用正则表达式查找标识符之间的内容
        String regex = Pattern.quote(startMarker) + "(.*?)" + Pattern.quote(endMarker);
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            int replaceStart = matcher.start(1); // 获取内容开始位置
            int replaceEnd = matcher.end(1);     // 获取内容结束位置
            // 替换内容
            if (!isCheck) {
                com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(replaceStart, replaceEnd, "\n" + content));
            }
        } else {
            Util.showNotify(project, "找不到标记", "阅读标记:\n @DocReader 与 @--- ", NotificationType.INFORMATION);
            return false;
        }
        return true;
    }

    //定位行
    public static void locateLine(BufferedReader file) {
        if (null == file) return;
        try {
            file.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long lineNum = 1;
        try {
            while (lineNum <= Config.currentLine && file.readLine() != null) {
                lineNum++;
            }
            System.out.println("locateLine:" + lineNum);
        } catch (Exception ex) {
            Messages.showMessageDialog("IO异常!", "Error", Messages.getErrorIcon());
        }
    }
    //翻页行为
    public static void moveLine(Editor editor, boolean positive) {
        if (editor == null) {
            Messages.showErrorDialog("Error 找不到编辑区", "Error");
            return;
        }
        Project project=editor.getProject();
        BufferedReader file = FileSelector.getFileReader();
        PropertiesComponent instance = PropertiesComponent.getInstance();
        if (file == null) {
            Util.showNotify(project, "未设置文件", "请先选择文件\n或者停用功能(ctrl alt d)", NotificationType.WARNING);
            return;
        }
        if (!Core.replace(editor, project, null, true)) {
            Util.showNotify(project, "找不到标记", "可以停用功能(ctrl alt d)\n或者先设置标记:\n @DocReader 与 @--- ", NotificationType.INFORMATION);
            return;
        }

        try {
            // 跳转到起始行号
            StringBuilder text = new StringBuilder();
            Config.currentLine = instance.getInt("reader_currentLine", 1);
            if (!positive && Config.currentLine <= 1) {
                return;
            }
            if (positive) {
                instance.setValue("reader_currentLine", ++Config.currentLine, 1);
            } else {
                instance.setValue("reader_currentLine", --Config.currentLine, 1);
            }
            System.out.println("move to:" + Config.currentLine);
            Core.locateLine(file);

            int onceLineNum = instance.getInt("reader_onceLineNum", 3);
            for (int i = 0; i < onceLineNum; i++) {
                text.append("\t *");
                String lineText = FileSelector.fileReader.readLine();
                text.append(lineText).append("\n");
            }
            Core.replace(editor,project, text.toString(), false);
        } catch (IOException ex) {
            Messages.showErrorDialog("无法读取文件 " + ex.getMessage(), "Error");
        }
    }
    //BOSS键
    public static void clear(Editor editor) {
        String content = """
                \t *Returns the presentation which represents the action in the place from where it is invoked
                \t *此功能尚未启用
                \t *@return Object
                """;
        if (editor != null) {
            Core.replace(editor, editor.getProject(), content, false);
        }
    }

}
