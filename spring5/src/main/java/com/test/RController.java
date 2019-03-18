package com.test;

import com.github.jackpanz.json.annotation.JFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RController {

    @JFilter(clazz = User.class, property = "id,status")
    @GetMapping("rmaps")
    public List maps() {
        return new ArrayList() {{
            add(TController.getData());
            add(TController.getData());
            add(TController.getData());
            add(TController.getData());
        }};
    }


}
