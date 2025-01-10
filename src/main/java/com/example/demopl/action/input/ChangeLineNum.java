package com.example.demopl.action.input;

import com.example.demopl.util.Util;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ChangeLineNum extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String input = Messages.showInputDialog(
                "Input number:",
                "输入显示行数",
                Messages.getQuestionIcon(),
                instance.getValue("reader_onceLineNum"),
                new InputValidator() {
                    @Override
                    public boolean checkInput(@NlsSafe String inputString) {
                        return Util.isPNumber(inputString);
                    }

                    @Override
                    public boolean canClose(@NlsSafe String inputString) {
                        return Util.isPNumber(inputString);
                    }
                }     // 默认值
                // 验证器
        );
        if (Integer.parseInt(input) <1) {
            input = "1";
        }
            instance.setValue("reader_onceLineNum", Integer.parseInt(input), 1);
    }
}
