package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ChapterJumps extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 动态生成选项列表
        List<chapter> options = generateDynamicOptions();
        if (options.isEmpty()) {
            Messages.showInfoMessage("File 请先解析章节", "ERROR");
            return;
        }
        Window window = WindowManager.getInstance().getFrame(e.getProject());
        // 获取窗口中心点
        Point centerPoint;
        if (window == null) {
            return;
        }
        centerPoint = getWindowCenter(window);
        // 创建弹窗菜单
        JBPopupFactory.getInstance()
                .createPopupChooserBuilder(options)
                .setTitle("选择章节")
                .setItemChosenCallback(chapter -> {
                    System.out.println("select "+chapter);
                    PropertiesComponent.getInstance().setValue("reader_currentLine", chapter.chapterNum-1, 1);
                    Config.currentLine = chapter.chapterNum-1;
                    Core.locateLine(FileSelector.fileReader);
                })
                .createPopup()
                .show(new RelativePoint(centerPoint));
    }

    // 动态生成选项内容
    private List<chapter> generateDynamicOptions() {
        List<chapter> options = new ArrayList<>();
        Map<String, Integer> chapters = Config.chapters;
        if (chapters==null || chapters.isEmpty()) {
            chapters=new HashMap<>();
            String rge = PropertiesComponent.getInstance().getValue("reader_chapterRge");
            if (!Core.isEmpty(rge)) {
                chapters = ChapterExtractor.extractChapters(rge);
            }
        }
        chapters.forEach((key, value) -> options.add(new chapter(key, value)));
        return options;
    }

    private Point getWindowCenter(Window window) {
        Rectangle bounds = window.getBounds(); // 获取窗口边界
        int centerX = bounds.x + bounds.width / 2; // 中心 X 坐标
        int centerY = bounds.y + bounds.height / 2; // 中心 Y 坐标
        return new Point(centerX, centerY);
    }

    record chapter(String chapterName, int chapterNum) {
        @Override
        public String toString() {
            return chapterName+"::第"+chapterNum+"行";
        }
    }

}
