package aula.funcionario.msfuncionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsFuncionarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFuncionarioApplication.class, args);
    }

}
