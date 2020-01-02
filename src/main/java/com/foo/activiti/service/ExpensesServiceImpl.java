package com.foo.activiti.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public String deploy() {
        //"expenses"对应于expenses.bpmn20.xml中<process id="expenses"> ,id的值
        return runtimeService.startProcessInstanceByKey("expenses").getId();
    }

    @Override
    public void apply(String processInstanceId,Integer money) {
        String taskId = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult().getId();
        //"money"对应于expenses.bpmn20.xml中<conditionExpression>的value:"money"
        taskService.setVariable(taskId,"money",money);
        taskService.complete(taskId);
    }

    @Override
    public void image(String processInstanceId) {
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        try {
            InputStream inputStream = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            File file = new File("expenses.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ImageIO.write(bufferedImage, "png", fileOutputStream);
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
