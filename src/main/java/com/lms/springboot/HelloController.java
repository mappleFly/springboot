package com.lms.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lms
 * @PackageName: com.lms.springboot
 * @ClassName: HelloController
 * @Description: todo
 * @date 2021/2/11 12:34 上午
 */
@RestController
@RequestMapping(value ="/hello")
public class HelloController {

    @GetMapping("/name")
    public String say(@RequestParam("name") String name){
        return "hi-->"+name;
    }
}
