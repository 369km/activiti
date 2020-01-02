package com.foo.activiti.controller;

import com.foo.activiti.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    /**
     * 部署流程图
     *
     * @return 流程实例Id
     */
    @GetMapping
    public String deploy() {
        return expensesService.deploy();
    }

    /**
     * @param processInstanceId 流程实例Id
     * @param money 申请报销金额
     */
    @GetMapping("/apply/{processInstanceId}/{money}")
    public void apply(@PathVariable String processInstanceId, @PathVariable Integer money) {
        expensesService.apply(processInstanceId, money);
    }


}
