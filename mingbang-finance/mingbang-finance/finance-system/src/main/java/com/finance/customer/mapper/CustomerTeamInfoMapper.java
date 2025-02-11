package com.finance.customer.mapper;


import com.finance.customer.domain.CustomerTeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户关联团队成员信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-11
 */
public interface CustomerTeamInfoMapper {
    /**
     * 查询客户关联团队成员信息
     *
     * @param id 客户关联团队成员信息主键
     * @return 客户关联团队成员信息
     */
    CustomerTeamInfo selectCustomerTeamInfoById(String id);

    /**
     * 查询客户关联团队成员信息列表
     *
     * @param customerTeamInfo 客户关联团队成员信息
     * @return 客户关联团队成员信息集合
     */
    List<CustomerTeamInfo> selectCustomerTeamInfoList(CustomerTeamInfo customerTeamInfo);

    /**
     * 新增客户关联团队成员信息
     *
     * @param customerTeamInfo 客户关联团队成员信息
     * @return 结果
     */
    int insertCustomerTeamInfo(CustomerTeamInfo customerTeamInfo);

    /**
     * 修改客户关联团队成员信息
     *
     * @param customerTeamInfo 客户关联团队成员信息
     * @return 结果
     */
    int updateCustomerTeamInfo(CustomerTeamInfo customerTeamInfo);

    /**
     * 删除客户关联团队成员信息
     *
     * @param id 客户关联团队成员信息主键
     * @return 结果
     */
    int deleteCustomerTeamInfoById(String id);

    /**
     * 批量删除客户关联团队成员信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCustomerTeamInfoByIds(String[] ids);

    /**
     * 批量保存客户管理团队成员
     * @param teamList
     */
    int batchSave(@Param("list") List<CustomerTeamInfo> teamList);

    /**
     * 删除原团队成员数据
     * @param customerId
     * @param userId
     */
    void deleteByCustomerId(@Param("customerId") String customerId, @Param("userId") String userId);
}
