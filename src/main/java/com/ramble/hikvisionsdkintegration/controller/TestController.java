package com.ramble.hikvisionsdkintegration.controller;

import com.alibaba.fastjson2.JSON;
import com.ramble.hikvisionsdkintegration.dto.GlobalResponseEntity;
import com.ramble.hikvisionsdkintegration.sdklib.HCNetSDK;
import com.ramble.hikvisionsdkintegration.service.SdkInitService;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project     hikvision-sdk-integration
 * Package     com.ramble.hikvisionsdkintegration.controller
 * Class       TestController
 * Date        2023/3/14 11:40
 * Author      MingliangChen
 * Email       cnaylor@163.com
 * Description
 */


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private static String m_sDeviceIP = "192.168.1.xx";
    private static String m_sUsername = "xx";
    private static String m_sPassword = "xx";

    /**
     * 获取sdk状态
     *
     * @return {@link GlobalResponseEntity}<{@link String}>
     * 返回值举例：{"success":true,"code":"000000","message":"request successfully",
     * "data":"{\"dwRes\":[0,0,0,0,0,0,0,0,0,0],\"dwTotalAlarmChanNum\":0,\"dwTotalBroadCastNum\":0,\"dwTotalFileSearchNum\":0,\"dwTotalFormatNum\":0,
     * \"dwTotalLogSearchNum\":0,\"dwTotalLoginNum\":1,\"dwTotalPlayBackNum\":0,\"dwTotalRealPlayNum\":0,\"dwTotalSerialNum\":0,\"dwTotalUpgradeNum\":0,
     * \"dwTotalVoiceComNum\":0,\"autoRead\":true,\"autoWrite\":true,\"pointer\":{\"size\":84,\"valid\":true}}"}
     */
    @GetMapping("/state")
    public GlobalResponseEntity<String> getSdkState() {
        //登录
        Integer userId = login();
        log.info("userId={}", userId);
        HCNetSDK.NET_DVR_SDKSTATE sdkState = new HCNetSDK.NET_DVR_SDKSTATE();
        //获取当前SDK状态信息
        boolean result = SdkInitService.hCNetSDK.NET_DVR_GetSDKState(sdkState);
        if (result) {
            sdkState.read();
            String s = JSON.toJSONString(sdkState);
            return GlobalResponseEntity.success(s);
        } else {
            int error = SdkInitService.hCNetSDK.NET_DVR_GetLastError();
            return GlobalResponseEntity.error("获取失败，错误码为：" + error);
        }
    }

    private Integer login() {
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息

        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = Short.valueOf("8000");
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
        int loginHandler = SdkInitService.hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (loginHandler == -1) {
            int errorCode = SdkInitService.hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(errorCode);
            log.error("[HK] login fail errorCode:{}, errMsg:{}", errorCode, SdkInitService.hCNetSDK.NET_DVR_GetErrorMsg(errorInt));
            return null;
        } else {
            return loginHandler;
        }
    }
}
