package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.*;

public class FileSelector extends AnAction {

    public static String selectedFilePath;
    public static BufferedReader fileReader;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 打开文件选择器
        FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor();
        fileChooserDescriptor.withFileFilter(file -> file.getName().endsWith(".txt"));
        VirtualFile vFile = FileChooser.chooseFile(fileChooserDescriptor, e.getProject(), null);
        if (vFile == null) return;
        selectedFilePath = vFile.getPath();
        PropertiesComponent properties = PropertiesComponent.getInstance();
        properties.setValue("reader_selectedFilePath", selectedFilePath);
        initFile(selectedFilePath);

        Messages.showInfoMessage("File selected: " + selectedFilePath, "Selection Complete");
        properties.setValue("reader_currentLine", 1, 1);
        Config.currentLine = 1;
    }

    public static BufferedReader getFileReader() {
        if (fileReader == null) {
            PropertiesComponent instance = PropertiesComponent.getInstance();
            if (instance.getValue("reader_selectedFilePath") != null) {
                initFile(instance.getValue("reader_selectedFilePath"));
            }
        }
        return fileReader;
    }

    private static void initFile(String filePath) {
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            fileReader.mark(10000000);
        } catch (FileNotFoundException e) {
            Messages.showInfoMessage("File no found", "ERROR");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        selectedFilePath = filePath;
    }

}
