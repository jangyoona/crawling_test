package com.cms_jsp.common;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfiguration {

    // 기본 WebClient 빌더 메서드
    private WebClient.Builder createWebClientBuilder() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024*1024*20))
                .build();
        
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

        return WebClient.builder()
                .clientConnector(
                    new ReactorClientHttpConnector(
                        HttpClient
                            .create()
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                            .secure(
                                ThrowingConsumer.unchecked(
                                    sslContextSpec -> sslContextSpec.sslContext(
                                        SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
                                    )
                                )
                            )
                            .responseTimeout(Duration.ofSeconds(60))
                    )
                )
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                    clientRequest -> {
                        log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                        clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                        return Mono.just(clientRequest);
                    }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                    clientResponse -> {
                        clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                        return Mono.just(clientResponse);
                    }
                ))
                .defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3");
    }

    // baseUrl을 받아 WebClient를 생성하는 메서드
    public WebClient createWebClient(String baseUrl) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(EncodingMode.VALUES_ONLY);
        
        return createWebClientBuilder()
                .uriBuilderFactory(factory)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean(name = "data")
    public WebClient apisDataWebClient() {
        return createWebClient("http://apis.data.go.kr");
    }

    @Bean(name = "biz")
    public WebClient bizNumWebClient() {
        return createWebClient("https://api.odcloud.kr");
    }
    
    @Bean(name = "crawl")
    public WebClient crawlingClient() {
        return createWebClient("https://infuser.odcloud.kr");
    }
    
    @Bean(name = "ccc")
    public WebClient crawlClient() {
        return createWebClient("https://news.naver.com");
    }
}