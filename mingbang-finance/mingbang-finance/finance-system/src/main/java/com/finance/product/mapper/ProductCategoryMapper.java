package com.finance.product.mapper;

import java.util.List;

import com.finance.product.domain.ProductCategory;
import org.apache.ibatis.annotations.Param;

/**
 * 产品分类信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-07
 */
public interface ProductCategoryMapper {
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
     * @param ids    需要删除的数据主键集合
     * @param userId
     * @return 结果
     */
    int deleteTProductCategoryByIds(@Param("ids") String[] ids, @Param("userId") Long userId);

    /**
     * 判断是否有下级子节点
     * @param ids
     * @return
     */
    int getChildById(@Param("ids") String[] ids);
}
