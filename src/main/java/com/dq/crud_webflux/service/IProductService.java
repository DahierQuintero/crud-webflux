package com.dq.crud_webflux.service;

import com.dq.crud_webflux.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Flux<Product> getAll();
    Mono<Product> getById(Integer id);
    Mono<Product> save(Product product);
    Mono<Product> update(Integer id, Product product);
    Mono<Void> delete(Integer id);
}
