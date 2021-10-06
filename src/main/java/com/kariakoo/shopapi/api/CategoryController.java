package com.kariakoo.shopapi.api;

import com.kariakoo.shopapi.entity.ProductCategory;
import com.kariakoo.shopapi.entity.ProductInfo;
import com.kariakoo.shopapi.service.CategoryService;
import com.kariakoo.shopapi.service.ProductService;
import com.kariakoo.shopapi.vo.response.CategoryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    /**
     * Show products in category
     *
     * @param categoryType
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/category/{type}")
    public CategoryPage showOne(@PathVariable("type") Integer categoryType,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size) {

        ProductCategory cat = categoryService.findByCategoryType(categoryType);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInCategory = productService.findAllInCategory(categoryType, request);
        CategoryPage tmp = new CategoryPage("", productInCategory);
        tmp.setCategory(cat.getCategoryName());
        return tmp;
    }
}
