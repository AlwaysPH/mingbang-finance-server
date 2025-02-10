package com.finance.product.service.impl;

import java.util.List;

import com.finance.common.utils.DateUtils;
import com.finance.common.utils.SecurityUtils;
import com.finance.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finance.product.mapper.ProductInfoMapper;
import com.finance.product.domain.ProductInfo;
import com.finance.product.service.IProductInfoService;

/**
 * 产品信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@Service
public class ProductInfoServiceImpl implements IProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    /**
     * 查询产品信息
     *
     * @param id 产品信息主键
     * @return 产品信息
     */
    @Override
    public ProductInfo selectProductInfoById(String id) {
        return productInfoMapper.selectProductInfoById(id);
    }

    /**
     * 查询产品信息列表
     *
     * @param productInfo 产品信息
     * @return 产品信息
     */
    @Override
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo) {
        return productInfoMapper.selectProductInfoList(productInfo);
    }

    /**
     * 新增产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    @Override
    public int insertProductInfo(ProductInfo productInfo) {
        productInfo.setId(IdUtils.fastSimpleUUID());
        productInfo.setCreateBy(SecurityUtils.getUserId().toString());
        productInfo.setCreateTime(DateUtils.getNowDate());
        productInfo.setDelFlag("0");
        return productInfoMapper.insertProductInfo(productInfo);
    }

    /**
     * 修改产品信息
     *
     * @param productInfo 产品信息
     * @return 结果
     */
    @Override
    public int updateProductInfo(ProductInfo productInfo) {
        productInfo.setUpdateBy(SecurityUtils.getUserId().toString());
        productInfo.setUpdateTime(DateUtils.getNowDate());
        return productInfoMapper.updateProductInfo(productInfo);
    }

    /**
     * 批量删除产品信息
     *
     * @param ids 需要删除的产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoByIds(String[] ids) {
        return productInfoMapper.deleteProductInfoByIds(ids, SecurityUtils.getUserId());
    }

    /**
     * 删除产品信息信息
     *
     * @param id 产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoById(String id) {
        return productInfoMapper.deleteProductInfoById(id);
    }
}
