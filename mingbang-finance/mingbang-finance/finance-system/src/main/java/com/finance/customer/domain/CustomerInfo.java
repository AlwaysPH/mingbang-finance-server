package com.finance.customer.domain;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户信息对象 t_customer_info
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@Data
public class CustomerInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户类型 0 潜在客户 1 签约客户
     */
    private String type;

    /**
     * 来源id
     */
    private Long sourceId;

    /**
     * 来源子id
     */
    private Long sourceChildId;

    /**
     * 负责人id
     */
    private String responseUserId;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;
}
