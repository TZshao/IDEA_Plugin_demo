package com.example.demopl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * @author shaoyh
 * @version V1.0
 * @company Finedo.cn
 * @date 2025/1/8 15:09
 */
public class Util {

    //正整数
    public static boolean isPNumber(String string) {
        try {
            int i = Integer.parseInt(string);
            return i > 0;
        }catch (Exception e) {
            return false;
        }
    }

    //定位行
    public static long locateLineNext(RandomAccessFile file, int line) throws IOException {
        if (null==file) return -1;
        if (Config.currentPos != -1) {
            file.seek(Config.currentPos);
            return Config.currentPos;
        }
        long pos = 0, lineNum = 1;
        String lineText;
        try {
            while (lineNum <= line && (lineText = file.readLine()) != null) {
                pos = file.getFilePointer();
                lineNum++;
//                System.out.println("skip Line::" + new String(lineText.getBytes(StandardCharsets.ISO_8859_1)));
            }
            Config.currentPos = file.getFilePointer();
        }catch (Exception ex) {}
        return pos;
    }

    //定位前一行
    public static long locateLinePre(RandomAccessFile file, int line) throws IOException  {
        if (null==file) return -1;
        long pos = 0, lineNum = 1;
        String lineText;
        try {
            file.seek(0);
            while (lineNum <= line-2 && (lineText = file.readLine()) != null) {
                pos = file.getFilePointer();
                lineNum++;
            }
        }catch (Exception ex) {}
        return pos;
    }
}
