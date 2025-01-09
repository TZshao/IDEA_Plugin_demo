package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jsoup.internal.StringUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class KeyNextRead extends AnAction {

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
        if (!Util.replace(editor, e.getProject(), null, true)) {
            return;
        }

        try {
            // 跳转到起始行号
            StringBuilder text = new StringBuilder();
            currentLine = instance.getInt("reader_currentLine", 1);
            Util.locateLineNext(file, currentLine);

            int onceLineNum = instance.getInt("reader_onceLineNum", 3);
            boolean isFirst = true;
            for (int i = 0; i < onceLineNum; i++) {
                String lineText = new String(FileSelector.file.readLine().getBytes(StandardCharsets.ISO_8859_1));
                if (isFirst) {//读多行，但pos仅前进一行
                    isFirst = false;
                    Config.currentPos = file.getFilePointer();
                }
                text.append(lineText).append("\n");
            }
            instance.setValue("reader_currentLine", ++currentLine, 1);
            Util.replace(editor,e.getProject(),text.toString(),false);

        } catch (IOException ex) {
            Messages.showErrorDialog("Failed to read the file: " + ex.getMessage(), "Error");
        }
    }




}
