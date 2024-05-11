package com.code.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrowserCommandRunner implements CommandLineRunner {

    @Value("${spring.web.loginurl}")
    private String loginUrl;

    @Value("${spring.auto.openurl}")
    private boolean isOpen;

    @Value("${extra.name}")
    private String extraConfigName;

    @Override
    public void run(String... args) {
        String os = getOs();
        if (isOpen) {
            log.info("自动加载指定的页面");
            try {
                Runtime.getRuntime().exec("cmd /c start " + loginUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.warn("浏览器打开页面异常");
            }
        }
    }

    public String getOs() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");

        log.info("Operating System Name: " + osName);
        log.info("Operating System Version: " + osVersion);
        log.info("Operating System Architecture: " + osArch);
        log.info("extraConfigName: " + extraConfigName);

        String os = null;
        // 判断操作系统类型
        if (osName.startsWith("Windows")) {
            os = "Windows";
        } else if (osName.startsWith("Linux")) {
            os = "Linux";
        } else if (osName.startsWith("Mac")) {
            os = "Mac";
        } else {
            os = "unknown";
        }
        return os;
    }

}
