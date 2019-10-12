package com.fangxingmall.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.fangxingmall.mapper.TbUserMapper;
import com.fangxingmall.pojo.TbUser;
import com.fangxingmall.pojo.TbUserExample;
import com.fangxingmall.pojo.TbUserExample.Criteria;
import com.fangxingmall.user.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import entity.PageResult;

/**
 * 服务实现层
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbUser user) {

		user.setCreated(new Date());// 用户注册时间
		user.setUpdated(new Date());// 修改时间
		user.setSourceType("1");// 注册来源，可以前端传递过来
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));// 密码加密

		userMapper.insert(user);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbUser user) {
		userMapper.updateByPrimaryKey(user);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			userMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (user != null) {
			if (user.getUsername() != null && user.getUsername().length() > 0) {
				criteria.andUsernameLike("%" + user.getUsername() + "%");
			}
			if (user.getPassword() != null && user.getPassword().length() > 0) {
				criteria.andPasswordLike("%" + user.getPassword() + "%");
			}
			if (user.getPhone() != null && user.getPhone().length() > 0) {
				criteria.andPhoneLike("%" + user.getPhone() + "%");
			}
			if (user.getEmail() != null && user.getEmail().length() > 0) {
				criteria.andEmailLike("%" + user.getEmail() + "%");
			}
			if (user.getSourceType() != null && user.getSourceType().length() > 0) {
				criteria.andSourceTypeLike("%" + user.getSourceType() + "%");
			}
			if (user.getNickName() != null && user.getNickName().length() > 0) {
				criteria.andNickNameLike("%" + user.getNickName() + "%");
			}
			if (user.getName() != null && user.getName().length() > 0) {
				criteria.andNameLike("%" + user.getName() + "%");
			}
			if (user.getStatus() != null && user.getStatus().length() > 0) {
				criteria.andStatusLike("%" + user.getStatus() + "%");
			}
			if (user.getHeadPic() != null && user.getHeadPic().length() > 0) {
				criteria.andHeadPicLike("%" + user.getHeadPic() + "%");
			}
			if (user.getQq() != null && user.getQq().length() > 0) {
				criteria.andQqLike("%" + user.getQq() + "%");
			}
			if (user.getIsMobileCheck() != null && user.getIsMobileCheck().length() > 0) {
				criteria.andIsMobileCheckLike("%" + user.getIsMobileCheck() + "%");
			}
			if (user.getIsEmailCheck() != null && user.getIsEmailCheck().length() > 0) {
				criteria.andIsEmailCheckLike("%" + user.getIsEmailCheck() + "%");
			}
			if (user.getSex() != null && user.getSex().length() > 0) {
				criteria.andSexLike("%" + user.getSex() + "%");
			}
		}
		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination smsDestination;

	@Autowired
	private Destination emailDestination;

	@Value("${template_code}")
	private String template_code;

	@Value("${sign_name}")
	private String sign_name;

	@Override
	public void createSmsCode(final String phone) {
		// 1.生成一个6位随机数（验证码）
		final String smscode = (long) (Math.random() * 1000000) + "";
		System.out.println("验证码：" + smscode);
		// 2.将验证码放入redis
		redisTemplate.boundHashOps("smscode").put(phone, smscode);
		// 3.将短信内容发送给activeMQ
		jmsTemplate.send(smsDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("mobile", phone);// 手机号
				// message.setString("template_code", template_code);//验证码
				// message.setString("sign_name", sign_name);//签名
				Map map = new HashMap();
				map.put("number", smscode);
				message.setString("param", JSON.toJSONString(map));
				return message;
			}
		});

	}

	@Override
	public boolean checkSmsCode(String phone, String code) {

		String systemcode = (String) redisTemplate.boundHashOps("smscode").get(phone);
		if (systemcode == null) {
			return false;
		}
		if (!systemcode.equals(code)) {
			return false;
		}
		return true;
	}

	@Override
	public void createEmailCode(final String email) {
		// TODO Auto-generated method stub
		// 1.生成一个6位随机数（验证码）
		final String emailcode = (long) (Math.random() * 1000000) + "";
		System.out.println("验证码：" + emailcode);
		// 2.将验证码放入redis
		redisTemplate.boundHashOps("emailcode").put(email, emailcode);
		// 3.将邮箱内容发送给activeMQ
		jmsTemplate.send(emailDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("email", email);// 邮箱
				message.setString("checkCode", emailcode);// 邮箱
				// message.setString("template_code", template_code);//验证码
				// message.setString("sign_name", sign_name);//签名
				//Map map = new HashMap();
				//map.put("number", emailcode);
				//message.setString("param", JSON.toJSONString(map));
				return message;
			}
		});
	}

	@Override
	public boolean checkEmailCode(String email, String code) {
		String systemcode = (String) redisTemplate.boundHashOps("emailcode").get(email);
		if (systemcode == null) {
			return false;
		}
		if (!systemcode.equals(code)) {
			return false;
		}
		return true;
	}

}
