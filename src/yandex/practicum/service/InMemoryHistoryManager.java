package yandex.practicum.service;

import yandex.practicum.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10;
    private final CustomLinkedList<Task> historyList = new CustomLinkedList<>();

    @Override
    public void addTask(Task task) {
        if (task != null) {
            remove(task.getId());
            if (historyList.size == MAX_HISTORY_SIZE) {
                historyList.removeNode(historyList.head);
            }
            historyList.linkLast(task);
        } else {
            System.out.println("Данная задача еще не создана.");
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyList.getTasks();
    }

    @Override
    public void remove(int id) {
        if (historyList.historyTasks.containsKey(id)) {
            historyList.removeNode(historyList.historyTasks.get(id));
        }
    }

    public class CustomLinkedList<E> {
        private Node<Task> tail;
        private Node<Task> head;
        private int size = 0;
        private final HashMap<Integer, Node> historyTasks = new HashMap<>();

        public void linkLast(Task task) {
            final Node<Task> oldTail = tail;
            final Node<Task> newTail = new Node<>(oldTail, task, null);
            tail = newTail;
            if (oldTail == null) {
                head = newTail;
            } else {
                oldTail.next = newTail;
            }
            size++;
            historyTasks.put(task.getId(), newTail);
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasksHistory = new ArrayList<>();
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
            size = size - 1;
        }
    }
}

