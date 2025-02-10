import request from '@/utils/request'

// 查询产品分类信息列表
export function listCategory(query) {
  return request({
    url: '/product/category/list',
    method: 'get',
    params: query
  })
}

// 查询产品分类信息详细
export function getCategory(id) {
  return request({
    url: '/product/category/' + id,
    method: 'get'
  })
}

// 新增产品分类信息
export function addCategory(data) {
  return request({
    url: '/product/category',
    method: 'post',
    data: data
  })
}

// 修改产品分类信息
export function updateCategory(data) {
  return request({
    url: '/product/category',
    method: 'put',
    data: data
  })
}

// 删除产品分类信息
export function delCategory(id) {
  return request({
    url: '/product/category/' + id,
    method: 'delete'
  })
}
