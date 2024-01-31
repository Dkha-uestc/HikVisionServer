package com.ramble.hikvisionsdkintegration;

import com.ramble.hikvisionsdkintegration.service.SdkInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Project     hikvision-sdk-integration
 * Package     com.ramble.hikvisionsdkintegration
 * Class       AppRunner
 * Date        2023/3/10 17:38
 * Author      MingliangChen
 * Email       cnaylor@163.com
 * Description
 */

@Component
public class AppRunner  implements ApplicationRunner {

    @Autowired
    private SdkInitService hksdkInitService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        hksdkInitService.initSdk();
    }
}
