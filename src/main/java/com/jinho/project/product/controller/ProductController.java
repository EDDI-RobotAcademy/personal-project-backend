package com.jinho.project.product.controller;


import com.jinho.project.account.service.AccountService;
import com.jinho.project.product.controller.form.*;
import com.jinho.project.product.entity.Product;
import com.jinho.project.product.service.ProductService;
import com.jinho.project.product.service.request.ProductRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.jinho.project.account.entity.RoleType.BUSINESS;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    final private ProductService productService;
    final private AccountService accountService;

    @GetMapping("/{id}")
    public ProductReadResponseForm readProduct(@PathVariable("id") Long id) {
        log.info("readProduct()");
        return productService.read(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        log.info("deleteProduct()");
        productService.delete(id);
    }

    @PostMapping(value = "/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public Boolean registerProduct(@RequestPart(value = "aboutProduct") ProductRegisterRequestForm registerRequestForm
                                   ) {




        return productService.register(registerRequestForm.toProductRegisterRequest());
    }

    @PostMapping("/list")
    public List<ProductListResponseForm> list() {
        List<ProductListResponseForm> returnList;
        returnList = productService.list();
        return returnList;
    }

    @PostMapping("/business-product-list")
    public List<BusinessProductListResponseForm> businessRegisterProductList(@RequestBody BusinessProductListRequestForm requestForm) {
        String userToken = requestForm.getUserToken();
        final Long accountId = accountService.findAccountId(userToken);
        log.info("accountId: " + accountId);

        List<BusinessProductListResponseForm> responseList = productService.businessRegisterProductList(accountId);
        log.info("businessRegisterProductList: " + responseList);

        return responseList;
    }

    @PutMapping("/{id}")
    public Product modifyProduct (@PathVariable("id") Long id,
                                  @RequestBody ProductRegisterRequest requestForm) {
        log.info("modifyProduct(): "+  requestForm);
        log.info("id:" + id);

        return productService.modify(id, requestForm);
    }
}