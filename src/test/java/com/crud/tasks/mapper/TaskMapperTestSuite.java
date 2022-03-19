package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest nie potrzebujemy springa, bo to jest test jednostkowy
public class TaskMapperTestSuite {
   // @Autowired analogicznie
   // private TaskMapper taskMapper;

    private TaskMapper taskMapper;

    @Test
    public void testMapToTask(){
        //Given
        TaskDto taskDto = new TaskDto(1L, "taskTitle", "taskContent");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(1L, task.getId());
        assertEquals("taskTitle", task.getTitle());
        assertEquals("taskContent", task.getContent());
    }

    @Test
    public void testMapToTaskDto(){
        //Given
        Task task = new Task(2L, "TaskTitle", "TaskContent");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(2L, taskDto.getId());
        assertEquals("TaskTitle", taskDto.getTitle());
        assertEquals("TaskContent", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList(){
        //Given
        Task task = new Task(3L, "TaskTitle", "TaskContent");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(1, taskDtoList.size());
        assertEquals(3L, taskDtoList.get(0).getId());
        assertEquals("TaskTitle", taskDtoList.get(0).getTitle());
        assertEquals("TaskContent", taskDtoList.get(0).getContent());
    }
}
