package com.example.demopl.core;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseWheelListener;

public class MouseWheelPlugin implements EditorFactoryListener {

    /**
     * @DocReader
	 *Returns the presentation which represents the action in the place from where it is invoked
	 *此功能尚未启用
	 *@return Object
@---
     * @param event
     */
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        JComponent contentComponent = editor.getContentComponent();
        JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, contentComponent);

        MouseWheelListener[] originalListeners = scrollPane.getMouseWheelListeners();
        // 移除所有原有监听器
        for (MouseWheelListener listener : originalListeners) {
            scrollPane.removeMouseWheelListener(listener);
        }

        // 添加自定义监听器，优先拦截事件
        scrollPane.addMouseWheelListener(e -> {
            if (Config.open && isMouseOverComment(editor)) {
                Core.moveLine(editor, e.getWheelRotation() > 0);
                e.consume(); // 阻止后续监听器接收到事件
            }

        });

        // 重新添加原有监听器
        for (MouseWheelListener listener : originalListeners) {
            scrollPane.addMouseWheelListener(listener);
        }
    }

    private boolean isMouseOverComment(Editor editor) {
        // 获取文档内容和光标位置
        int offset = editor.getCaretModel().getOffset();
        String text = editor.getDocument().getText();
        // 从光标开始向前和向后找到最近的 /** 和 */
        int startIndex = text.lastIndexOf(Config.startMark, offset);
        int endIndex = text.indexOf(Config.endMark, offset);
        // 如果找到了起始和结束标记，并且光标在范围内，则返回 true
        return startIndex != -1 && endIndex != -1 && startIndex < offset && offset < endIndex;
    }
}
