package cl.transporte.caul;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cl.transporte.caul.repository")
@MapperScan("cl.transporte.caul.mapper")
public class TransporteEmpresarialApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransporteEmpresarialApplication.class, args);
    }
}
