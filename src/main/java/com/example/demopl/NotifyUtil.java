package com.example.demopl;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class NotifyUtil {

    public static void showNotify(@Nullable Project project, String title, String content, NotificationType type) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DocReader_Notifications")
                .createNotification(title, content, type)
                .notify(project);
    }
}
