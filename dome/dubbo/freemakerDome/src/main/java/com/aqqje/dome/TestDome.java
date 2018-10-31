package com.aqqje.dome;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestDome {

	public static void main(String[] args) throws Exception {
		//1.对象模板配置对象 Configuration(参数为模板版本号)
		Configuration conf = new Configuration(Configuration.getVersion());
		//2.设置模板文件夹路径
		conf.setDirectoryForTemplateLoading(new File("E:\\github\\pingyougou\\dome\\dubbo\\freemakerDome\\src\\main\\resources\\ftl"));
		//3.设置字符集
		conf.setDefaultEncoding("utf-8");
		//4.获取模板文件创建模板对象
		Template template = conf.getTemplate("yjgm.ftl");
		//5.创建模板数据对象
		Map<String,Object> dataModel = new HashMap<>();
		//6.添加数据
		dataModel.put("name", "liuli");
		dataModel.put("message", "欢迎来到 FreeMaker !");
		dataModel.put("success", false);
		
		List<Map> goodList = new ArrayList<>();
		Map<String,Object> good1 = new HashMap<>();
		Map<String,Object> good2 = new HashMap<>();
		Map<String,Object> good3 = new HashMap<>();
		good1.put("name", "苹果");
		good1.put("price", "200");
		good2.put("name", "桔子");
		good2.put("price", "100");
		good3.put("name", "香蕉");
		good3.put("price", "500");
		goodList.add(good1);
		goodList.add(good2);
		goodList.add(good3);
		
		dataModel.put("goodList", goodList);
		dataModel.put("today", new Date());
		dataModel.put("point", 12545821);
		Writer out = new FileWriter("E:\\github\\pingyougou\\dome\\dubbo\\freemakerDome\\src\\main\\resources\\page\\yjgm.html");
		//7.生成文件
		template.process(dataModel, out);
		//8.关闭输出流
		out.close();
		System.out.println("==成功==");
	}
}
