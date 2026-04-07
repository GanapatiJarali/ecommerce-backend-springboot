
package org.ganapati.project.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * author by Ganapati-Jarali
 */


@SpringBootApplication
//@ComponentScan(basePackages = {
//        "org.ganapati.project.ecommerce",
//        "org.ganapati.project.ecommerce.repository"
//})
@EnableCaching
//@EnableJpaRepositories("org.example.ecommerce.repository")
@Slf4j
public class EcommerceApplication  {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//        log.info("Beans :{} ", (Object) context.getBeanDefinitionNames());
//    }
}
