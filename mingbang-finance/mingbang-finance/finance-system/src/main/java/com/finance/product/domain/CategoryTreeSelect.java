package com.finance.product.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class CategoryTreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 节点禁用
     */
    private boolean disabled = false;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryTreeSelect> children;

    public CategoryTreeSelect() {

    }

    public CategoryTreeSelect(ProductCategory category) {
        this.id = category.getId();
        this.label = category.getName();
        this.children = category.getChildren().stream().map(CategoryTreeSelect::new).collect(Collectors.toList());
    }


}
