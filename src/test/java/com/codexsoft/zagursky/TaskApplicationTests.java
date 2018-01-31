package com.codexsoft.zagursky;

import com.codexsoft.zagursky.repository.TaskRepository;
import com.codexsoft.zagursky.service.ProjectService;
import com.codexsoft.zagursky.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskApplicationTests {

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void contextLoads(){
    }

}
