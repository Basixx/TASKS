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
        for (Long i = 1L; i<=3; i++){
            tasks.add(new Task(i, "Title"+i, "Content"+i));
            tasksDto.add(new TaskDto(i, "Title"+i, "Content"+i));
        }
        given(taskMapper.mapToTaskDtoList(tasks)).willReturn(tasksDto);
        given(service.getAllTasks()).willReturn(tasks);
        //When && Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Content1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Title2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("Content2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("Title3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].content", Matchers.is("Content3")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");
        given(taskMapper.mapToTaskDto(task)).willReturn(taskDto);
        given(service.getTask(1L)).willReturn(Optional.of(task));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception{
        //Given
        doNothing().when(service).deleteTask(1L);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/task/deleteTask?taskId=1")
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
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("UpdatedTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("UpdatedContent")));
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
                    .post("/v1/task/createTask")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}