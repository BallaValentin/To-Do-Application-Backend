package edu.bbte.idde.bvim2209.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "edu.bbte.idde.bvim2209")
public class ToDoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ToDoApplication.class)
                .headless(false)
                .run(args);
    }
}
