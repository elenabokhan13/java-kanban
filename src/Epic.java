import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtasksList;

    public Epic(String name, String description, String status, ArrayList<Integer> subtasksList) {
        super(name, description, status);
        this.subtasksList = subtasksList;
    }

    @Override
    public String toString() {
        return "Тип: эпик\nНомер задачи:" + id + "\n" + "Название задачи: " + name + "\n"
                + "Описание задачи: " + description + "\n" + "Статус: " + status;
    }

}
