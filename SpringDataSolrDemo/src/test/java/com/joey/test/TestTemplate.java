package com.joey.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joey.pojo.TbItem;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext.xml")
public class TestTemplate {
	@Autowired
	private SolrTemplate solrTemplate;
	
	@Test
	public void testAdd(){
		TbItem item=new TbItem();
		item.setId(1L);
		item.setBrand("华为");
		//item.setCategory("手机1");
		item.setGoodsId(10L);
		item.setSeller("华为2号专卖店");
		item.setTitle("华为Mate9");
		item.setPrice(new BigDecimal(22000));		
		solrTemplate.saveBean(item);
		solrTemplate.commit();
	}
	@Test
	public void testFindOne(){
		TbItem item = solrTemplate.getById(1, TbItem.class);
		System.out.println(item.getTitle());
	}
	@Test
	public void testDelete(){
		solrTemplate.deleteById("1");
		solrTemplate.commit();
	}
	@Test
	public void testAddList(){
		List<TbItem> list=new ArrayList();
		
		for(int i=0;i<100;i++){
			TbItem item=new TbItem();
			item.setId(i+1L);
			item.setBrand("华为");
			//item.setCategory("手机");
			item.setGoodsId(1L);
			item.setSeller("华为2号专卖店");
			item.setTitle("华为Mate"+i);
			item.setPrice(new BigDecimal(2000+i));	
			list.add(item);
		}
		
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}

	
	@Test
	public void testPageQuery(){
		Query query=new SimpleQuery("*:*");
		query.setOffset(20);//开始索引（默认0）
		query.setRows(20);//每页记录数(默认10)
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		System.out.println("总记录数："+page.getTotalElements());
		
		List<TbItem> list = page.getContent();
		showList(list);
	}	
	//显示记录数据
	private void showList(List<TbItem> list){		
		for(TbItem item:list){
			System.out.println(item.getTitle() +"，价格："+item.getPrice());
		}		
	}

	
	@Test
	public void testPageQueryMutil(){	
		Query query=new SimpleQuery("*:*");
		Criteria criteria=new Criteria("item_title").contains("2");
		criteria=criteria.and("item_title").contains("5");		
		query.addCriteria(criteria);
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		System.out.println("总记录数："+page.getTotalElements());
		List<TbItem> list = page.getContent();
		showList(list);
	}

	@Test
	public void testDeleteAll(){
		Query query=new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
}
