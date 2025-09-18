package org.example;

import java.util.*;
 class TodoApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            System.out.print("\nEnter command: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("a")) {
                System.out.print("Message: ");
                String msg = sc.nextLine();
                System.out.print("Date (dd/MM/yyyy): ");
                String date = sc.nextLine();
                manager.addTask(msg, date);

            }
            else if (input.startsWith("v")) {
                String arg = input.substring(1);
                if (arg.matches("\\d+")) {
                    manager.viewById(Long.parseLong(arg));
                } else {
                    manager.viewByDate(arg);
                }

            }
            else if (input.startsWith("d")) {
                String arg = input.substring(1);
                if (arg.matches("\\d+")) {
                    manager.deleteTask(Long.parseLong(arg));
                } else {
                    System.out.println("Invalid delete command.");
                }

            }
            else {
                System.out.println("Invalid command! Use 'a', 'v<id>', 'v<date>', or 'd<id>'.");
            }
        }
    }
}
class Task {
    private static long counter = 100;
    private long id;
    private String message;
    private String date;

    public Task(String message, String date) {
        this.id = counter++;
        this.message = message;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + " | Message: " + message + " | Date: " + date;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;
        Task t = (Task) obj;
        return this.id == t.id;
    }
}


class TaskManager {
    private Set<Task> tasks = new HashSet<>();
    private Map<Long, Task> idMap = new HashMap<>();
    private Map<String, List<Task>> dateMap = new HashMap<>();


    public void addTask(String message, String date) {
        Task task = new Task(message, date);
        tasks.add(task);
        idMap.put(task.getId(), task);

        dateMap.putIfAbsent(date, new ArrayList<>());
        dateMap.get(date).add(task);

        System.out.println("Task Added!");
        showAllTasks();
    }


    public void deleteTask(long id) {
        Task task = idMap.get(id);
        if (task != null) {
            tasks.remove(task);
            idMap.remove(id);
            dateMap.get(task.getDate()).remove(task);
            if (dateMap.get(task.getDate()).isEmpty()) {
                dateMap.remove(task.getDate());
            }
            System.out.println("Task Deleted!");
        } else {
            System.out.println("No task found with ID " + id);
        }
        showAllTasks();
    }


    public void viewById(long id) {
        Task task = idMap.get(id);
        if (task != null) {
            System.out.println(task);
        } else {
            System.out.println("No task found with ID " + id);
        }
    }


    public void viewByDate(String date) {
        List<Task> list = dateMap.get(date);
        if (list != null && !list.isEmpty()) {
            for (Task t : list) {
                System.out.println(t);
            }
        } else {
            System.out.println("No tasks found on date " + date);
        }
    }


    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("All Tasks:");
            for (Task t : tasks) {
                System.out.println(t);
            }
        }
    }
}


