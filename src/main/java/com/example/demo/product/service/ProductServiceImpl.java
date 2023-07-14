package com.example.demo.product.service;

import com.example.demo.product.controller.form.ProductRequestForm;
import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements  ProductService {

    final private ProductRepository productRepository;

    @Override
    public Product register(ProductRequestForm requestForm) {
        Product product = requestForm.toProduct();
        productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> list(){
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "productId"));
    }

    @Override
    public Product read(Long productId){
        Optional<Product> maybeProduct = productRepository.findById(productId);

        if(maybeProduct.isEmpty()){
            log.info("상품 정보가 존재하지 않습니다.");
            return null;
        }

        return maybeProduct.get();
    }

    @Override
    public Boolean delete(Long productId){
        Optional<Product> maybeProduct = productRepository.findById(productId);

        if(maybeProduct.isPresent()){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}