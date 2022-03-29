package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class DbServiceTestSuite {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository repository;

    @Test
    public void testSaveTask(){
        //Given
        Task task = Task.builder().title("Title").content("Content").build();

        //When
        dbService.saveTask(task);

        //Then
        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testGetAllTasks(){
        //Given
        Task task = Task.builder().title("Title").content("Content").build();
        Task task2 = Task.builder().title("Title2").content("Content2").build();
        repository.save(task);
        repository.save(task2);

        //When
        List<Task> taskList = dbService.getAllTasks();

        //Then
        assertEquals(2, taskList.size());
    }

    @Test
    public void testGetTask(){
        //Given
        Task task = Task.builder().title("Title").content("Content").build();
        repository.save(task);
        Long id = task.getId();

        //When
        Optional<Task> foundTask = dbService.getTask(id);

        //Then
        assertEquals("Title", foundTask.get().getTitle());
        assertEquals("Content", foundTask.get().getContent());
    }

    @Test
    public void testDeleteTask(){
        //Given
        Task task = Task.builder().title("Title").content("Content").build();
        Task task2 = Task.builder().title("Title2").content("Content2").build();
        repository.save(task);
        repository.save(task2);
        Long id = task.getId();

        //When
        dbService.deleteTask(id);

        //Then
        assertEquals(1, repository.findAll().size());
    }
}
