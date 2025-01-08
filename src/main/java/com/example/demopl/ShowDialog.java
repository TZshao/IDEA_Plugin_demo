package com.example.demopl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author shaoyh
 * @version V1.0
 * @company Finedo.cn
 * @date 2025/1/8 13:45
 */
public class ShowDialog extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        DiaLog diaLog = new DiaLog();
        // 构建对话框
        DialogBuilder dialogBuilder = new DialogBuilder(project);
        // 设置对话框显示内容
        dialogBuilder.setCenterPanel(diaLog.getPanel());
        dialogBuilder.setTitle("提示框标题");
        // 显示对话框
        dialogBuilder.show();

    }


}
