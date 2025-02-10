package com.finance.customer.mapper;

import java.util.List;

import com.finance.customer.domain.CustomerInfo;

/**
 * 客户信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-10
 */
public interface CustomerInfoMapper {
    /**
     * 查询客户信息
     *
     * @param id 客户信息主键
     * @return 客户信息
     */
    CustomerInfo selectCustomerInfoById(String id);

    /**
     * 查询客户信息列表
     *
     * @param customerInfo 客户信息
     * @return 客户信息集合
     */
    List<CustomerInfo> selectCustomerInfoList(CustomerInfo customerInfo);

    /**
     * 新增客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    int insertCustomerInfo(CustomerInfo customerInfo);

    /**
     * 修改客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    int updateCustomerInfo(CustomerInfo customerInfo);

    /**
     * 删除客户信息
     *
     * @param id 客户信息主键
     * @return 结果
     */
    int deleteCustomerInfoById(String id);

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCustomerInfoByIds(String[] ids);
}
