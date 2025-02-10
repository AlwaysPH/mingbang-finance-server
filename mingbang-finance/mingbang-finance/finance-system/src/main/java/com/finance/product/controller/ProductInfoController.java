package com.finance.product.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.finance.product.domain.ProductCategory;
import com.finance.product.service.ProductCategoryService;
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
import com.finance.product.domain.ProductInfo;
import com.finance.product.service.IProductInfoService;
import com.finance.common.utils.poi.ExcelUtil;
import com.finance.common.core.page.TableDataInfo;

/**
 * 产品信息Controller
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/product/info")
public class ProductInfoController extends BaseController {
    @Autowired
    private IProductInfoService productInfoService;

    @Resource
    private ProductCategoryService categoryService;

    /**
     * 查询产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductInfo productInfo) {
        startPage();
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        return getDataTable(list);
    }

    /**
     * 导出产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:info:export')")
    @Log(title = "产品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductInfo productInfo) {
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
        util.exportExcel(response, list, "产品信息数据");
    }

    /**
     * 获取产品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(productInfoService.selectProductInfoById(id));
    }

    /**
     * 新增产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:add')")
    @Log(title = "产品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductInfo productInfo) {
        return toAjax(productInfoService.insertProductInfo(productInfo));
    }

    /**
     * 修改产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:edit')")
    @Log(title = "产品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductInfo productInfo) {
        return toAjax(productInfoService.updateProductInfo(productInfo));
    }

    /**
     * 删除产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:remove')")
    @Log(title = "产品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(productInfoService.deleteProductInfoByIds(ids));
    }

    /**
     * 获取产品分类树型节点数据
     * @return
     */
    @GetMapping("/categoryTreeSelect")
    public AjaxResult categoryTreeSelect(ProductCategory category) {
        return success(categoryService.getCategoryTreeSelect(category));
    }
}
