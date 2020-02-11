package com.example.leidong.ldplugin.utils

import org.apache.http.util.TextUtils

/**
 * Created by Lei Dong on 2020/2/10.
 */
class LogUtils {
    /*打印标题日志*/

    static void printTitle(String titleStr) {
        if (!TextUtils.isEmpty(titleStr)) {
            println "==============================="
            println titleStr
            println "==============================="
        }
    }

    static void printContent(String contentStr) {
        if (!TextUtils.isEmpty(contentStr)) {
            println "  " + contentStr
        }
    }

}
