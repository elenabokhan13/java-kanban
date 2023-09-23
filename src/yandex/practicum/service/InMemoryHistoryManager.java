package yandex.practicum.service;

import yandex.practicum.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> historyList = new CustomLinkedList<>();

    @Override
    public void addTask(Task task) {
        if (task != null) {
            remove(task.getId());
            historyList.linkLast(task);
        } else {
            System.out.println("Данная задача еще не создана.");
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList.getTasks();
    }

    @Override
    public void remove(int id) {
        if (historyList.historyTasks.containsKey(id)) {
            historyList.removeNode(historyList.historyTasks.get(id));
        }
    }

    public class CustomLinkedList<E> {
        // я использовала size, чтобы выше проверять, равен размер просмотров 10 или нет, чтобы не превысить 10
        // просмотров, но раз это не нужно больше делать, то конечно убрала
        private Node<Task> tail;
        private Node<Task> head;
        private final Map<Integer, Node> historyTasks = new HashMap<>();

        public void linkLast(Task task) {
            final Node<Task> oldTail = tail;
            final Node<Task> newTail = new Node<>(oldTail, task, null);
            tail = newTail;
            if (oldTail == null) {
                head = newTail;
            } else {
                oldTail.next = newTail;
            }
            historyTasks.put(task.getId(), newTail);
        }

        public List<Task> getTasks() {
            List<Task> tasksHistory = new ArrayList<>();
            Node<Task> curNode = head;
            if (curNode == null) {
                throw new NoSuchElementException();
            }
            tasksHistory.add(curNode.data);
            while (curNode.next != null) {

                curNode = curNode.next;
                tasksHistory.add(curNode.data);
            }
            return tasksHistory;
        }

        public void removeNode(Node<Task> node) {
            Node<Task> previous = node.prev;
            Node<Task> continuous = node.next;
            if (previous != null) {
                previous.next = continuous;
            } else {
                head = continuous;
            }
            if (continuous != null) {
                continuous.prev = previous;
            } else {
                tail = previous;
            }
        }
    }
}

