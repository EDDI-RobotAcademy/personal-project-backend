package com.jinho.project.product.service.request;

import com.jinho.project.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRegisterRequest {

    final private String productName;
    final private String productPrice;
    final private String productInfo;
    final private String userToken;


    public Product toProduct() {
        return new Product(productName, productPrice, productInfo);
    }
}

