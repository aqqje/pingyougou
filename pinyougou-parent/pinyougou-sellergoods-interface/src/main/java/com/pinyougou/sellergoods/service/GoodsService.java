package com.pinyougou.sellergoods.service;
import java.util.List;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;

import entity.PageResult;
/**
 * 服务层接口
 * @author AqqJe
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Goods goods);
	
	
	/**
	 * 修改
	 */
	public void update(Goods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Goods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum,int pageSize);
	
	/**
	 * 商品审核：商品状态 0：未审核 ，1：已审核，2：审核未通过，3：关闭
	 * @param ids
	 * @param status
	 */
	public void updateStatus(Long[] ids,String status);
	
	/**
	 * 根据SPU id集合查询SKU
	 * @param goodsId
	 * @param status
	 * @return
	 */
	public List findItemByGoodsId(Long[] goodsId, String status);
}
