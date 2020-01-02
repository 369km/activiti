package com.foo.activiti.service;

public interface ExpensesService {

    String deploy();

    void apply(String processInstanceId,Integer money);

    void image(String processInstanceId);

}
