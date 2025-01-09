package com.example.demopl.action;

import com.example.demopl.core.Config;
import com.example.demopl.util.Util;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import org.jetbrains.annotations.NotNull;

public class EnableAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 切换状态
        Config.open = !Config.open;
        Util.showNotify(e.getProject(),"状态切换",
                Config.open ? "快捷键占用已开启":"快捷键占用已关闭", NotificationType.INFORMATION);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 动态更新菜单项显示状态
        Presentation presentation = e.getPresentation();
        presentation.setText(Config.open ? "启用中" : "停用中");

    }
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT; // 指定在事件分发线程运行
    }
}
