package com.finance.product.mapper;

import java.util.List;

import com.finance.product.domain.ProductInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 产品信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-08
 */
public interface ProductInfoMapper {
    /**
     * 查询产品信息
     *
     * @param id 产品信息主键
     * @return 产品信息
     */
    ProductInfo selectProductInfoById(String id);

    /**
     * 查询产品信息列表
     *
     * @param productInfo 产品信息
     * @return 产品信息集合
     */
    List<ProductInfo> selectProductInfoList(ProductInfo productInfo);

    /**
     * 新增产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    int insertProductInfo(ProductInfo productInfo);

    /**
     * 修改产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    int updateProductInfo(ProductInfo productInfo);

    /**
     * 删除产品信息
     *
     * @param id 产品信息主键
     * @return 结果
     */
    int deleteProductInfoById(String id);

    /**
     * 批量删除产品信息
     *
     * @param ids    需要删除的数据主键集合
     * @param userId
     * @return 结果
     */
    int deleteProductInfoByIds(@Param("ids") String[] ids, @Param("userId")Long userId);
}
