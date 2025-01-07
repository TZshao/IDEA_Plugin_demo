package com.example.demopl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

public class HelloWorld extends AnAction {

    public static String selectedFilePath;
    public static int startLine = 0;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 打开文件选择器
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);
        VirtualFile file = FileChooser.chooseFile(fileChooserDescriptor, e.getProject(), null);

        if (file != null) {
            selectedFilePath = file.getPath();
            // 提示用户输入起始行号
            String inputLine = Messages.showInputDialog("Enter the starting line number:", "Input Start Line", Messages.getQuestionIcon());
            try {
                if (inputLine != null) {
                    startLine = Integer.parseInt(inputLine);
                    Messages.showInfoMessage("File selected: " + selectedFilePath + "\nStart line: " + startLine, "Selection Complete");
                }
            } catch (NumberFormatException ex) {
                Messages.showErrorDialog("Invalid line number!", "Error");
            }
        }
    }
}
