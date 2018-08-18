package com.lq.sell.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 模拟秒杀接口
 */
@RestController
@RequestMapping("/skill")
public class SkillController {


    @Autowired
    private SkillService skillService;

    @GetMapping("/{id}")
    public String get(@PathVariable("id") String productId) {
        skillService.skill(productId);
        String query = skillService.query(productId);
        return query;
    }
}
