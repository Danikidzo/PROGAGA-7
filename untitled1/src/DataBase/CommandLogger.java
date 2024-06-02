package DataBase;

import java.util.ArrayDeque;

public class CommandLogger {
    ArrayDeque<String> log = new ArrayDeque<>();

    public void Add(String command) {
        if (this.log.size() == 6)
            this.log.poll();
        this.log.add(command);
    }

    public String toString() {
        StringBuilder report = new StringBuilder();
        ArrayDeque<String> temp = this.log.clone();
        while (!temp.isEmpty())
            report.append(temp.poll()).append("\n");
        return report.toString();
    }
}