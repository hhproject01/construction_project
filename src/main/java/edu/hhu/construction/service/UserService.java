package edu.hhu.construction.service;

import edu.hhu.construction.mapper.UserMapper;
import edu.hhu.construction.bean.User;
import edu.hhu.construction.exception.GlobalException;
import edu.hhu.construction.result.CodeMsg;
import edu.hhu.construction.util.MD5Util;
import edu.hhu.construction.util.UUIDUtil;
import edu.hhu.construction.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class UserService {

	public static final String COOKI_NAME_TOKEN = "token";
	public static final int TOKEN_EXPIRE = 3*24*3600;
	
	@Autowired
	UserMapper userMapper;

	public String login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo){
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		User user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//获取DB的密码 进行验证
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);

		//判断计算出来的password 和 DBpassword 是否一样
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}

/*
		try {
			response.sendRedirect("http://www.yanstudy.com");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//登录成功
		//生成cookie  UUIDUtil
		String token = UUIDUtil.uuid(); //token对应user
		addCookie(response, token);
		HttpSession session = request.getSession();
		session.setAttribute(token, user);
		return token;
	}

	//从DB取值
	public User getById(long id) {
		return userMapper.getById(id);
	}

	// 根据token获取用户信息
	public User getByToken(HttpServletRequest request, HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		//取出数据
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(token);;
		//延长有效期
		if(user != null) {
			addCookie(response, token);
		}
		return user;
	}
	
	private void addCookie(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(TOKEN_EXPIRE);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
