package edu.hhu.construction.controller;

import edu.hhu.construction.result.Result;
import edu.hhu.construction.service.UserService;
import edu.hhu.construction.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    UserService userService;

	
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());

    	//参数校验:
        //1.引入依赖spring-boot-starter-validation
        //2.参数前加注解 @Valid
        //3.LoginVo类中需要校验的参数加注解 @NotNull/@IsMobile/@Length(min=32)
        //  定义一个验证器@IsMobile validatedBy = {IsMobileValidator.class }
        //此时仍有Exception ----> 引入异常处理
        /*String mobile = loginVo.getMobile();
        String passInput = loginVo.getPassword();
        if(StringUtils.isEmpty(passInput)){//密码为空 @NotNull
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){//手机号为空  @NotNull
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){//手机号格式不对 @IsMobile
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/

    	//登录
        //seckillUserService.login(response, loginVo);
        String token = userService.login(response, loginVo);
        /*if(cm.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(cm);
        }*/

        return Result.success(token);
    }
}
