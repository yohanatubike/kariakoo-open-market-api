package com.kariakoo.shopapi.service;

import com.kariakoo.shopapi.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    List<ProductCategory> findAll();

    ProductCategory findByCategoryType(Integer categoryType);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);


}
