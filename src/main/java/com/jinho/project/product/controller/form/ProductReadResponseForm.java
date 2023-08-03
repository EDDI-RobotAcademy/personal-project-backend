package com.jinho.project.product.controller.form;

import com.jinho.project.product.entity.Product;
import com.jinho.project.product.entity.ProductImages;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductReadResponseForm {

    final private Long id;
    final private String productName;
    final private String productPrice;
    final private String productInfo;
    final private List<String> productImagesPathList = new ArrayList<>();

    public ProductReadResponseForm(Product product, List<ProductImages> productImagesList) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productInfo = product.getProductInfo();

        for (ProductImages images: productImagesList) {
            this.productImagesPathList.add(images.getImageResourcePath());
        }
    }
}
