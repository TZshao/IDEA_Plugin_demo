package com.example.demopl.action;

import com.example.demopl.core.Config;
import com.example.demopl.core.Core;
import com.example.demopl.util.Util;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JumpToLine extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String input = JOptionPane.showInputDialog("请输入目标行:");

        if (input.isEmpty() || !Util.isPNumber(input)) {
            return;
        }

        try {
            int line = Integer.parseInt(input);
            System.out.println("jump to ::" + line);
            // 在这里添加跳转逻辑，比如定位到指定代码行
            if (null == FileSelector.getFileReader()) {
                return;
            }
            Config.currentLine = line - 1;
            PropertiesComponent instance = PropertiesComponent.getInstance();
            instance.setValue("reader_currentLine", Config.currentLine, 1);
            Core.locateLine(FileSelector.getFileReader());
        } catch (NumberFormatException ignored) {
        }
    }
}
