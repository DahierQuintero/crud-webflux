package com.dq.crud_webflux.service.impl;

import com.dq.crud_webflux.entity.Product;
import com.dq.crud_webflux.exception.ProductAlreadyExistsException;
import com.dq.crud_webflux.exception.ProductNotFoundException;
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
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found")));
    }

    @Override
    public Mono<Product> save(Product product) {
        Mono<Boolean> existsName = productRepository.findByName(product.getName()).hasElement();
        return existsName.flatMap(exists -> exists ? Mono.error(new ProductAlreadyExistsException("Product name is already exist in the system")) : productRepository.save(product));
    }

    @Override
    public Mono<Product> update(Integer id, Product product) {
        Mono<Boolean> productId = productRepository.findById(id).hasElement();
        Mono<Boolean> productRepeatedName = productRepository.repeatedName(id, product.getName()).hasElement();
        return productId.flatMap(
                existsId -> existsId ?
                        productRepeatedName.flatMap(existsName -> existsName ? Mono.error(new ProductAlreadyExistsException("Product name is already exist in the system"))
                                : productRepository.save(new Product(id, product.getName(), product.getPrice())))
                        : Mono.error(new ProductNotFoundException("Product not found")));
    }

    @Override
    public Mono<Void> delete(Integer id) {
        Mono<Boolean> productId = productRepository.findById(id).hasElement();
        return productId.flatMap(exists -> exists ? productRepository.deleteById(id) : Mono.error(new ProductNotFoundException("Product not found")));
    }
}
