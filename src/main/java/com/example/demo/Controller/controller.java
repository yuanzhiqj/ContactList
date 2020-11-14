package com.example.demo.Controller;

import com.example.demo.Entity.Contack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
public class controller {
    private List<Contack> userList = new ArrayList<Contack>();
    private int id = 0;
    @RequestMapping("/login")
    public String Login(Model model){
        return "login";
    }

    @RequestMapping("/list")
    public ModelAndView Index(Model model){
        model.addAttribute("user",userList);
        ModelAndView modelAndView = new ModelAndView("list","userModel",model);
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView Add(Model model){
        model.addAttribute("user",new Contack(id));
        System.out.println("id"+id);
        id+=1;
        model.addAttribute("title","添加联系人");
        ModelAndView modelAndView = new ModelAndView("add","userModel",model);
        return modelAndView ;
    }

    @RequestMapping("/update/{curid}")
    public String Update(@PathVariable("curid") int id,Model model){
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

    @PostMapping(value = "/add/post")
    public String ADD(Contack contack){
        userList.add(contack);
        System.out.println(contack.getCurid());
        return "redirect:/list";
    }

    @PostMapping(value = "/list/delete/{curid}")
    public String deleteUser(@PathVariable("curid") int id){
        System.out.println("删除"+id);
        /*String tmp = id;
        tmp.replace("{","");
        tmp.replace("}","");*/
        //int a = Integer.parseInt(id);
        Iterator<Contack> iter = userList.iterator();
        while (iter.hasNext()) {
            Contack tp = iter.next();
            if (tp.getCurid() == id) {
                iter.remove();
                System.out.println("删除成功");
                break;
            }
        }
        return "redirect:/list";
    }

    @GetMapping(value = "/list/update/{curid}")
    public ModelAndView update(@PathVariable("curid") int id,Model model){
        System.out.println("更新"+id);
        Iterator<Contack> iter = userList.iterator();
        while (iter.hasNext()) {
            Contack tp = iter.next();
            System.out.println(tp.getCurid()+tp.getName());
            if (tp.getCurid() == id) {
                System.out.println(tp.getName());
                model.addAttribute("user",tp);
                break;
            }
        }
        ModelAndView modelAndView = new ModelAndView("update","userModel",model);
        return modelAndView;
    }

    @PostMapping(value = "/update/post/{curid}")
    public String updatePost(@PathVariable("curid")int id, Contack contack,Model model){
        int tempid = contack.getCurid();
        System.out.println("更新完毕"+id);
        Iterator<Contack> iter = userList.iterator();

        while (iter.hasNext()) {
            Contack tp = iter.next();
            if (tp.getCurid() == tempid) {
                Collections.replaceAll(userList,tp,contack);
                break;
            }
        }
        return "redirect:/list";
    }
}
