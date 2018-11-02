package com.pinyougou.page.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${pagedir}")
	private String itemDir;
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public boolean genItemPage(Long id) {
		
		try {
			//创建配置对象
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			
			//获取模板对象
			Template template = configuration.getTemplate("item.ftl");
			Map<String,Object> dataModel = new HashMap<>();
			
			//模板数据
			//1.商品信息
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			
			//2.商品扩展信息
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
			
			//3.商品分类信息
			if(itemCatMapper.selectByPrimaryKey(goods.getCategory1Id())!=null) {
				String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
				dataModel.put("itemCat1", itemCat1);
			}
			if(itemCatMapper.selectByPrimaryKey(goods.getCategory2Id())!=null) {
				String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
				dataModel.put("itemCat2", itemCat2);
			}
			if(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id())!=null) {
				String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
				dataModel.put("itemCat3", itemCat3);
			}
			
			//4.查询出SUK列表
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(id);
			criteria.andStatusEqualTo("1");
			example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认	
			List<TbItem> itemList = itemMapper.selectByExample(example);
			
			//添加数据 
			dataModel.put("goods", goods);
			dataModel.put("goodsDesc", goodsDesc);
			dataModel.put("itemList", itemList);
			
			//输出文件
			Writer out = new FileWriter(new File(itemDir+id+".html"));
			//创建模板数据
			template.process(dataModel, out); 
			//关闭输出流
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
