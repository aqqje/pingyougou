package com.pinyougou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

/**
 * 商品组合实体
 * @author Administrator
 *
 */
public class Goods implements Serializable {

	private TbGoods goods;// 商品SPU
	private TbGoodsDesc goodsDesc;// 商品扩展属性
	private List<TbItem> item; // 商品集合
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItem() {
		return item;
	}
	public void setItem(List<TbItem> item) {
		this.item = item;
	}
	
	
}
