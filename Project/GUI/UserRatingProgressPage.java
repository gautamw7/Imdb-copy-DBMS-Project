package Project.GUI;

import javax.swing.*;
import Project.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRatingProgressPage extends JFrame {
    private DatabaseConnection dbConnection;
    private JLabel RatingLabel, UserProgressLabel, TotalEpisodes, TotalEpisodesValue, TitleLabel;
    private JComboBox<Integer> userProgressDropdown, ratingDropdown;
    private String animeTitle;
    private int[] userData;

    private JButton SubmitButton;
    UserRatingProgressPage(String Title) {
        animeTitle = Title;
        userData = getDatabaseData(animeTitle);

        setTitle(Title); // Set the frame title to the anime title
        setLayout(null);

        TitleLabel = new JLabel(Title);
        TitleLabel.setBounds(10, 10, 300, 20); // Adjust the bounds as needed

        RatingLabel = new JLabel("Rating:");
        RatingLabel.setBounds(10, 40, 60, 20); // Adjust the bounds as needed

        UserProgressLabel = new JLabel("User Progress:");
        UserProgressLabel.setBounds(10, 70, 100, 20); // Adjust the bounds as needed

        TotalEpisodes = new JLabel("Total Episodes:");
        TotalEpisodes.setBounds(10, 100, 100, 20); // Adjust the bounds as needed

        TotalEpisodesValue = new JLabel(String.valueOf(userData[1]));
        TotalEpisodesValue.setBounds(120, 100, 50, 20); // Adjust the bounds as needed

        // Create the user progress dropdown with values from 0 to total episodes
        userProgressDropdown = new JComboBox<>();
        for (int i = 0; i <= userData[1]; i++) {
            userProgressDropdown.addItem(i);
        }
        userProgressDropdown.setBounds(120, 70, 60, 20); // Adjust the bounds as needed

        // Create the rating dropdown with values from 0.0 to 10.0 in 0.1 increments
        // Create the rating dropdown with values from 0.0 to 10.0 in 0.1 increments
        ratingDropdown = new JComboBox<>();
        for (int rating = 0; rating <= 10; rating++) {
            ratingDropdown.addItem(rating);
        }
        ratingDropdown.setBounds(120, 40, 60, 20); // Adjust the bounds as needed

        // Set the initial selected values based on the database data
        userProgressDropdown.setSelectedItem((int) userData[0]);
        ratingDropdown.setSelectedItem(userData[2]);

        add(TitleLabel);
        add(RatingLabel);
        add(UserProgressLabel);
        add(TotalEpisodes);
        add(TotalEpisodesValue);
        add(userProgressDropdown);
        add(ratingDropdown);

        // Submit Button
        SubmitButton = new JButton("Submit");
        add(SubmitButton);
        SubmitButton.addActionListener(e -> {
            int userProgressDropdownData = (int) userProgressDropdown.getSelectedItem();
            int ratingData = (int) ratingDropdown.getSelectedItem();
            insertData(animeTitle, userProgressDropdownData, ratingData);
        });
        SubmitButton.setBounds(10, 130, 100, 30);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200); // Adjust the frame size as needed
        setVisible(true);
    }

    private void insertData(String title, Integer userProgressDropdownData, Integer ratingData) {
        openConnection();
        String updateQuery = "UPDATE ANIME SET User_Progress = ?, Rating = ? WHERE Title = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, userProgressDropdownData);
            preparedStatement.setInt(2, ratingData);
            preparedStatement.setString(3, title);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update was successful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private int[] getDatabaseData(String animeTitle) {
        int[] data = new int[3];

        openConnection();
        String query = "SELECT User_Progress, Total_Progress, Rating FROM anime WHERE Title = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query)) {
                preparedStatement.setString(1, animeTitle); // Set the anime title as a parameter
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int userProgress = resultSet.getInt("User_Progress");
                    int totalProgress = resultSet.getInt("Total_Progress");
                    int rating = resultSet.getInt("Rating");
                    data[0] = userProgress;
                    data[1] = totalProgress;
                    data[2] = rating;
                    // Now you can use userProgress, totalProgress, and rating as needed
                }
        }catch (SQLException e) {
                e.printStackTrace();
        }finally {
            closeDatabaseConnection();
        }
        return data;
    }

    private void openConnection(){
        dbConnection = new DatabaseConnection();
    }
    private void closeDatabaseConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}
