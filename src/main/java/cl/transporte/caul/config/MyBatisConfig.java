package cl.transporte.caul.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cl.transporte.caul.mapper")
public class MyBatisConfig {
}
