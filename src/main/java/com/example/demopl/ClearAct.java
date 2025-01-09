package com.example.demopl;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/**
 * @author shaoyh
 * @version V1.0
 */
public class ClearAct extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        String content = """
                \t *到常量折叠的约束。在构造后覆盖此字段将导致问题。
                \t *此外，它还标记为 Stable to trust the contents of the array. JDK 中没有其他工具提供此功能。
                \t *Stable 在这里是安全的，因为 value 永远不会为 null。
                """;
        if (editor != null) {
            Core.replace(editor, e.getProject(), content, false);
        }
    }
}