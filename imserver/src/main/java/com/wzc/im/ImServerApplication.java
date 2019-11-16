package com.wzc.im;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author WANGZIC
 */
@Slf4j
@MapperScan(basePackages="com.wzc.*.mapper")
@SpringBootApplication
public class ImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
        try {
            String ip =InetAddress.getLocalHost().getHostAddress();
            log.info("项目启动启动成功！首页地址: http://"+ip+":8686/cgim");
            log.info("项目启动启动成功！接口地址: http://127.0.0.1:8686/cgim/swagger-ui.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
