package edu.hhu.construction.service;

import edu.hhu.construction.dao.UserDao;
import edu.hhu.construction.domain.User;
import edu.hhu.construction.exception.GlobalException;
import edu.hhu.construction.result.CodeMsg;
import edu.hhu.construction.util.MD5Util;
import edu.hhu.construction.util.UUIDUtil;
import edu.hhu.construction.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

	public static final String COOKI_NAME_TOKEN = "token";
	
	@Autowired
	UserDao userDao;

	public String login(HttpServletResponse response, LoginVo loginVo){
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
		//登录成功
		//生成cookie  UUIDUtil
		String token = UUIDUtil.uuid(); //token对应user

		//将token写入redis
		//addCookie(response, token, user);

		return token;
	}

	//从DB取值
	public User getById(long id) {
		return userDao.getById(id);
	}

	//从redis取值
/*	public User getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		//取出数据
		User user = redisService.get(UserKey.token, token, User.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}*/
	
/*	private void addCookie(HttpServletResponse response, String token, User user) {
		//将token信息写入redis
		redisService.set(UserKey.token, token, user);  //(prefix, key, value)
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);  //(name, value)
		cookie.setMaxAge(UserKey.token.expireSeconds());
		cookie.setPath("/"); //在webapp文件夹下的所有应用共享cookie
		response.addCookie(cookie);
	}*/

}
