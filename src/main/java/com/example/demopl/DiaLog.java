package com.example.demopl;

import com.intellij.ide.util.PropertiesComponent;
import kotlinx.html.I;

import javax.swing.*;
import java.io.IOException;
import java.util.Map;

/**
 * @author shaoyh
 * @version V1.0
 * @company Finedo.cn
 * @date 2025/1/8 12:06
 */
public class DiaLog {
    private JPanel panel;
    private JTextField lineNum;
    private JButton buttonLineNum;
    private JTextField jumpTo;
    private JButton buttonJumpTo;
    private JTextField chapterRge;
    private JButton buttonChapter;

    public String getLineNum() {
        return lineNum.getText();
    }

    public JPanel getPanel() {
        return panel;
    }

    public DiaLog() {
        lineNum.setText(PropertiesComponent.getInstance().getValue("reader_onceLineNum"));
        chapterRge.setText(PropertiesComponent.getInstance().getValue("reader_chapterRge"));
        //跳转
        buttonJumpTo.addActionListener(e -> {
            String input = jumpTo.getText();
            if (input.isEmpty() || !Util.isPNumber(input)) {
                JOptionPane.showMessageDialog(panel, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                PropertiesComponent instance = PropertiesComponent.getInstance();
                instance.setValue("reader_currentLine", Integer.parseInt(input), 0);
                System.out.println("jump to ::" + instance.getValue("reader_currentLine"));
                // 在这里添加跳转逻辑，比如定位到指定代码行
                if (null == FileSelector.getFile()) {
                    return;
                }
                Util.locateLinePre(FileSelector.getFile(), Integer.parseInt(input));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //单页显示数量
        buttonLineNum.addActionListener(e -> {
            String onceLineNum = lineNum.getText();
            if (Util.isPNumber(onceLineNum)) {
                PropertiesComponent.getInstance().setValue("reader_onceLineNum", Integer.parseInt(onceLineNum), 3);
            }
        });

        //章节分析
        buttonChapter.addActionListener(e -> {
            String rgeText = chapterRge.getText();
            if (Util.isEmpty(rgeText)) return;
            PropertiesComponent.getInstance().setValue("reader_chapterRge", rgeText);
            Map<String, Integer> map = ChapterExtractor.extractChapters(rgeText);
            if (map.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "未识别到有效章节！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Config.chapters = map;
            JOptionPane.showMessageDialog(panel, "别到章节，共："+map.size(), "完成", JOptionPane.INFORMATION_MESSAGE);
        });

    }


}
