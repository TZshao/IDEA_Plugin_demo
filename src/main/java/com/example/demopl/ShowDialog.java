//package com.example.demopl;
//
//import com.intellij.openapi.actionSystem.AnAction;
//import com.intellij.openapi.actionSystem.AnActionEvent;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.ui.DialogBuilder;
//import org.jetbrains.annotations.NotNull;
//
//import javax.swing.*;
//
///**
// * @author shaoyh
// * @version V1.0
// */
//public class ShowDialog extends AnAction {
//    @Override
//    public void actionPerformed(@NotNull AnActionEvent e) {
//        Project project = e.getProject();
//        DiaLog diaLog = new DiaLog();
//        // 构建对话框
//        DialogBuilder dialogBuilder = new DialogBuilder(project);
//        // 设置对话框显示内容
//        dialogBuilder.setCenterPanel(diaLog.getPanel());
//        dialogBuilder.setTitle("设置");
//        // 在 UI 渲染完成后初始化监听器
////        SwingUtilities.invokeLater(diaLog::initListener);
//        // 显示对话框
//        dialogBuilder.show();
////        diaLog.initListener();
//
//    }
//
//}
