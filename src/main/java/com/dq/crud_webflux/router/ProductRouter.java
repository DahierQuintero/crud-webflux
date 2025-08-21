package com.dq.crud_webflux.router;

import com.dq.crud_webflux.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    private static final String BASE_PATH = "/api/v1/products";

    @Bean
    RouterFunction<ServerResponse> router(ProductHandler handler) {
        return RouterFunctions.route()
                .GET(BASE_PATH, handler::getAll)
                .GET(BASE_PATH + "/{id}", handler::getOne)
                .POST(BASE_PATH, handler::save)
                .PUT(BASE_PATH + "/{id}", handler::update)
                .DELETE(BASE_PATH + "/{id}", handler::delete)
                .build();
    }
}
