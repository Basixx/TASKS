package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        List<TaskDto> tasksDto = new ArrayList<>();
        for (Long i = 0L; i<3; i++){
            tasks.add(new Task(i+1, "Title"+i, "Content"+i));
            tasksDto.add(new TaskDto(i+1, "Title"+i, "Content"+i));
        }
        given(taskMapper.mapToTaskDtoList(tasks)).willReturn(tasksDto);
        given(service.getAllTasks()).willReturn(tasks);

        //When && Then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
        matchResult(result, 1, "Title0", "Content0", "$[0]");
        matchResult(result, 2, "Title1", "Content1", "$[1]");
        matchResult(result, 3, "Title2", "Content2", "$[2]");
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");
        given(taskMapper.mapToTaskDto(task)).willReturn(taskDto);
        given(service.getTask(1L)).willReturn(Optional.of(task));

        //When & Then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(result, 1, "Title", "Content", "$");
    }

    @Test
    public void shouldDeleteTask() throws Exception{
        //Given
        doNothing().when(service).deleteTask(1L);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void shouldUpdateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "UpdatedTitle", "UpdatedContent");
        Task task = new Task(1L, "Title", "Content");
        given(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).willReturn(task);
        given(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).willReturn(taskDto);
        given(service.saveTask(ArgumentMatchers.any(Task.class))).willReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(result, 1, "UpdatedTitle", "UpdatedContent", "$");
    }

    @Test
    public void shouldCreateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");
        Task task = new Task(1L, "Title", "Content");
        given(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).willReturn(task);
        given(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).willReturn(taskDto);
        given(service.saveTask(ArgumentMatchers.any(Task.class))).willReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    private ResultActions matchResult(ResultActions resultActions, int id, String title, String content, String expression) throws Exception{
        return resultActions
                .andExpect(MockMvcResultMatchers.jsonPath(expression + ".id", Matchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath(expression + ".title", Matchers.is(title)))
                .andExpect(MockMvcResultMatchers.jsonPath(expression + ".content", Matchers.is(content)));
    }
}