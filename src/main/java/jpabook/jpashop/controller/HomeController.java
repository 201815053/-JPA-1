package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@Slf4j //롬복으로 Logger log = LoggerFactory.getLogger(getClass());와 같음 - 로그 뽑는 코드
public class HomeController {

    @RequestMapping("/") //홈페이지, 메인화면
    public String home() {
        log.info("home controller");
        return "home";
    }
}
