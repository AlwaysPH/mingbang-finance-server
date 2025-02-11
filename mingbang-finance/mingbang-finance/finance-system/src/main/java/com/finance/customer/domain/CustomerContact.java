package com.finance.customer.domain;

import com.finance.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.finance.common.annotation.Excel;

/**
 * 客户联系人信息对象 t_customer_contact
 *
 * @author ruoyi
 * @date 2025-02-11
 */
@Data
public class CustomerContact extends BaseEntity {
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
     * 姓名
     */
    private String userName;

    /**
     * 性别 0 男 1 女
     */
    private String sex;

    /**
     * 是否首要联系人 0 否 1 是
     */
    private String mainFlag;

    /**
     * 角色类型（字典）
     */
    private String roleType;

    /**
     * 联系方式类型（字典）
     */
    private String contactType;

    /**
     * 联系方式
     */
    private String contactNum;

    /**
     * 删除标识 0 未删除 1 已删除
     */
    private String delFlag;

}
