package com.fangxingmall.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.fangxingmall.pojo.TbBrand;

import entity.PageResult;

public interface BrandService {
	//查询所有
	public List<TbBrand> findAll();
	//分页
	public PageResult findPage(int pageNum,int pageSize);
	//增加品牌
	public void add(TbBrand brand);
	//根据Id查询实体 
	public TbBrand findOneById(Long id);
	//更新 
	public void update(TbBrand brand);
	//删除
	public void delete(Long[] ids);
	//按条件查询
	public PageResult findPage(TbBrand brand,int pageNum,int pageSize);
	//品牌下拉框数据
	public List<Map> selectOptionList();
}
