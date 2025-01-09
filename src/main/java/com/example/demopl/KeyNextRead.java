package com.example.demopl;

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




}
