package com.finance.customer.domain;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @NotNull(message = "客户名称不能为空")
    private String name;

    /**
     * 客户类型 0 潜在客户 1 签约客户
     */
    @NotNull(message = "客户类型不能为空")
    private String type;

    /**
     * 来源id
     */
    @NotNull(message = "客户来源不能为空")
    private Long sourceId;

    /**
     * 来源子id
     */
    private Long sourceChildId;

    /**
     * 负责人id
     */
    @NotNull(message = "负责人不能为空")
    private String responseUserId;

    /**
     * 老客户或渠道来源员工id
     */
    private Long sourceUserId;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;

    /**
     * 团队成员列表
     */
    @NotEmpty(message = "团队成员不能为空")
    private List<CustomerTeamInfo> teamList;

    /**
     * 联系人列表
     */
    @NotEmpty(message = "联系人不能为空")
    private List<CustomerContact> contactList;

}
