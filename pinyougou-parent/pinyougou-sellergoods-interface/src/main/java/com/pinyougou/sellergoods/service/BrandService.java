package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbBrand;

import entity.PageResult;

/**
 * 品牌接口
 * @author AqqJe
 *
 */
public interface BrandService {

	// 查询所有品牌
	public List<TbBrand> findAll(); 
	
	// 品牌分页
	public PageResult findPage(int pageNum, int pageSize);
	
	// 品牌添加
	public void add(TbBrand brand);
	
	// 品牌修改
	public void update(TbBrand brand);
	
	// 根据ID获取实体
	public TbBrand findOne(Long id);
	
	// 批量删除
	public void delete(Long[] ids);
	
	// 条件查询
	public PageResult findPage(TbBrand brand,int pageNum, int pageSize);
}
