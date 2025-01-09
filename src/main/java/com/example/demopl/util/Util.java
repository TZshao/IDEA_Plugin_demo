package com.example.demopl.util;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class Util {

    public static void showNotify(@Nullable Project project, String title, String content, NotificationType type) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DocReader_Notifications")
                .createNotification("DocReader: "+title, content, type)
                .notify(project);
    }
    //正整数
    public static boolean isPNumber(String string) {
        try {
            int i = Integer.parseInt(string);
            return i > 0;
        }catch (Exception e) {
            return false;
        }
    }
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
