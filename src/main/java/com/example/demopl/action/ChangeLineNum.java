package com.example.demopl.action;

import com.example.demopl.util.Util;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ChangeLineNum extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String onceLineNum = JOptionPane.showInputDialog("请输入显示行数:",instance.getValue("reader_onceLineNum"));
        if (Util.isPNumber(onceLineNum)) {
            instance.setValue("reader_onceLineNum", Integer.parseInt(onceLineNum), 1);
        }
    }
}
