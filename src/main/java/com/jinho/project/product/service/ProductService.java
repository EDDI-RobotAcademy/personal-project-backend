package com.jinho.project.product.service;

import com.jinho.project.product.controller.form.BusinessProductListResponseForm;
import com.jinho.project.product.controller.form.ProductListResponseForm;
import com.jinho.project.product.controller.form.ProductReadResponseForm;
import com.jinho.project.product.entity.Product;
import com.jinho.project.product.service.request.ProductRegisterRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    @Transactional
    ProductReadResponseForm read(Long id);
    void delete(Long id);
    Boolean register(ProductRegisterRequest productRegisterRequest);

    List<ProductListResponseForm> list();

    List<BusinessProductListResponseForm> businessRegisterProductList(Long accountId);
  
    Product modify(Long productId, ProductRegisterRequest requestForm);
}
