package com.finance.product.domain;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.finance.common.annotation.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类信息对象 t_product_category
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@Data
public class ProductCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 上级分类id
     */
    private String parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;

    /**
     * 上级分类名
     */
    private String firstName;

    /**
     * 创建人名
     */
    private String createName;

    private List<ProductCategory> children = new ArrayList<>();
}
