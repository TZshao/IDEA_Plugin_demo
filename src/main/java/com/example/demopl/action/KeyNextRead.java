package com.example.demopl.action;

import com.example.demopl.core.Config;
import com.example.demopl.core.Core;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class KeyNextRead extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (Config.open) {
            Core.moveLine(e, true);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabled(Config.open);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT; // 指定在事件分发线程运行
    }

}
