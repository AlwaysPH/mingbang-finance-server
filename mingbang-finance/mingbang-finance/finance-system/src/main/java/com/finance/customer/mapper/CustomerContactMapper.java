package com.finance.customer.mapper;

import com.finance.customer.domain.CustomerContact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户联系人信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-11
 */
public interface CustomerContactMapper {
    /**
     * 查询客户联系人信息
     *
     * @param id 客户联系人信息主键
     * @return 客户联系人信息
     */
    CustomerContact selectCustomerContactById(String id);

    /**
     * 查询客户联系人信息列表
     *
     * @param customerContact 客户联系人信息
     * @return 客户联系人信息集合
     */
    List<CustomerContact> selectCustomerContactList(CustomerContact customerContact);

    /**
     * 新增客户联系人信息
     *
     * @param customerContact 客户联系人信息
     * @return 结果
     */
    int insertCustomerContact(CustomerContact customerContact);

    /**
     * 修改客户联系人信息
     *
     * @param customerContact 客户联系人信息
     * @return 结果
     */
    int updateCustomerContact(CustomerContact customerContact);

    /**
     * 删除客户联系人信息
     *
     * @param id 客户联系人信息主键
     * @return 结果
     */
    int deleteCustomerContactById(String id);

    /**
     * 批量删除客户联系人信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCustomerContactByIds(String[] ids);

    /**
     * 保存联系人信息
     * @param contactList
     * @return
     */
    int batchSave(@Param("list") List<CustomerContact> contactList);

    /**
     * 删除原联系人数据
     * @param customerId
     * @param userId
     */
    void deleteByCustomerId(@Param("customerId") String customerId, @Param("userId") String userId);

}
