package com.fangxingmall.search.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxingmall.pojo.TbItem;
import com.fangxingmall.search.service.ItemSearchService;

@Service
public class ItemSeachServiceImpl  implements ItemSearchService{

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Override
	public Map search(Map searchMap) {
		Map map=new HashMap();
		
		Query query = new SimpleQuery();
		Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		solrTemplate.queryForPage(query, TbItem.class);
		
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		map.put("rows",page.getContent());
		
		return map;
	}

}
