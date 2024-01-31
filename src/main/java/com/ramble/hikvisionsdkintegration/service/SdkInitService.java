package com.ramble.hikvisionsdkintegration.service;

import com.ramble.hikvisionsdkintegration.sdklib.HCNetSDK;
import com.ramble.hikvisionsdkintegration.task.InitSdkTask;
import com.ramble.hikvisionsdkintegration.util.OSUtils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Project     hikvision-sdk-integration
 * Package     com.ramble.hikvisionsdkintegration.service
 * Class       SdkInitService
 * Date        2023/3/10 17:38
 * Author      MingliangChen
 * Email       cnaylor@163.com
 * Description
 */

@Slf4j
@Component
public class SdkInitService {

    public static HCNetSDK hCNetSDK = null;

    static FExceptionCallBack_Imp fExceptionCallBack;

    static class FExceptionCallBack_Imp implements HCNetSDK.FExceptionCallBack {
        public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
            System.out.println("异常事件类型:" + dwType);
            return;
        }
    }

    public SdkInitService() {
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                try {
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(OSUtils.getLoadLibrary(), HCNetSDK.class);
                } catch (Exception ex) {
                    log.error("SdkInitService-init-hCNetSDK-error");
                }
            }
        }
    }

    @Autowired
    private ThreadPoolExecutor executor;

    public void initSdk() {
        log.info("HKSDKInitService-init-coming");
        executor.execute(new InitSdkTask());
    }
}
