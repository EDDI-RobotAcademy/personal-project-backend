package com.happycamper.backend.product.controller;

import com.happycamper.backend.product.controller.form.CheckProductNameDuplicateRequestForm;
import com.happycamper.backend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    final private ProductService productService;

    @GetMapping("/check-product-name-duplicate")
    public Boolean checkProductNameDuplicate(@RequestBody CheckProductNameDuplicateRequestForm requestForm) {
        Boolean isDuplicatedProductName = productService.checkProductNameDuplicate(requestForm);

        return isDuplicatedProductName;
    }
}
