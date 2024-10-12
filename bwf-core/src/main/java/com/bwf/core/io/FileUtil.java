package com.bwf.core.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    public static String traverseFiles(File dir, List list) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String s = traverseFiles(file,list);
                    if(!"".equals(s)){
                        list.add(s);
                    }
                }
            }
            return "";
        } else {
            return dir.getAbsolutePath();
        }
    }
}
