package com.finance.customer.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.finance.common.exception.ServiceException;
import com.finance.common.utils.ValidatorUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finance.common.annotation.Log;
import com.finance.common.core.controller.BaseController;
import com.finance.common.core.domain.AjaxResult;
import com.finance.common.enums.BusinessType;
import com.finance.customer.domain.CustomerInfo;
import com.finance.customer.service.ICustomerInfoService;
import com.finance.common.utils.poi.ExcelUtil;
import com.finance.common.core.page.TableDataInfo;

/**
 * 客户信息Controller
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@RestController
@RequestMapping("/customer/customer")
public class CustomerInfoController extends BaseController {
    @Autowired
    private ICustomerInfoService customerInfoService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerInfo customerInfo) {
        startPage();
        List<CustomerInfo> list = customerInfoService.selectCustomerInfoList(customerInfo);
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:export')")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerInfo customerInfo) {
        List<CustomerInfo> list = customerInfoService.selectCustomerInfoList(customerInfo);
        ExcelUtil<CustomerInfo> util = new ExcelUtil<CustomerInfo>(CustomerInfo.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(customerInfoService.selectCustomerInfoById(id));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:add')")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerInfo customerInfo) throws ServiceException {
        ValidatorUtils.validateEntity(customerInfo);
        return toAjax(customerInfoService.insertCustomerInfo(customerInfo));
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:edit')")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerInfo customerInfo) {
        ValidatorUtils.validateEntity(customerInfo);
        return toAjax(customerInfoService.updateCustomerInfo(customerInfo));
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:customer:remove')")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(customerInfoService.deleteCustomerInfoByIds(ids));
    }

    /**
     * 获取负责人列表
     * @return
     */
    @GetMapping("/getResponseUserList")
    public AjaxResult getResponseUserList() {
        return success(customerInfoService.getResponseUserList());
    }
}
