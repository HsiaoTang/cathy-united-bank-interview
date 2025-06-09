package com.cub.interview.cathyunitedbankinterview;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.SQLException;

@EnableJpaAuditing
@SpringBootApplication
public class CathyUnitedBankInterviewApplication {

    public static void main(String[] args) throws SQLException {
        Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-tcpPort", "9092",
                "-baseDir", "./data"
        ).start();
        SpringApplication.run(CathyUnitedBankInterviewApplication.class, args);
    }

}
