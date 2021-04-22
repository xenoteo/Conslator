package xenoteo.com.github;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConslatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runConsoleApp(ConsoleApplication consoleApp){
        return args -> consoleApp.run();
    }

}
