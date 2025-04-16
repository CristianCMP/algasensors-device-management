package com.algaworks.algasensors.device.management.api.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@AllArgsConstructor
public class RestClientFactory {

    public final RestClient.Builder builder;

    public RestClient temperatureMonitoringRestClient() {
        return builder
                .baseUrl("http://localhost:8082")
                .requestFactory(generateClienteHpptRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayExeption();
                })
                .build();
    }

    private ClientHttpRequestFactory generateClienteHpptRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));

        return factory;
    }
}
