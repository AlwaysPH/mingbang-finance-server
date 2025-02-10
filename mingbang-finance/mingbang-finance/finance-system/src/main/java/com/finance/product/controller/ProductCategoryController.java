package com.finance.product.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finance.common.annotation.Log;
import com.finance.common.core.controller.BaseController;
import com.finance.common.core.domain.AjaxResult;
import com.finance.common.enums.BusinessType;
import com.finance.product.domain.ProductCategory;
import com.finance.product.service.ProductCategoryService;
import com.finance.common.core.page.TableDataInfo;

/**
 * 产品分类信息Controller
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@RestController
@RequestMapping("/product/category")
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService tProductCategoryService;

    /**
     * 查询产品分类信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductCategory productCategory) {
        startPage();
        List<ProductCategory> list = tProductCategoryService.selectTProductCategoryList(productCategory);
        return getDataTable(list);
    }

    /**
     * 获取产品分类信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(tProductCategoryService.selectTProductCategoryById(id));
    }

    /**
     * 新增产品分类信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:add')")
    @Log(title = "产品分类信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductCategory productCategory) {
        return toAjax(tProductCategoryService.insertTProductCategory(productCategory));
    }

    /**
     * 修改产品分类信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:edit')")
    @Log(title = "产品分类信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductCategory productCategory) {
        return toAjax(tProductCategoryService.updateTProductCategory(productCategory));
    }

    /**
     * 删除产品分类信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "产品分类信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //判断是否有下级子节点
        if(tProductCategoryService.hasChildById(ids)){
            return warn("存在子节点,不允许删除");
        }
        return toAjax(tProductCategoryService.deleteTProductCategoryByIds(ids));
    }
}
