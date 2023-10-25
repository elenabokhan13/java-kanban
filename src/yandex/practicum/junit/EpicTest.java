package yandex.practicum.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yandex.practicum.service.InMemoryTaskManager;
import yandex.practicum.service.Managers;
import yandex.practicum.tasks.Epic;
import yandex.practicum.tasks.Status;
import yandex.practicum.tasks.Subtask;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    private Epic epic1 = new Epic("epic1", "Description epic1");
    private Subtask subtask1 = new Subtask("sub task1", "Description sub task1", "55",
            "19.05.2023_12:00", 1);
    private Subtask subtask2 = new Subtask("sub task2", "Description sub task2", "25",
            "20.05.2023_12:00", 1);
    private Subtask subtask3 = new Subtask("sub task3", "Description sub task3", "10",
            "21.05.2023_12:00", 1);
    private InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();


    @Test
    public void epicEmpty() {
        inMemoryTaskManager.createNewEpic(epic1);

        assertEquals(Status.NEW, inMemoryTaskManager.getEpicById(1).getStatus());
    }

    @Test
    public void epicAllSubtasksNew() {
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        assertEquals(Status.NEW, inMemoryTaskManager.getEpicById(1).getStatus());
    }

    @Test
    public void epicAllSubtasksDone() {
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);
        inMemoryTaskManager.updateSubtask(subtask3);

        assertEquals(Status.DONE, inMemoryTaskManager.getEpicById(1).getStatus());
    }

    @Test
    public void epicSubtasksNewAndDone() {
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        subtask1.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subtask1);

        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpicById(1).getStatus());
    }

    @Test
    public void epicAllSubtasksInProgress() {
        inMemoryTaskManager.createNewEpic(epic1);
        inMemoryTaskManager.createNewSubtask(subtask1);
        inMemoryTaskManager.createNewSubtask(subtask2);
        inMemoryTaskManager.createNewSubtask(subtask3);

        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.IN_PROGRESS);

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);
        inMemoryTaskManager.updateSubtask(subtask3);

        assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.getEpicById(1).getStatus());
    }
}