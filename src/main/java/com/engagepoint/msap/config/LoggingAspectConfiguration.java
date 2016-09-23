package com.engagepoint.msap.config;

import com.engagepoint.msap.aop.logging.LoggingAspect;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    @Profile(Constants.SPRING_PROFILE_PRODUCTION)
    public LoggingAspect loggingAspectProd() {
        return new LoggingAspect();
    }
}
