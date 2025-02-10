package com.finance.product.domain;

import java.math.BigDecimal;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 产品信息对象 t_product_info
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@Data
public class ProductInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品分类id
     */
    private String categoryId;

    /**
     * 合同状态 0 上架  1 下架
     */
    private String status;

    /**
     * 最低价格
     */
    private BigDecimal lowPrice;

    /**
     * 最高价格
     */
    private BigDecimal highPrice;

    /**
     * 单位（字典id）
     */
    private Long unitId;

    /**
     * 是否为纸质合同 0 是  1 否
     */
    private String paperFlag;

    /**
     * 环节模板（字典id）
     */
    private Long linkTemplateId;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;

    /**
     * 推荐价格
     */
    private String recommendPrice;

    /**
     * 产品分类
     */
    private String childrenName;

    /**
     * 产品板块
     */
    private String parentName;

    private String createName;

}
