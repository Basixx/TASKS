package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(DbService service, TaskMapper taskMapper){
        this.service = service;
        this.taskMapper = taskMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "getTask")
    public TaskDto getTask(Long taskId){
        Task task = service.getTaskByid(taskId);
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping(value = "deleteTask")
    public void deleteTask(Long taskId){

    }
    @PutMapping(value = "updateTask")
    public TaskDto updateTask(TaskDto task){
        return new TaskDto(1L,"Edited Test title", "Test_content_2");
    }

    @PostMapping(value = "createTask")
    public void createTask(TaskDto task){

    }
}
