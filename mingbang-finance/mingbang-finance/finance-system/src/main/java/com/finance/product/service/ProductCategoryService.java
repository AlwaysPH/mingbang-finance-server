package com.finance.product.service;

import java.util.List;

import com.finance.product.domain.ProductCategory;
import com.finance.product.domain.CategoryTreeSelect;

/**
 * 产品分类信息Service接口
 *
 * @author ruoyi
 * @date 2025-02-07
 */
public interface ProductCategoryService {
    /**
     * 查询产品分类信息
     *
     * @param id 产品分类信息主键
     * @return 产品分类信息
     */
    ProductCategory selectTProductCategoryById(String id);

    /**
     * 查询产品分类信息列表
     *
     * @param productCategory 产品分类信息
     * @return 产品分类信息集合
     */
    List<ProductCategory> selectTProductCategoryList(ProductCategory productCategory);

    /**
     * 新增产品分类信息
     *
     * @param productCategory 产品分类信息
     * @return 结果
     */
    int insertTProductCategory(ProductCategory productCategory);

    /**
     * 修改产品分类信息
     *
     * @param productCategory 产品分类信息
     * @return 结果
     */
    int updateTProductCategory(ProductCategory productCategory);

    /**
     * 批量删除产品分类信息
     *
     * @param ids 需要删除的产品分类信息主键集合
     * @return 结果
     */
    int deleteTProductCategoryByIds(String[] ids);

    /**
     * 判断是否有下级子节点
     * @param ids
     * @return
     */
    boolean hasChildById(String[] ids);

    /**
     * 获取产品分类树型节点数据
     * @return
     */
    List<CategoryTreeSelect> getCategoryTreeSelect(ProductCategory category);

}
