package com.example.demopl;

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

        // 更新按钮文本
        Presentation presentation = e.getPresentation();
        presentation.setText(Config.open ? "启用" : "停用");

        // 处理启用/停用逻辑
        if (Config.open) {
            System.out.println("功能已启用");
        } else {
            System.out.println("功能已停用");
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 动态更新菜单项显示状态
        Presentation presentation = e.getPresentation();
        presentation.setText(Config.open ? "停用" : "启用");
    }
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT; // 指定在事件分发线程运行
    }
}
