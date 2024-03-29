package com.fangxingmall.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.fangxingmall.mapper.TbBrandMapper;
import com.fangxingmall.pojo.TbBrand;
import com.fangxingmall.pojo.TbBrandExample;
import com.fangxingmall.pojo.TbBrandExample.Criteria;
import com.fangxingmall.sellergoods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import entity.PageResult;
@Service
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		// TODO Auto-generated method stub
		return brandMapper.selectByExample(null);
	}
	
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		//mybatis分页
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		// TODO Auto-generated method stub
		brandMapper.insert(brand);
	}

	@Override
	public TbBrand findOneById(Long id) {
		// TODO Auto-generated method stub
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		// TODO Auto-generated method stub
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		for(Long id:ids){
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		TbBrandExample example = new TbBrandExample();
		Criteria criteria = example.createCriteria();
		if(brand!=null){
			if(brand.getName()!=null&&brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");				
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");				
			}
		}
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example );
		return new PageResult(page.getTotal(), page.getResult());
	}	
	
	/**
	 * 列表数据
	 */
	@Override
	public List<Map> selectOptionList() {
		return brandMapper.selectOptionList();
	}
}
