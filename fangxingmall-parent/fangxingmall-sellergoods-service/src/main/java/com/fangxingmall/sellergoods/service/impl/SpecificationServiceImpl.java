package com.fangxingmall.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.fangxingmall.mapper.TbSpecificationMapper;
import com.fangxingmall.mapper.TbSpecificationOptionMapper;
import com.fangxingmall.pojo.TbSpecification;
import com.fangxingmall.pojo.TbSpecificationExample;
import com.fangxingmall.pojo.TbSpecificationExample.Criteria;
import com.fangxingmall.pojo.TbSpecificationOption;
import com.fangxingmall.pojo.TbSpecificationOptionExample;
import com.fangxingmall.pojogroup.Specification;
import com.fangxingmall.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.insert(tbSpecification);
		
		List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptionList();
		for(TbSpecificationOption option:specificationOptions){
			option.setSpecId(tbSpecification.getId());
			specificationOptionMapper.insert(option);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		
		
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(tbSpecification);
		
		//先删除
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.fangxingmall.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		specificationOptionMapper.deleteByExample(example);
		
		List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptionList();
		for(TbSpecificationOption option:specificationOptions){
			option.setSpecId(tbSpecification.getId());
			specificationOptionMapper.insert(option);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		Specification specification = new Specification();
		
		specification.setSpecification(specificationMapper.selectByPrimaryKey(id));
		
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.fangxingmall.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		specification.setSpecificationOptionList(specificationOptionMapper.selectByExample(example));
		
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//删除规格表
			specificationMapper.deleteByPrimaryKey(id);
			//删除规格选项表
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.fangxingmall.pojo.TbSpecificationOptionExample.Criteria Criteria = example.createCriteria();
			Criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example );
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> selectOptionList() {
			// TODO Auto-generated method stub
			return specificationMapper.selectOptionList();
		}
	
}
