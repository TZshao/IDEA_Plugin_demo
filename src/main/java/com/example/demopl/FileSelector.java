package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class FileSelector extends AnAction {

    public static String selectedFilePath;
    public static RandomAccessFile file;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 打开文件选择器
        FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor();
        fileChooserDescriptor.withFileFilter(file -> file.getName().endsWith(".txt"));
        VirtualFile vFile = FileChooser.chooseFile(fileChooserDescriptor, e.getProject(), null);
        try {
            selectedFilePath = vFile.getPath();
            PropertiesComponent properties = PropertiesComponent.getInstance();
            properties.setValue("reader_selectedFilePath", selectedFilePath);
            if (vFile != null) {
                file = new RandomAccessFile(selectedFilePath, "r");
                Messages.showInfoMessage("File selected: " + selectedFilePath, "Selection Complete");
            }
        } catch (FileNotFoundException ex) {
        }
    }

    public static RandomAccessFile getFile() {
        if (file == null) {
            PropertiesComponent instance = PropertiesComponent.getInstance();
            if (instance.getValue("reader_selectedFilePath") != null) {
                initFile(instance.getValue("reader_selectedFilePath"));
            }
        }
        return file;
    }

    public static boolean initFile(String filePath) {
        try {
            file = new RandomAccessFile(filePath, "r");
            selectedFilePath = filePath;
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

}
