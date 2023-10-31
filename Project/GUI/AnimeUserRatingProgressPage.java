package Project.GUI;

import Project.DatabaseConnection;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 * @author lenovo
 */
public class AnimeUserRatingProgressPage extends JFrame {

    private DatabaseConnection dbConnection;
    private JLabel Titlelabel;
    private JLabel CharacterLabel1, CharacterLabel2, VoiceActor1;
    private JLabel YearLabel, RatingLabel, GenreLabel ,TotalProgress, SeasonValueLabel , UserProgressLabel;
    private JComboBox RatingBox;

    private JComboBox UserProgressBox;
    private JLabel label7;
    private JButton SubmitButton;


    public AnimeUserRatingProgressPage(String Title) {
        initComponents(Title);
    }
    private void initComponents(String title) {
        setVisible(true);

        TotalProgress = new JLabel();
        SeasonValueLabel = new JLabel();
        Titlelabel = new JLabel();
        VoiceActor1 = new JLabel();
        CharacterLabel1 = new JLabel();
        CharacterLabel2 = new JLabel();
        YearLabel = new JLabel();
        UserProgressBox = new JComboBox<>();
        UserProgressLabel = new JLabel();
        RatingBox = new JComboBox();
        RatingLabel = new JLabel();
        label7 = new JLabel();
        SubmitButton = new JButton();
        GenreLabel = new JLabel();

        //======== this ========
        setBackground(new Color(0xf0f011));
        setIconImage(new ImageIcon("E:\\photo\\New Folder (2)2015\\20130419_132450.jpg").getImage());
        // to close only the frame that is opened seprately like chrome
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the close operation for this frame
                dispose(); // Close the current frame
            }
        });
        setForeground(SystemColor.textHighlight);
        setTitle(title);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        List<String[] > data = getData(title);

        //---- Titlelabel ----
        Titlelabel.setText(title);
        Titlelabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(Titlelabel);
        Titlelabel.setBounds(60, 40, 300, 35);

        // --- TotalProgress---
        TotalProgress.setText("Total Episodes: ");
        TotalProgress.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(TotalProgress);
        TotalProgress.setBounds(60, 190, 250, 35);

        // --- SeasonValueLabel---
        SeasonValueLabel.setText(data.get(1)[3]);
        SeasonValueLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(SeasonValueLabel);
        SeasonValueLabel.setBounds(320, 190, 190, 35);


        //---- CharacterLabel1 ----
        CharacterLabel1.setText(data.get(0)[1] + " : " + data.get(0)[0] );
        CharacterLabel1.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(CharacterLabel1);
        CharacterLabel1.setBounds(60, 280, 500, 35);

        //---- CharacterLabel2 ----
        CharacterLabel2.setText( data.get(1)[1] + " : " +  data.get(1)[0]);
        CharacterLabel2.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(CharacterLabel2);
        CharacterLabel2.setBounds(60, 325, 500, 35);

        //---- YearLabel ----
        YearLabel.setText("(" + data.get(0)[2] + ")");
        YearLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(YearLabel);
        YearLabel.setBounds(350, 40, 165, 35);

        //---- GenreLabel ----
        GenreLabel.setText(data.get(0)[6]);
        GenreLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        contentPane.add(GenreLabel);
        GenreLabel.setBounds(60, 95, 500, 35);


        // --- UserProgressLabel ---
        UserProgressLabel.setText("My Progress : ");
        UserProgressLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(UserProgressLabel);
        UserProgressLabel.setBounds(60, 235, 190, 35);

        //---- UserProgressBox ----
        int Season = Integer.parseInt(data.get(0)[5]);
        UserProgressBox.setFont(new Font("Arial", Font.BOLD, 20));
        UserProgressBox.setSelectedItem(Integer.parseInt(data.get(0)[5]));
        UserProgressBox.setBackground(new Color(0xff394d));
        contentPane.add(UserProgressBox);
        for (int i = 0; i <= Season; i++) {
            int rating = i ;
            UserProgressBox.addItem(rating);
        }
        UserProgressBox.setBounds(270, 235, 90, 24);

        //---- RatingBox ----
        RatingBox.setFont(new Font("Arial", Font.BOLD, 20));
        RatingBox.setSelectedItem(Float.parseFloat(data.get(0)[4]));
        RatingBox.setBackground(new Color(0xff394d));
        contentPane.add(RatingBox);
        for (int i = 0; i <= 100; i++) {
            float rating = i/10.0f;
            RatingBox.addItem(rating);
        }
        RatingBox.setBounds(270, 150, 90, 24);

        //---- RatingLabel ----
        RatingLabel.setText("Rating");
        RatingLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        contentPane.add(RatingLabel);
        RatingLabel.setBounds(60, 145, 165, 35);

        //---- label7 ----
        label7.setText("text");
        label7.setBackground(Color.magenta);
        label7.setIcon(new ImageIcon("E:\\photo\\4k-captain-america-1g.jpg"));
        contentPane.add(label7);
        label7.setBounds(0, 0, 635, 515);

        //---- SubmitButton ----
        SubmitButton.setText("Submit Button");
        contentPane.add(SubmitButton);
        SubmitButton.setBounds(195, 420, 175, 35);
        SubmitButton.addActionListener(e -> {
            int userProgressDropdownData = UserProgressBox.getSelectedIndex();
            float ratingData = (float) RatingBox.getSelectedItem();
            insertData(title,userProgressDropdownData , ratingData);
        });

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
    }

    private List<String[]> getData(String title) {
        List<String[]> dataList = new ArrayList<>();
        openConnection();

        String Query = "{ call GetAnimeShowCombinedData( ? ) }"; // Note the correct stored procedure name
        try (CallableStatement cstmt = dbConnection.getConnection().prepareCall(Query)) {
            cstmt.setString(1, title); // Set the movie title parameter

            try (ResultSet resultSet = cstmt.executeQuery()) {
                while (resultSet.next()) {
                    String actorFirstName = resultSet.getString("VA_last_name");
                    String actorLastName = resultSet.getString("VA_first_name");
                    String CHName = resultSet.getString("Char_name");
                    String tags = resultSet.getString("Tags");
                    int releaseDate = resultSet.getInt("Release_year");
                    int totalprogress = resultSet.getInt("Total_Progress");
                    int userProgresss = resultSet.getInt("User_Progress");
                    float ratings = resultSet.getFloat("rating");

                    String VAActor = actorFirstName + " " + actorLastName;
                    String[] rowData = {
                            VAActor,
                            CHName,
                            String.valueOf(releaseDate),
                            String.valueOf(userProgresss),
                            String.valueOf(ratings),
                            String.valueOf(totalprogress),
                            tags
                    };

                    dataList.add(rowData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDatabaseConnection();
        }

        return dataList;
    }



    private void insertData(String title, int userProgressDropdownData, float ratingData) {
        openConnection();

        String updateQuery = "UPDATE anime SET User_Progress = ?, rating = ? WHERE TITLE = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, userProgressDropdownData);
            preparedStatement.setFloat(2, ratingData);
            preparedStatement.setString(3, title);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update was successful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeDatabaseConnection();
        }
    }


    private void openConnection(){
        dbConnection = new DatabaseConnection();
    }

    public static void main(String[] args) {
        AnimeUserRatingProgressPage page = new AnimeUserRatingProgressPage("Haikyuu ");
    }

    private void closeDatabaseConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}
