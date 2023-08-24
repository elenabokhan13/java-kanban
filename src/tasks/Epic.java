package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "Тип: эпик\nНомер задачи: " + id + "\n" + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status;
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(ArrayList<Integer> subtasksList) {
        this.subtasksList = subtasksList;
    }
}
