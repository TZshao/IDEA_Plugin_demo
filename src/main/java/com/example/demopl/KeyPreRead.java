package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jsoup.internal.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyPreRead extends AnAction {


    private BufferedReader reader;
    private int currentLine = 0;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        if (editor == null) {
            Messages.showErrorDialog("Failed to fiend edit", "Error");
            return;
        }
        RandomAccessFile file = FileSelector.getFile();
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String selectedFilePath = instance.getValue("reader_selectedFilePath");
        // 检查是否已选择文件
        if (file == null) {
            boolean haveFile = FileSelector.initFile(selectedFilePath);
            if (!haveFile || StringUtil.isBlank(selectedFilePath)) {
                Messages.showErrorDialog("No file selected! Please select a file first.", "Error");
                return;
            }
            file = FileSelector.getFile();
        }
        try {
            // 跳转到起始行号
            StringBuilder text = new StringBuilder();
            currentLine = instance.getInt("reader_currentLine",1);
            System.out.println(currentLine-1);
            if (currentLine <= 1) {
                return;
            }
            Util.locateLinePre(file, currentLine);
            int onceLineNum = Config.onceLineNum == -1 ? instance.getInt("reader_onceLineNum", 3) : Config.onceLineNum;
            boolean isFirst = true;
            for (int i = 0; i < onceLineNum; i++) {
                String lineText = new String(FileSelector.file.readLine().getBytes(StandardCharsets.ISO_8859_1));
                if (isFirst) {//读多行，但pos仅前进一行
                    isFirst = false;
                    Config.currentPos = file.getFilePointer();
                }
                text.append(lineText).append("\n");

            }
            instance.setValue("reader_currentLine", --currentLine, 1);
            replace(editor,e.getProject(),text.toString());
            System.out.println(text);
        } catch (IOException ex) {
            Messages.showErrorDialog("Failed to read the file: " + ex.getMessage(), "Error");
        }
//
    }



    public void replace(Editor editor, Project project,String content) {
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
            com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction(project, () -> {
                document.replaceString(replaceStart, replaceEnd, "\n"+content);
            });
        } else {
            Messages.showMessageDialog("Markers not found!", "Error", Messages.getErrorIcon());
        }
    }
}
