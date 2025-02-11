package com.finance.customer.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.finance.common.core.domain.TreeSelect;
import com.finance.common.core.domain.entity.SysDept;
import com.finance.common.core.domain.entity.SysUser;
import com.finance.common.utils.DateUtils;
import com.finance.common.utils.SecurityUtils;
import com.finance.common.utils.uuid.IdUtils;
import com.finance.customer.domain.CustomerContact;
import com.finance.customer.domain.CustomerTeamInfo;
import com.finance.customer.mapper.CustomerContactMapper;
import com.finance.customer.mapper.CustomerTeamInfoMapper;
import com.finance.system.service.ISysDeptService;
import com.finance.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finance.customer.mapper.CustomerInfoMapper;
import com.finance.customer.domain.CustomerInfo;
import com.finance.customer.service.ICustomerInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysUserService userService;

    @Resource
    private CustomerTeamInfoMapper customerTeamInfoMapper;

    @Resource
    private CustomerContactMapper customerContactMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setId(IdUtils.fastSimpleUUID());
        customerInfo.setDelFlag("0");
        customerInfo.setCreateBy(SecurityUtils.getUserId().toString());
        customerInfo.setCreateTime(DateUtils.getNowDate());
        int row = customerInfoMapper.insertCustomerInfo(customerInfo);

        //保存团队成员信息
        if(CollectionUtils.isNotEmpty(customerInfo.getTeamList())){
            List<CustomerTeamInfo> teamList = customerInfo.getTeamList();
            teamList.forEach(e -> {
                e.setId(IdUtils.fastSimpleUUID());
                e.setCustomerId(customerInfo.getId());
                e.setCreateBy(SecurityUtils.getUserId().toString());
                e.setCreateTime(DateUtils.getNowDate());
                e.setDelFlag("0");
            });
            row += customerTeamInfoMapper.batchSave(teamList);
        }
        //保存联系人信息
        if(CollectionUtils.isNotEmpty(customerInfo.getContactList())){
            List<CustomerContact> contactList = customerInfo.getContactList();
            contactList.forEach(e -> {
               e.setId(IdUtils.fastSimpleUUID());
               e.setCustomerId(customerInfo.getId());
               e.setCreateBy(SecurityUtils.getUserId().toString());
               e.setCreateTime(DateUtils.getNowDate());
               e.setDelFlag("0");
            });
            row += customerContactMapper.batchSave(contactList);
        }
        return row;
    }

    /**
     * 修改客户信息
     *
     * @param customerInfo 客户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setUpdateBy(SecurityUtils.getUserId().toString());
        customerInfo.setUpdateTime(DateUtils.getNowDate());
        int row = customerInfoMapper.updateCustomerInfo(customerInfo);
        if(CollectionUtils.isNotEmpty(customerInfo.getTeamList())){
            //删除原团队成员数据
            customerTeamInfoMapper.deleteByCustomerId(customerInfo.getId(), SecurityUtils.getUserId().toString());
            //保存团队成员数据
            List<CustomerTeamInfo> teamList = customerInfo.getTeamList();
            teamList.forEach(e -> {
                e.setId(IdUtils.fastSimpleUUID());
                e.setCustomerId(customerInfo.getId());
                e.setCreateBy(SecurityUtils.getUserId().toString());
                e.setCreateTime(DateUtils.getNowDate());
                e.setDelFlag("0");
            });
            row += customerTeamInfoMapper.batchSave(teamList);
        }
        if(CollectionUtils.isNotEmpty(customerInfo.getContactList())){
            //删除原联系人信息
            customerContactMapper.deleteByCustomerId(customerInfo.getId(), SecurityUtils.getUserId().toString());
            //保存联系人数据
            List<CustomerContact> contactList = customerInfo.getContactList();
            contactList.forEach(e -> {
                e.setId(IdUtils.fastSimpleUUID());
                e.setCustomerId(customerInfo.getId());
                e.setCreateBy(SecurityUtils.getUserId().toString());
                e.setCreateTime(DateUtils.getNowDate());
                e.setDelFlag("0");
            });
            row += customerContactMapper.batchSave(contactList);
        }
        return row;
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCustomerInfoByIds(String[] ids) {
        //删除客户信息
        int row = customerInfoMapper.deleteCustomerInfoByIds(ids, SecurityUtils.getUserId().toString());
        //删除客户关联团队信息
        customerTeamInfoMapper.deleteByCustomerId(ids[0], SecurityUtils.getUserId().toString());
        //删除客户联系人信息
        customerContactMapper.deleteByCustomerId(ids[0], SecurityUtils.getUserId().toString());
        return row;
    }

    /**
     * 获取负责人列表
     * @return
     */
    @Override
    public List<TreeSelect> getResponseUserList() {
        //获取部门树
        List<SysDept> deptList = deptService.selectDeptList(new SysDept());
        if(CollectionUtils.isEmpty(deptList)){
            return Lists.newArrayList();
        }
        List<TreeSelect> treeData = deptService.buildDeptTreeSelect(deptList);
        //获取所有用户
        List<SysUser> userList = userService.selectUserList(new SysUser());
        if(CollectionUtils.isEmpty(treeData) || CollectionUtils.isEmpty(userList)){
            return Lists.newArrayList();
        }
        Map<Long, List<SysUser>> map = userList.stream().collect(Collectors.groupingBy(SysUser::getDeptId));
        //部门下人员数据组装
        buildDeptUser(treeData, map);
        return treeData;
    }

    /**
     * 部门下人员数据组装
     * @param treeData
     * @param map
     */
    private void buildDeptUser(List<TreeSelect> treeData, Map<Long, List<SysUser>> map) {
        treeData.forEach(e -> {
            List<SysUser> userList = map.get(e.getId());
            e.setUserList(userList);
            e.setDisabled(true);
            if(CollectionUtils.isNotEmpty(e.getChildren())){
                buildDeptUser(e.getChildren(), map);
            }
        });
    }
}
