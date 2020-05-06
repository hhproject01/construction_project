package edu.hhu.construction.controller;

import edu.hhu.construction.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/index")
public class IndexController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/to_index")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);

        // 手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("index", ctx);
        return html;
    }
}
