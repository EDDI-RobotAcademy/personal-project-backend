package com.example.demo.product.controller;

import com.example.demo.product.controller.form.ProductRequestForm;
import com.example.demo.product.entity.Product;
import com.example.demo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    final private ProductService productService;

    @PostMapping("/register")
    public Product productRegister(@RequestBody ProductRequestForm productRequestForm) {
        log.info("productRegister()");

        return productService.register(productRequestForm);
    }

    @GetMapping("/list")
    public List<Product> productList() {
        log.info("productList()");

        List<Product> returnedProductList = productService.list();
        log.info("returnedProductList: " + returnedProductList);

        return returnedProductList;
    }

    @GetMapping("/{productId}")
    public Product productRead (@PathVariable("productId") Long productId){
        log.info("readProduct()");

        return productService.read(productId);
    }

    @DeleteMapping("/{productId}")
    public Boolean productDelete (@PathVariable("productId") Long productId){
        log.info("deleteProduct()");

        return productService.delete(productId);
    }
}