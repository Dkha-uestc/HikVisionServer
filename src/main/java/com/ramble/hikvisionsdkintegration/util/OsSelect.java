package com.ramble.hikvisionsdkintegration.util;

/**
 * Project     hikvision-sdk-integration
 * Package     com.ramble.hikvisionsdkintegration.util
 * Class       OsSelect
 * Date        2023/3/10 18:20
 * Author      MingliangChen
 * Email       cnaylor@163.com
 * Description
 */
public class OsSelect {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
