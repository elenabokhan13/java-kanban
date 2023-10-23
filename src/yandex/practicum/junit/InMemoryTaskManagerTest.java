package yandex.practicum.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.service.InMemoryTaskManager;
import yandex.practicum.service.Managers;

public class InMemoryTaskManagerTest extends TaskManagerTest {

    @BeforeEach
    public void createManager() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    @Override
    public void createNewTaskTest() {
        super.createNewTaskTest();
    }

    @Test
    @Override
    public void getTasksTest() {
        super.getTasksTest();
    }

    @Test
    @Override
    public void getPrioritizedTasksTest() {
        super.getPrioritizedTasksTest();
    }

    @Test
    @Override
    public void deleteAllTasksTest() {
        super.deleteAllTasksTest();
    }

    @Test
    @Override
    public void getTaskByIdTest() {
        super.getTaskByIdTest();
    }

    @Test
    @Override
    public void deleteTaskTest() {
        super.deleteTaskTest();
    }

    @Test
    @Override
    public void updateTaskTest() {
        super.updateTaskTest();
    }

    @Test
    @Override
    public void createNewEpicTest() {
        super.createNewEpicTest();
    }

    @Test
    @Override
    public void getEpicsTest() {
        super.getEpicsTest();
    }

    @Test
    @Override
    public void deleteAllEpicsTest() {
        super.deleteAllEpicsTest();
    }

    @Test
    @Override
    public void getEpicByIdTest() {
        super.getEpicByIdTest();
    }

    @Test
    @Override
    public void deleteEpicTest() {
        super.deleteEpicTest();
    }

    @Test
    @Override
    public void updateEpicTest() {
        super.updateEpicTest();
    }

    @Test
    @Override
    public void createNewSubtaskTest() {
        super.createNewSubtaskTest();
    }

    @Test
    @Override
    public void getSubtasksTest() {
        super.getSubtasksTest();
    }

    @Test
    @Override
    public void deleteAllSubtasksTest() {
        super.deleteAllSubtasksTest();
    }

    @Test
    @Override
    public void getSubtaskByIdTest() {
        super.getSubtaskByIdTest();
    }

    @Test
    @Override
    public void deleteSubtaskTest() {
        super.deleteSubtaskTest();
    }

    @Test
    @Override
    public void updateSubtaskTest() {
        super.updateSubtaskTest();
    }
}
