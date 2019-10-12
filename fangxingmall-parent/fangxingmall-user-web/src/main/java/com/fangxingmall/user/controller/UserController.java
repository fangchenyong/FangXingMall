package com.fangxingmall.user.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxingmall.pojo.TbUser;
import com.fangxingmall.user.service.UserService;

import entity.PageResult;
import entity.Result;
import util.RegexFormatCheckUtils;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserService userService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbUser> findAll(){			
		return userService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return userService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbUser user,String smscode){
		
		//校验验证码是否正确
		boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), smscode);
		System.out.println("smscode:"+smscode);
		if(!checkSmsCode){
			return new Result(false, "验证码不正确！");
		}
		try {
			System.out.println("user:"+user.toString());
			userService.add(user);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@RequestMapping("/addByEmail")
	public Result addByEmail(@RequestBody TbUser user,String emailcode){
		if(emailcode==""||emailcode==null||emailcode=="undefined"){
			return new Result(false, "请填写验证码！");
		}
		//校验验证码是否正确
		boolean checkEmailCode = userService.checkEmailCode(user.getEmail(), emailcode);
		System.out.println("emailcode:"+emailcode);
		if(!checkEmailCode){
			return new Result(false, "验证码不正确！");
		}
		try {
			System.out.println("user:"+user.toString());
			userService.add(user);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbUser user){
		try {
			userService.update(user);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbUser findOne(Long id){
		return userService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			userService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbUser user, int page, int rows  ){
		return userService.findPage(user, page, rows);		
	}
	
	@RequestMapping("/sendCode")
	public Result sendCode(String phone){
		
		if(!RegexFormatCheckUtils.isPhoneLegal(phone)){
			return new Result(false, "手机格式不正确");
		}
		
		try {
			userService.createSmsCode(phone);
			return new Result(true, "验证码发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "验证码发送失败");
		}
	}
	
	@RequestMapping("/sendEmailCode")
	public Result sendEmailCode(String email){
		
		if(!RegexFormatCheckUtils.isEmail(email)){
			return new Result(false, "邮箱格式不正确");
		}
		try {
			userService.createEmailCode(email);
			return new Result(true, "验证码发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "验证码发送失败");
		}
	}
	
}
