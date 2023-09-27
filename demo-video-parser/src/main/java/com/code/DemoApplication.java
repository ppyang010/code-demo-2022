package com.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

@SpringBootApplication
@Slf4j

public class DemoApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext application = SpringApplication.run(DemoApplication.class, args);

            Environment env = application.getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            if (StringUtils.isEmpty(path)) {
                path = "";
            }
            log.info("\n----------------------------------------------------------\n\t" +
                    "项目启动成功\n\t" +
                    "Application  is running! Access URLs:\n\t" +
                    "Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
                    "External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
                    "----------------------------------------------------------");
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            log.info("当前项目进程号：" + jvmName.split("@")[0]);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
