package com.example.demo.Controller;

import com.example.demo.Entity.Contack;
import com.example.demo.Dao.ContackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.beans.Customizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
public class controller {
    //初始化数据库
    private ContackRepository contackRepository;

    @Autowired
    public void setContactRepository(ContackRepository contackRepository){
        this.contackRepository = contackRepository;
    }

    private List<Contack> userList = new ArrayList<Contack>();
    private List<Contack> contackList = new ArrayList<>();
    private int id = 0;
    @RequestMapping("/login")
    public String Login(Model model){
        return "login";
    }

    @RequestMapping("/list")
    public ModelAndView Index(Model model){

        contackList = contackRepository.findAll();
        model.addAttribute("user",contackList);
        ModelAndView modelAndView = new ModelAndView("list","userModel",model);
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView Add(Model model){
        model.addAttribute("user",new Contack());
        //System.out.println("id"+id);

        id+=1;
        model.addAttribute("title","添加联系人");
        ModelAndView modelAndView = new ModelAndView("add","userModel",model);
        return modelAndView ;
    }

    @RequestMapping("/update/{curid}")
    public String Update(@PathVariable("curid") int id,Model model){
        System.out.println("更新");
        return "update";
    }

    @RequestMapping("/list/update/{curid}")
    public String ListUpdate(@PathVariable("curid") int id,Model model){
        return "update";
    }

    @PostMapping(value="/login/flag")
    public String Login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("userid") String userid,
                        @RequestParam("password") String password) throws IOException {
        if (userid.equals("123") && password.equals("123")) {
            HttpSession session = request.getSession();
            session.setAttribute("User","123");
            return "redirect:/list";
        }
        else {
            return "redirect:/login";
        }
    }

    //添加页面返回
    @PostMapping(value = "/add/post")
    public String ADD(Contack contack){
        userList.add(contack);
        contackRepository.save(contack);
        System.out.println(contack.getCurid() + "添加数据库成功");
        return "redirect:/list";
    }

    @PostMapping(value = "/list/delete/{curid}")
    public String deleteUser(@PathVariable("curid") int id){
        /*
        Iterator<Contack> iter = userList.iterator();
        while (iter.hasNext()) {
            Contack tp = iter.next();
            if (tp.getCurid() == id) {
                iter.remove();
                //System.out.println("删除成功");
                break;
            }
        }*/
        contackRepository.deleteById(id);
        return "redirect:/list";
    }

    @GetMapping(value = "/list/update/{curid}")
    public ModelAndView update(@PathVariable("curid") int id,Model model){
        //System.out.println("更新"+id);
        /*
        Iterator<Contack> iter = userList.iterator();
        while (iter.hasNext()) {
            Contack tp = iter.next();
            //System.out.println(tp.getCurid()+tp.getName());
            if (tp.getCurid() == id) {
                //System.out.println(tp.getName());
                model.addAttribute("user",tp);
                break;
            }
        }*/
        Contack up_contack = contackRepository.findById(id).orElse(null);
        model.addAttribute("user",up_contack);
        ModelAndView modelAndView = new ModelAndView("update","userModel",model);
        return modelAndView;
    }

    @PostMapping(value = "/update/post/{curid}")
    public String updatePost(@PathVariable("curid")int id, Contack contack,Model model){
        int tempid = contack.getCurid();
        //System.out.println("更新完毕"+id);
        contackRepository.save(contack);
        return "redirect:/list";
    }

    //ajax判断手机号是否相等
    @RequestMapping(value = "/add/phone")
    public @ResponseBody void phone(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String LOGIN_PHONE= request.getParameter("inputPhone");
        System.out.println("phone"+LOGIN_PHONE);
        Iterator<Contack> iter = contackList.iterator();
        int flag = 1;
        while (iter.hasNext()) {
            Contack tp = iter.next();
            if (tp.getPhone().equals(LOGIN_PHONE)) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("0");
                flag = 0;
                System.out.println("重复");
                break;
            }
        };
        if(flag==1) {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("1");
        }
    }
}
