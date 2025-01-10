package com.example.demopl.action;

import com.example.demopl.core.Config;
import com.example.demopl.core.Core;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class KeyPreRead extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (Config.open) {
            Editor editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
            Core.moveLine(editor, true);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabled(Config.open);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT; // 指定在事件分发线程运行
    }


}
