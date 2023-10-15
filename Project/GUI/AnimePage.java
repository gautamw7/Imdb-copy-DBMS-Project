package Project.GUI;

import javax.swing.*;
import Project.DatabaseConnection;
import Project.GUI.UserRatingProgressPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimePage extends JFrame {

    private List<String> Title;
    private List<JButton> Buttons;
    private JPanel buttonPanel;
    private DatabaseConnection dbConnection;

    public AnimePage() {
        setTitle("Anime Titles");
        setSize(1500, 1500);
     //   setLayout(null);

        // Get the Title
        Title = new ArrayList<>();
        Title.addAll(getEntries());

        // Add the components to the frame
        buttonPanel = new JPanel();  // Create the buttonPanel here
        add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        // List for the Button
        ButtonMaker(Title);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ActionListener buttonClickListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String animeTitle = e.getActionCommand(); // Get the anime title from the ActionCommand
            // Now you can use 'animeTitle' to determine which button was clicked
            UserRatingProgressPage page = new UserRatingProgressPage(animeTitle);
        }
    };
    private void ButtonMaker(List<String> title) {
        Buttons = new ArrayList<>();
        // Make buttons for all the titles
        for (String entry : Title) {
            JButton button = new JButton(entry);
            Buttons.add(button);
            button.addActionListener(buttonClickListener); // Add the common ActionListener
        }

        // Add all the buttons to the screen
        for (JButton Jbutton : Buttons) {
            buttonPanel.add(Jbutton);
        }
    }

    private List<String> getEntries() {
        List<String> entries = new ArrayList<>();
        openDatabaseConnection();
        try {
            Connection connection = dbConnection.getConnection();
            String query = "SELECT Title FROM anime";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String entry = resultSet.getString("Title");
                    entries.add(entry);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception here, e.g., show an error message to the user
        } finally {
            closeDatabaseConnection();
        }

        return entries;
    }

    private void openDatabaseConnection() {
        dbConnection = new DatabaseConnection();
    }

    private void closeDatabaseConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AnimePage();
        });
    }
}
