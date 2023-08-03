package com.jinho.project.product.service;


import com.jinho.project.account.entity.Account;
import com.jinho.project.account.repository.AccountRepository;
import com.jinho.project.account.repository.UserTokenRepository;
import com.jinho.project.account.repository.UserTokenRepositoryImpl;
import com.jinho.project.product.controller.form.BusinessProductListResponseForm;
import com.jinho.project.product.controller.form.ProductListResponseForm;
import com.jinho.project.product.controller.form.ProductReadResponseForm;
import com.jinho.project.product.entity.Product;
import com.jinho.project.product.entity.ProductImages;
import com.jinho.project.product.repository.ProductImagesRepository;
import com.jinho.project.product.repository.ProductRepository;
import com.jinho.project.product.service.request.ProductRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    final private ProductRepository productRepository;
  
    final private ProductImagesRepository productImagesRepository;

    final private AccountRepository accountRepository;

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
  
    @Override
    public ProductReadResponseForm read(Long id) {
        final Optional<Product> maybeProduct = productRepository.findById(id);

        if (maybeProduct.isEmpty()) {
            log.info("존재하지 않는 상품id입니다.");
            return null;
        }
        final Product product = maybeProduct.get();
        log.info("product:" + product);
        //final List<ProductImages> productImagesList = productImagesRepository.findImagePathByProductId(id);
        final List<ProductImages> productImagesList = productImagesRepository.findByProductId(product.getId());
        log.info("productImagesList: " + productImagesList);

        return new ProductReadResponseForm(product, productImagesList);
    }

    @Override
    public void delete(Long id) {
        productImagesRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }
    public Boolean register(ProductRegisterRequest request) {

        final List<ProductImages> productImagesList = new ArrayList<>();
//        final String fixedDirectoryPath = "../../personal-project-frontend/src/assets/uploadImgs/";

        Product product = request.toProduct();
        log.info("product: " + product);

        String userToken = request.getUserToken();
        log.info("userToken: " + userToken);
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        log.info("accountId: " + accountId);
//        Optional<Account> maybeAccount = accountRepository.findById(accountId);
//        if(maybeAccount.isPresent()) {
//            product.setAccount(maybeAccount.get());
//        }

//        try {
//            for (MultipartFile multipartFile: productImg) {
////                final String originalFileName = multipartFile.getOriginalFilename();
////                final String uniqueRandomFileName = UUID.randomUUID() + originalFileName;
////                final String fullPath = fixedDirectoryPath + uniqueRandomFileName;
////                final FileOutputStream writer = new FileOutputStream(fullPath);
//
//                final String originalFileName = multipartFile.getOriginalFilename();
//                final FileOutputStream writer = new FileOutputStream(originalFileName);
//
//                log.info("originalFileName: " + originalFileName);
//
//                writer.write(multipartFile.getBytes());
//                writer.close();
//
//                ProductImages productImages = new ProductImages(originalFileName);
//                productImagesList.add(productImages);
//
//                product.setProductImages(productImages);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

        productRepository.save(product);
        productImagesRepository.saveAll(productImagesList);

        return true;
    }
  
    @Override
    public List<ProductListResponseForm> list() {
        List<ProductListResponseForm> tmpList=new ArrayList<>();
        List<Product> products=productRepository.findAll();
        for (Product product:products ){
            List<ProductImages> maybeImages=productImagesRepository.findByProductId(product.getId());
            ProductListResponseForm responseForm=new ProductListResponseForm(product);
            tmpList.add(responseForm);
        }
        return tmpList;
    }
  
    @Override
    public List<BusinessProductListResponseForm> businessRegisterProductList(Long accountId) {
        List<BusinessProductListResponseForm> businessRegisterProductList = new ArrayList<>();
        List<Product> productList = productRepository.findAllByAccountId(accountId) ;

        for (Product product: productList ){
            List<ProductImages> maybeImages=productImagesRepository.findByProductId(product.getId());
            BusinessProductListResponseForm responseForm = new BusinessProductListResponseForm(product.getProductName(), product.getProductPrice(), product.getProductInfo(), maybeImages.get(0).getImageResourcePath());
            businessRegisterProductList.add(responseForm);
        }
        return businessRegisterProductList;
    }

        // 등록할 수 있는 사람이면 상품을 등록하도록
    @Override
    public Product modify(Long id, ProductRegisterRequest requestForm){
        Optional<Product> maybeProduct = productRepository.findById(id);

        if(maybeProduct.isEmpty()){
            log.info("존재하지 않는 상품id입니다.");
            return null;
        }

        Product product = maybeProduct.get();
        product.setProductName(requestForm.getProductName());
        product.setProductPrice(requestForm.getProductPrice());
        product.setProductInfo(requestForm.getProductInfo());

        return productRepository.save(product);
    }
}
