package com.pinyougou.page.service;

public interface ItemPageService {

	/**
	 * 根据商品ID生成商品详情页
	 * @param id
	 * @return
	 */
	public boolean genItemPage(Long id);

	/**
	 * 根据id删除静态页面
	 * @param ids
	 * @return
	 */
	public boolean deleteById(Long id);
}
