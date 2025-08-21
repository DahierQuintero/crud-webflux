package com.dq.crud_webflux.service.impl;

import com.dq.crud_webflux.entity.Product;
import com.dq.crud_webflux.repository.IProductRepository;
import com.dq.crud_webflux.service.IProductService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> update(Integer id, Product product) {
        return productRepository.save(new Product(id, product.getName(), product.getPrice()));
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return productRepository.deleteById(id);
    }
}
