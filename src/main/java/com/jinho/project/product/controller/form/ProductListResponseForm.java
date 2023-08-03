package com.jinho.project.product.controller.form;

import com.jinho.project.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductListResponseForm {

    final private Long id;
    final private String productName;
    final private String productPrice;
    final private String productInfo;

    public ProductListResponseForm(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productInfo = product.getProductInfo();
    }

}
