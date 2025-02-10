package com.finance.product.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.finance.common.utils.DateUtils;
import com.finance.common.utils.SecurityUtils;
import com.finance.common.utils.StringUtils;
import com.finance.common.utils.uuid.IdUtils;
import com.finance.product.domain.CategoryTreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finance.product.mapper.ProductCategoryMapper;
import com.finance.product.domain.ProductCategory;
import com.finance.product.service.ProductCategoryService;

/**
 * 产品分类信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 查询产品分类信息
     *
     * @param id 产品分类信息主键
     * @return 产品分类信息
     */
    @Override
    public ProductCategory selectTProductCategoryById(String id) {
        return productCategoryMapper.selectTProductCategoryById(id);
    }

    /**
     * 查询产品分类信息列表
     *
     * @param productCategory 产品分类信息
     * @return 产品分类信息
     */
    @Override
    public List<ProductCategory> selectTProductCategoryList(ProductCategory productCategory) {
        return productCategoryMapper.selectTProductCategoryList(productCategory);
    }

    /**
     * 新增产品分类信息
     *
     * @param productCategory 产品分类信息
     * @return 结果
     */
    @Override
    public int insertTProductCategory(ProductCategory productCategory) {
        productCategory.setId(IdUtils.fastSimpleUUID());
        if(StringUtils.isEmpty(productCategory.getParentId())){
            productCategory.setParentId("0");
        }
        productCategory.setCreateBy(SecurityUtils.getUserId().toString());
        productCategory.setCreateTime(DateUtils.getNowDate());
        productCategory.setDelFlag("0");
        return productCategoryMapper.insertTProductCategory(productCategory);
    }

    /**
     * 修改产品分类信息
     *
     * @param productCategory 产品分类信息
     * @return 结果
     */
    @Override
    public int updateTProductCategory(ProductCategory productCategory) {
        productCategory.setUpdateTime(DateUtils.getNowDate());
        productCategory.setUpdateBy(SecurityUtils.getUserId().toString());
        return productCategoryMapper.updateTProductCategory(productCategory);
    }

    /**
     * 批量删除产品分类信息
     *
     * @param ids 需要删除的产品分类信息主键
     * @return 结果
     */
    @Override
    public int deleteTProductCategoryByIds(String[] ids) {
        return productCategoryMapper.deleteTProductCategoryByIds(ids, SecurityUtils.getUserId());
    }

    /**
     * 判断是否有下级子节点
     * @param ids
     * @return
     */
    @Override
    public boolean hasChildById(String[] ids) {
        return productCategoryMapper.getChildById(ids) > 0 ? true : false;
    }

    /**
     * 获取产品分类树型节点数据
     * @return
     */
    @Override
    public List<CategoryTreeSelect> getCategoryTreeSelect(ProductCategory category) {
        List<ProductCategory> list = selectTProductCategoryList(category);
        return buildCategoryTreeSelect(list);
    }

    private List<CategoryTreeSelect> buildCategoryTreeSelect(List<ProductCategory> list) {
        List<ProductCategory> returnList = new ArrayList<ProductCategory>();
        List<String> tempList = list.stream().map(ProductCategory::getId).collect(Collectors.toList());
        for (ProductCategory category : list) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(category.getParentId())) {
                recursionFn(list, category);
                returnList.add(category);
            }
        }
        if (returnList.isEmpty()) {
            returnList = list;
        }
        return returnList.stream().map(CategoryTreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ProductCategory> list, ProductCategory t) {
        // 得到子节点列表
        List<ProductCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ProductCategory tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ProductCategory> getChildList(List<ProductCategory> list, ProductCategory t) {
        List<ProductCategory> tlist = new ArrayList<ProductCategory>();
        Iterator<ProductCategory> it = list.iterator();
        while (it.hasNext()) {
            ProductCategory n = (ProductCategory) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && org.apache.commons.lang3.StringUtils.equals(n.getParentId(), t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ProductCategory> list, ProductCategory t) {
        return getChildList(list, t).size() > 0;
    }
}
