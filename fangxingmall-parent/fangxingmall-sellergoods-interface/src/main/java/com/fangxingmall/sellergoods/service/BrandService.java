package com.fangxingmall.sellergoods.service;

import java.util.List;

import com.fangxingmall.pojo.TbBrand;

import entity.PageResult;

public interface BrandService {
	//查询所有
	public List<TbBrand> findAll();
	//分页
	public PageResult findPage(int pageNum,int pageSize);
	//增加品牌
	public void add(TbBrand brand);
	
}
