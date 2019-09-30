package com.fangxingmall.page.service.impl;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxingmall.mapper.TbGoodsDescMapper;
import com.fangxingmall.mapper.TbGoodsMapper;
import com.fangxingmall.page.service.ItemPageService;
import com.fangxingmall.pojo.TbGoods;
import com.fangxingmall.pojo.TbGoodsDesc;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Service
public class ItemPageServiceImpl implements ItemPageService{

	@Value("${pagedir}")
	private String pagedir;
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
		
	@Override
	public boolean genItemHtml(Long goodsId){				
		try {
			System.out.println("生成静态文件");
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map dataModel=new HashMap<>();			
			//1.加载商品表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);			
			//2.加载商品扩展表数据			
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);						
			Writer out=new FileWriter(pagedir+goodsId+".html");
			template.process(dataModel, out);
			out.close();
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
