package com.finance.customer.service.impl;

import java.util.List;

import com.finance.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finance.customer.mapper.CustomerInfoMapper;
import com.finance.customer.domain.CustomerInfo;
import com.finance.customer.service.ICustomerInfoService;

/**
 * 客户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class CustomerInfoServiceImpl implements ICustomerInfoService {
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    /**
     * 查询客户信息
     *
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public CustomerInfo selectCustomerInfoById(String id) {
        return customerInfoMapper.selectCustomerInfoById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param customerInfo 客户信息
     * @return 客户信息
     */
    @Override
    public List<CustomerInfo> selectCustomerInfoList(CustomerInfo customerInfo) {
        return customerInfoMapper.selectCustomerInfoList(customerInfo);
    }

    /**
     * 新增客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    @Override
    public int insertCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setCreateTime(DateUtils.getNowDate());
        return customerInfoMapper.insertCustomerInfo(customerInfo);
    }

    /**
     * 修改客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    @Override
    public int updateCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setUpdateTime(DateUtils.getNowDate());
        return customerInfoMapper.updateCustomerInfo(customerInfo);
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerInfoByIds(String[] ids) {
        return customerInfoMapper.deleteCustomerInfoByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerInfoById(String id) {
        return customerInfoMapper.deleteCustomerInfoById(id);
    }
}
