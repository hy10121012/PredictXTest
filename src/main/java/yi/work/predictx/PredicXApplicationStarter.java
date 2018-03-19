package yi.work.predictx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by hy10121012 on 2018/3/18.
 */


@SpringBootApplication
@EnableAutoConfiguration
@ImportResource(locations = {"classpath:spring-context.xml"})
public class PredicXApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(PredicXApplicationStarter.class, args);
    }

}
