package com.pinyougou.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;
import entity.Result;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	/**
	 * 查询品牌所有数据
	 * @return List<TbBrand>
	 */
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();
	}

	/**
	 * 品牌分页
	 * @param page
	 * @param rows
	 * @return PageResult(分页实体)
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows) {
		return brandService.findPage(page, rows);
	}
	/**
	 * 品牌添加 
	 * @param brand
	 * @return Result(返回结果实体)
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand) {
		try {
			brandService.add(brand);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	} 
}
