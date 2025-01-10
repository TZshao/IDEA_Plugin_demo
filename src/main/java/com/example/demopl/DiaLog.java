//package com.example.demopl;
//
//import com.example.demopl.action.input.ChapterExtractor;
//import com.example.demopl.action.FileSelector;
//import com.intellij.ide.util.PropertiesComponent;
//
//import javax.swing.*;
//import java.util.Map;
//
///**
// * @author shaoyh
// * @version V1.0
// * @date 2025/1/8 12:06
// */
//public class DiaLog {
//    private JPanel panel;
//    private JTextField lineNum;
//    private JButton buttonLineNum;
//    private JTextField jumpTo;
//    private JButton buttonJumpTo;
//    private JTextField chapterRge;
//    private JButton buttonChapter;
//
//    public String getLineNum() {
//        return lineNum.getText();
//    }
//
//    public JPanel getPanel() {
//        return panel;
//    }
//
//    public void initListener() {
//        PropertiesComponent instance = PropertiesComponent.getInstance();
//        //跳转
//        buttonJumpTo.addActionListener(e -> {
//            String input = jumpTo.getText();
//            if (input.isEmpty() || !Core.isPNumber(input)) {
//                JOptionPane.showMessageDialog(panel, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                int line = Integer.parseInt(input);
//                System.out.println("jump to ::" + line);
//                // 在这里添加跳转逻辑，比如定位到指定代码行
//                if (null == FileSelector.getFileReader()) {
//                    return;
//                }
//                Config.currentLine = line - 1;
//                instance.setValue("reader_currentLine", Config.currentLine, 1);
//                Core.locateLine(FileSelector.getFileReader());
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(panel, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        //单页显示数量
//        buttonLineNum.addActionListener(e -> {
//            String onceLineNum = lineNum.getText();
//            if (Core.isPNumber(onceLineNum)) {
//                instance.setValue("reader_onceLineNum", Integer.parseInt(onceLineNum), 1);
//            }
//        });
//
//        //章节分析
//        buttonChapter.addActionListener(e -> {
//            String rgeText = chapterRge.getText();
//            if (Core.isEmpty(rgeText)) return;
//            instance.setValue("reader_chapterRge", rgeText);
//            Map<String, Integer> map = ChapterExtractor.extractChapters(rgeText);
//            if (map.isEmpty()) {
//                JOptionPane.showMessageDialog(panel, "未识别到有效章节！", "错误", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            Config.chapters = map;
//            JOptionPane.showMessageDialog(panel, "别到章节，共：" + map.size(), "完成", JOptionPane.INFORMATION_MESSAGE);
//        });
//        String readerCurrentLine = instance.getValue("reader_currentLine");
//        String chapterRange = instance.getValue("reader_chapterRange");
//        lineNum.setText(readerCurrentLine);
//        chapterRge.setText(chapterRange);
//    }
//
//
//}
//
//
//
//
