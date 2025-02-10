<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品分类" prop="categoryId">
        <treeselect style="width: 200px;" v-model="queryParams.categoryId" :options="categoryOptions" :show-count="true"
                    placeholder="请选择产品分类" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="产品状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择产品状态" clearable
                   @keyup.enter.native="handleQuery">
          <el-option v-for="dict in dict.type.product_status" :key="dict.value" :label="dict.label"
                     :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="环节模板" prop="linkTemplateId">
        <el-select v-model="queryParams.linkTemplateId" placeholder="请选择环节模板" clearable
                   @keyup.enter.native="handleQuery">
          <el-option v-for="dict in dict.type.link_template" :key="dict.value" :label="dict.label"
                     :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['product:info:add']"
        >新增
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" style="width: 100%;" :data="infoList">
      <el-table-column label="产品名称" :show-overflow-tooltip="true" align="center" prop="name"/>
      <el-table-column label="产品板块" :show-overflow-tooltip="true" align="center" prop="parentName"/>
      <el-table-column label="产品分类" :show-overflow-tooltip="true" align="center" prop="childrenName"/>
      <el-table-column label="产品状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch class="tableScopeSwitch" v-model="scope.row.status" active-text="上架" inactive-text="下架" active-value="0" inactive-value="1"
                     @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <
      <el-table-column label="推荐价格（元）" :show-overflow-tooltip="true" width="150" align="center" prop="recommendPrice"/>
      <el-table-column label="单位" align="center" prop="unitId">
        <template slot-scope="scope">
          <span>{{ getDictName(scope.row.unitId, dict.type.unit_type) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否为纸质合同" align="center" prop="paperFlag">
        <template slot-scope="scope">
          <span>{{ scope.row.paperFlag === '0' ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="环节模板" align="center" prop="linkTemplateId">
        <template slot-scope="scope">
          <span>{{ getDictName(scope.row.linkTemplateId, dict.type.link_template) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="说明" :show-overflow-tooltip="true" align="center" prop="remark"/>
      <el-table-column label="创建人" align="center" prop="createName"/>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleDetail(scope.row)"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:info:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改产品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="产品名称" prop="name">
              <el-input v-model="form.name" :disabled="detailFlag" placeholder="请输入产品名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="产品分类" prop="categoryId">
              <treeselect v-model="form.categoryId" :disabled="detailFlag" :options="categoryOptions" :show-count="true"
                          placeholder="请选择产品分类"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="产品状态" prop="status" required>
              <el-switch class="tableScopeSwitch1" :disabled="detailFlag" v-model="form.status" active-value="0" inactive-value="1" active-text="上架"
                         inactive-text="下架"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="推荐价格（元）" label-width="120px" required>
              <el-input style="width: 45%;" :disabled="detailFlag" type="number" v-model="form.lowPrice" placeholder="请输入最低价格"/>
              ～
              <el-input style="width: 45%;" :disabled="detailFlag" type="number" v-model="form.highPrice" placeholder="请输入最高价格"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="单位"  prop="unitId" required>
              <el-select v-model="form.unitId" :disabled="detailFlag" placeholder="请选择单位">
                <el-option v-for="dict in dict.type.unit_type" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="是否为纸质合同" label-width="120px" prop="paperFlag" required>
              <el-radio v-model="form.paperFlag" :disabled="detailFlag" label="0">是</el-radio>
              <el-radio v-model="form.paperFlag" :disabled="detailFlag" label="1">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="描述说明" prop="remark">
              <el-input :disabled="detailFlag" :rows="4" v-model="form.remark" type="textarea" placeholder="请输入描述说明"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="环节模板" prop="linkTemplateId" required>
              <el-select v-model="form.linkTemplateId" :disabled="detailFlag" placeholder="请选择环节模板">
                <el-option v-for="dict in dict.type.link_template" :key="dict.value" :label="dict.label"
                           :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" v-if="!detailFlag" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listInfo, getInfo, delInfo, addInfo, updateInfo, categoryTreeSelect} from "@/api/product/info";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Info",
  dicts: ['unit_type', 'link_template', 'product_status'],
  components: {Treeselect},
  data() {
    return {
      detailFlag:false,
      categoryOptions: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        categoryId: null,
        status: null,
        lowPrice: null,
        highPrice: null,
        unitId: null,
        paperFlag: null,
        linkTemplateId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "产品名称不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "产品分类不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "产品状态不能为空", trigger: "blur"}
        ],
        lowPrice: [
          {required: true, message: "最低价格不能为空", trigger: "blur"},
          { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输⼊正确的格式,可保留两位⼩数' }
        ],
        highPrice: [
          {required: true, message: "最高价格不能为空", trigger: "blur"},
          { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输⼊正确的格式,可保留两位⼩数' }
        ],
        unitId: [
          {required: true, message: "单位不能为空", trigger: "blur"}
        ],
        paperFlag: [
          {required: true, message: "是否为纸质合同不能为空", trigger: "blur"}
        ],
        linkTemplateId: [
          {required: true, message: "环节模板不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCategoryTree();
  },
  methods: {
    /** 查询产品信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        categoryId: null,
        status: null,
        lowPrice: null,
        highPrice: null,
        unitId: null,
        paperFlag: null,
        remark: null,
        linkTemplateId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();

      this.open = true;
      this.title = "添加产品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.form.unitId = response.data.unitId.toString();
        this.form.linkTemplateId = response.data.linkTemplateId.toString();
        this.open = true;
        this.detailFlag = false;
        this.title = "修改产品信息";
      });
    },
    //详情
    handleDetail(row){
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.form.unitId = response.data.unitId.toString();
        this.form.linkTemplateId = response.data.linkTemplateId.toString();
        this.open = true;
        this.detailFlag = true;
        this.title = "产品详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除当前产品数据？').then(function () {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    getCategoryTree() {
      categoryTreeSelect().then(response => {
        this.categoryOptions = JSON.parse(JSON.stringify(response.data));
      });
    },
    getDictName(dictValue, datas) {
      let str = dictValue.toString();
      const dictItem = datas.find(item => str === item.value);
      return dictItem ? dictItem.label : '';
    },
    //修改合同状态
    handleStatusChange(row){
      updateInfo({'id':row.id, 'status':row.status}).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
  }
};
</script>

<style>
.tableScopeSwitch .el-switch__label {
  position: absolute;
  display: none;
  color: #fff;
}
/*打开时文字位置设置*/
.tableScopeSwitch .el-switch__label--right {
  z-index: 1;
  right: 8px;    /*不同场景下可能不同，自行调整*/
}
/*关闭时文字位置设置*/
.tableScopeSwitch .el-switch__label--left {
  z-index: 1;
  left: 10px;    /*不同场景下可能不同，自行调整*/
}
/*显示文字*/
.tableScopeSwitch .el-switch__label.is-active {
  display: block;
}
.tableScopeSwitch.el-switch .el-switch__core,
.el-switch .el-switch__label {
  width: 55px !important;    /*开关按钮的宽度大小*/
}

.tableScopeSwitch1 .el-switch__label {
  position: absolute;
  display: none;
  color: #fff;
}
/*打开时文字位置设置*/
.tableScopeSwitch1 .el-switch__label--right {
  z-index: 1;
  right: -8px;    /*不同场景下可能不同，自行调整*/
}
/*关闭时文字位置设置*/
.tableScopeSwitch1 .el-switch__label--left {
  z-index: 1;
  left: 20px;    /*不同场景下可能不同，自行调整*/
}
/*显示文字*/
.tableScopeSwitch1 .el-switch__label.is-active {
  display: block;
}
.tableScopeSwitch1.el-switch .el-switch__core,
.el-switch .el-switch__label {
  width: 60px !important;    /*开关按钮的宽度大小*/
}
</style>
