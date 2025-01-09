package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
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
public class Util {

    //正整数
    public static boolean isPNumber(String string) {
        try {
            int i = Integer.parseInt(string);
            return i > 0;
        }catch (Exception e) {
            return false;
        }
    }
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean replace(Editor editor, Project project, String content, boolean isCheck) {
        Document document = editor.getDocument();
        CharSequence text = document.getCharsSequence();
        String startMarker = "@author";
        String endMarker = "@return";

        // 使用正则表达式查找标识符之间的内容
        String regex = Pattern.quote(startMarker) + "(.*?)" + Pattern.quote(endMarker);
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            int replaceStart = matcher.start(1); // 获取内容开始位置
            int replaceEnd = matcher.end(1);     // 获取内容结束位置
            // 替换内容
            if (!isCheck) {
                com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction(project, () -> {
                    document.replaceString(replaceStart, replaceEnd, "\n" + content);
                });
            }
        } else {
            Messages.showMessageDialog("Markers not found!", "Error", Messages.getErrorIcon());
            return false;
        }
        return true;
    }
    //定位行
    public static void locateLineNext(RandomAccessFile file, int line) throws IOException {
        if (null==file) return;
        if (Config.currentPos != -1) {
            file.seek(Config.currentPos);
            return;
        }
        long lineNum = 1;
        String lineText;
        try {
            while (lineNum <= line && (lineText = file.readLine()) != null) {
                lineNum++;
            }
            Config.currentPos = file.getFilePointer();
        }catch (Exception ex) {
            Messages.showMessageDialog("IO异常!", "Error", Messages.getErrorIcon());
        }
    }
    //定位前一行
    public static void locateLinePre(RandomAccessFile file, int line)  {
        if (null==file) return;
        long pos = 0, lineNum = 1;
        String lineText;
        try {
            file.seek(0);
            while (lineNum <= line-2 && (lineText = file.readLine()) != null) {
                pos = file.getFilePointer();
                lineNum++;
            }
            PropertiesComponent.getInstance().setValue("reader_currentLine", line, 1);
        }catch (Exception ex) {
            Messages.showMessageDialog("IO异常!", "Error", Messages.getErrorIcon());
        }
    }

    public static long moveToPreviousLineStart(RandomAccessFile raf, long filePointer) throws IOException {
        // 从当前位置开始向前扫描
        boolean isFirstCharNewline = false;
        raf.seek(--filePointer);

        while (filePointer > 0) {
            raf.seek(--filePointer);
            int c = raf.read();

            // 检查是否遇到换行符
            if (c == '\n') {
                if (isFirstCharNewline) { // 支持 Windows 换行符（\r\n）
                    break;
                }
                isFirstCharNewline = true;
            } else if (isFirstCharNewline) {
                filePointer++; // 修正位置，指向行首
                break;
            }
        }

        // 如果文件指针到达开头，修正位置
        if (filePointer < 0) {
            filePointer = 0;
        }

        raf.seek(filePointer); // 定位到行首
        return filePointer;
    }

}
