package net.dengzixu.bilvetools;

import net.dengzixu.bilvetools.constant.Constant;
import org.apache.commons.cli.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BilveToolsApplication implements CommandLineRunner {
    // LOGGER
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BilveToolsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BilveToolsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options()
                .addRequiredOption("ID", "room-id", true, "直播间 ID");

        try {
            CommandLine line = parser.parse(options, args);

            if (!line.hasOption("room-id")) {
                LOGGER.error("缺少直播间ID");
                System.exit(1);
            }

            Constant.ROOM_ID = Long.parseLong(line.getOptionValue("room-id"));

            LOGGER.info("直播间ID: {}", Constant.ROOM_ID);
        } catch (ParseException e) {
            LOGGER.error("命令行解析错误", e);
            System.exit(1);
        }
    }
}
