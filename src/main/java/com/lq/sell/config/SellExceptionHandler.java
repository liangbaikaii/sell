package com.lq.sell.config;

import com.lq.sell.sellException.AuthException;
import com.lq.sell.sellException.SellException;
import com.lq.sell.sellException.SkillException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class SellExceptionHandler {

    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAuth(AuthException e) {
        log.error("AuthException" + e.toString());
        return new ModelAndView("user/login");
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ModelAndView handle(SellException e) {
        log.error("SellException" + e.toString());
        return new ModelAndView("order/list" );
    }


    @ExceptionHandler(value = SkillException.class)
    @ResponseBody
    public String handle(SkillException e) {
        log.error("SellException" + e.toString());
        return new String(e.getMessage() );
    }
}
