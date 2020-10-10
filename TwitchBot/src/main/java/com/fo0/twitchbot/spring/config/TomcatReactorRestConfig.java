package com.fo0.twitchbot.spring.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

import lombok.extern.log4j.Log4j2;

/**
 * @created 10.10.2020 - 21:35:01
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Configuration
@EnableWebFlux
@Log4j2
public class TomcatReactorRestConfig {

    @Value("${server.port:7777}")
    private int port;

    // @Value("${server.context.path}")
    private String contextPath = "/api";

    @Bean
    public TomcatReactiveWebServerFactory nettyReactiveWebServerFactory() {
        TomcatReactiveWebServerFactory webServerFactory = new TomcatReactiveWebServerFactory();
        webServerFactory.addConnectorCustomizers(portCustomizer());
        webServerFactory.addContextCustomizers(contextCustomizer());
        return webServerFactory;
    }

    public TomcatContextCustomizer contextCustomizer() {
        return ctx -> ctx.setPath(contextPath);
    }

    public TomcatConnectorCustomizer portCustomizer() {
        return connector -> connector.setPort(port);
    }

    @PostConstruct
    public void init() {
        log.info(String.format("Swagger-UI is now listening on http://localhost:%s%s/swagger-ui.html", port, contextPath));
    }
}
