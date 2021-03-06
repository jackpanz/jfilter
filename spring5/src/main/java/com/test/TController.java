package com.test;

import com.github.jackpanz.json.annotation.JFilter;
import com.github.jackpanz.json.annotation.JFilters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Controller
@RestController
@RequestMapping
public class TController {

    @ResponseBody
    @RequestMapping(value = "/userNj")
    public User userNj() {
        return getUser();
    }

    @JFilter(clazz = User.class, property = "id,status")
    @ResponseBody
    @RequestMapping(value = "/user")
    public User user() {
        return getUser();
    }

    @JFilter(clazz = User.class, property = "id,status")
    @ResponseBody
    @RequestMapping("users")
    public List users() {
        return new ArrayList() {{
            add(getUser());
            add(getUser());
            add(getUser());
            add(getUser());
        }};
    }

    @JFilter(clazz = User.class, property = "id,status")
    @ResponseBody
    @RequestMapping("map")
    public Map map() {
        return getData();
    }

    @JFilters({
            @JFilter(clazz = User.class, property = "id,status")
            , @JFilter(clazz = Admin.class, property = "password,create_date")
    })
    @ResponseBody
    @RequestMapping("maps")
    public List maps() {
        return new ArrayList() {{
            add(getData());
            add(getData());
            add(getData());
            add(getData());
        }};
    }

    public static Map getData() {
        Map data = new HashMap();
        data.put("user", getUser());
        data.put("admin", getAdmin());
        return data;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1);
        user.setName("user");
        user.setPassword("123456");
        user.setEmail("user@mm.com");
        user.setStatus(1);
        user.setCreate_date(new Date());
        return user;
    }

    public static Admin getAdmin() {
        Admin admin = new Admin();
        admin.setId(1);
        admin.setName("king");
        admin.setPassword("123456");
        admin.setEmail("admin@mm.com");
        admin.setStatus(1);
        admin.setCreate_date(new Date());
        return admin;
    }
}
