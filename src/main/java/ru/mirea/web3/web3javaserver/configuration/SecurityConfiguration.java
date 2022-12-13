package ru.mirea.web3.web3javaserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        File file = new File("ip");
        String ip = null;
        if (file.length() == 0) {
            System.out.print("Please, enter IP Address of Client Hosting Machine [x.x.x.x]: ");

            Scanner scanner = new Scanner(System.in);
            boolean isCorrect = false;
            while (!isCorrect) {
                ip = scanner.nextLine();
                if (ip.matches("^\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b")) {
                    isCorrect = true;
                } else {
                    System.out.print("IP Address is incorrect! Please, enter another [x.x.x.x]: ");
                }
            }

            FileWriter writer = new FileWriter("ip");
            writer.write(ip);
            writer.close();
        } else {
            Scanner reader = new Scanner(file);
            ip = reader.nextLine();
        }

        http
                .authorizeRequests()
                .antMatchers("/error").permitAll()
                .anyRequest().hasIpAddress(ip)
                .and()
                .csrf().disable();
        return http.build();
    }
}
