package yandex.practicum.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yandex.practicum.service.FileBackedTasksManager;

import java.io.File;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static yandex.practicum.service.FileBackedTasksManager.loadFromFile;

class FileBackedTasksManagerTest extends TaskManagerTest {


    @BeforeEach
    public void createManager() {
        File file = new File("data.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    @Test
    @Override
    public void createNewTaskTest() {
        final NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()
                -> taskManager.getHistory());
        super.createNewTaskTest();
        FileBackedTasksManager managerNew = loadFromFile(new File("data.csv"));
        assertEquals(managerNew.getHistory(), taskManager.getHistory());
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
        final NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()
                -> taskManager.getHistory());
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
        FileBackedTasksManager managerNew = loadFromFile(new File("data.csv"));
        assertEquals(managerNew.getHistory(), taskManager.getHistory());
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
        FileBackedTasksManager managerNew = loadFromFile(new File("data.csv"));
        assertEquals(managerNew.getHistory(), taskManager.getHistory());
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