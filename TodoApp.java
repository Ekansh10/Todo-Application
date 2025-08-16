import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TodoApp {
    private DefaultListModel<String> tasks;
    private JList<String> taskList;
    private JTextField input;

    private static final String SAVE_FILE = System.getProperty("user.home") + "/.todo_tasks.txt";

    public TodoApp() {
        JFrame frame = new JFrame("My ToDo App");
        tasks = new DefaultListModel<>();
        taskList = new JList<>(tasks);
        input = new JTextField();

        // Load saved tasks
        loadTasks();

        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");

        addBtn.addActionListener(e -> {
            String task = input.getText().trim();
            if (!task.isEmpty()) {
                tasks.addElement(task);
                input.setText("");
                saveTasks();
            }
        });

        removeBtn.addActionListener(e -> {
            int idx = taskList.getSelectedIndex();
            if (idx != -1) {
                tasks.remove(idx);
                saveTasks();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(input, BorderLayout.CENTER);
        panel.add(addBtn, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(removeBtn, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void saveTasks() {
        try (PrintWriter out = new PrintWriter(new FileWriter(SAVE_FILE))) {
            for (int i = 0; i < tasks.size(); i++) {
                out.println(tasks.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        File f = new File(SAVE_FILE);
        if (f.exists()) {
            try (Scanner sc = new Scanner(f)) {
                while (sc.hasNextLine()) {
                    tasks.addElement(sc.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoApp::new);
    }
}
