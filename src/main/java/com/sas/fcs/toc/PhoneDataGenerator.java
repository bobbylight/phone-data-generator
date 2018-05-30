package com.sas.fcs.toc;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.sas.fcs.toc.model.Phone;
import com.sas.fcs.toc.service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PhoneDataGenerator implements CommandLineRunner {

    private final PhoneService phoneService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDataGenerator.class);

    @Autowired
    public PhoneDataGenerator(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public static void main(String[] args) throws Exception {

        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(PhoneDataGenerator.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Override
    public void run(String... args) throws IOException {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Phone.class).withHeader();
        ObjectWriter objectWriter = mapper.writer(schema);

        LOGGER.info("Generating phone numbers...");
        for (int i = 0; i < 150; i++) {
            phoneService.createAndSavePhones(1000);
        }

        LOGGER.info("Generating call information...");
        phoneService.createAndSaveCallInformation(1000, 100);
    }
}
