package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
	/**
	 * 商品搜索
	 */
	public Map<String,Object> search(Map searchMap);
	
	/**
	 * 更新solr索引库数据 
	 */
	public void updateSolrData(List list);

	/**
	 * 同步删除solr索引库数据
	 * @param asList
	 */
	public void deleteByGoodsIds(List goodsId);
}
