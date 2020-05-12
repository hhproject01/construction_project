package edu.hhu.construction.service;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet1")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.创建一个对象，就是在内存中的一个图片（验证码图片对象）：
        //BufferedImage对象：传参图片的宽、高、类型（宽和高一般提前设置为一个变量）
        int width = 90;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //2.美化图片
        //2.1 填充背景色
        // 获取画笔对象，getGraphics()方法；
        Graphics g = image.getGraphics();
        // 设置画笔颜色setColor(),传参颜色对象的变量（Color.颜色）
        g.setColor(Color.pink);
        g.fillRect(0,0,width,height);
        //2.2 画边框
        // 设置画笔颜色；
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,height-1);// 画线方法drawRect()，传参边框的起点坐标x和y，以及边框的宽度width高度height
        //2.3 生成验证码字符串
        // 定义一个源字符串String对象
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random ran = new Random();//生成随机角标（Random对象的nextInt()方法 传参随机数的最大值为字符串的长度
       StringBuilder sb = new StringBuilder();

        for (int i = 1;i <= 4;i++){
            int index = ran.nextInt(str.length());//循环生成随机角标对应的字符
            char ch = str.charAt(index);
            sb.append(ch);
            g.drawString(ch+"",width/5*i,height/2);//把生成的字符画到图片上:drawString()方法，传参字符对象和字符在图片中的位置坐标

        }

        String checkCode_session = sb.toString();
        request.getSession().setAttribute("checkCode_session",checkCode_session);

        //2.4画干扰线：drawLine()方法，传参干扰线的起始坐标和结束坐标
        g.setColor(Color.green);//设置画笔颜色
        for (int j = 0; j < 8; j++) {//循环生成随机坐标点，并画线到图片
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //3.把图片输出到页面显示:ImageIO对象的write()方法，传参图片对象，图片类型，输出流对象
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}