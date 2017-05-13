package com.philip.goodbook.utils;

/**
 * Created by Zhangdingying on 2017/5/13.
 */

public class Utils {
    public static String[] parseOnline(String url) {
        String[] urls = url.split(" ");
        String[] strs = new String[3];
        String strJd = null;
        String strDD = null;
        String strAMZ = null;
        for (String str : urls) {
            if (str.contains("京东")) {
                strJd = str.split(":", 2)[1];
            } else if (str.contains("当当")) {
                strDD = str.split(":", 2)[1];
            } else if (str.contains("亚马逊")) {
                strAMZ = str.split(":", 2)[1];
            }
        }
        strs[0] = strJd;
        strs[1] = strDD;
        strs[2] = strAMZ;
        return strs;
    }
}
