package com.finance.customer.domain;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户关联团队成员信息对象 t_customer_team_info
 *
 * @author ruoyi
 * @date 2025-02-11
 */
@Data
public class CustomerTeamInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 客户id
     */
    private String customerId;

    /**
     * 团队成员id
     */
    private Long userId;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;

}
