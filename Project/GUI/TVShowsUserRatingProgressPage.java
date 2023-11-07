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
public class TVShowsUserRatingProgressPage extends JFrame {

    private DatabaseConnection dbConnection;
    private JLabel Titlelabel;
    private JLabel ActorLabel1, ActorLabel2, Directorlabel1;
    private JLabel YearLabel, RatingLabel, GenreLabel ,SeasonLabel, SeasonValueLabel , UserProgressLabel;
    private JComboBox RatingBox;
    private JComboBox UserProgressBox;
    private JLabel label7;
    private JButton SubmitButton;


    public TVShowsUserRatingProgressPage(String Title) {
        initComponents(Title);
    }
    private void initComponents(String title) {
        setVisible(true);

        SeasonLabel = new JLabel();
        SeasonValueLabel = new JLabel();
        Titlelabel = new JLabel();
        Directorlabel1 = new JLabel();
        ActorLabel1 = new JLabel();
        ActorLabel2 = new JLabel();
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

        // --- SeasonLabel---
        SeasonLabel.setText("Season ");
        SeasonLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(SeasonLabel);
        SeasonLabel.setBounds(60, 190, 190, 35);

        // --- SeasonValueLabel---
        SeasonValueLabel.setText(data.get(1)[3]);
        SeasonValueLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(SeasonValueLabel);
        SeasonValueLabel.setBounds(270, 190, 190, 35);


        //---- Directorlabel1 ----
        Directorlabel1.setText("Director : " + data.get(0)[0]);
        Directorlabel1.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(Directorlabel1);
        Directorlabel1.setBounds(60, 285, 500, 35);

        //---- ActorLabel1 ----
        ActorLabel1.setText( "Actor : " + data.get(0)[1] );
        ActorLabel1.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(ActorLabel1);
        ActorLabel1.setBounds(60, 330, 500, 35);

        //---- ActorLabel2 ----
        ActorLabel2.setText("Actor : " +  data.get(1)[1]);
        ActorLabel2.setFont(new Font("Tahoma", Font.BOLD, 26));
        contentPane.add(ActorLabel2);
        ActorLabel2.setBounds(60, 370, 500, 35);

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
        int Season = Integer.parseInt(data.get(0)[3]);
        UserProgressBox.setFont(new Font("Arial", Font.BOLD, 20));
        UserProgressBox.setSelectedItem(Integer.parseInt(data.get(0)[3]));
        UserProgressBox.setBackground(new Color(0xff394d));
        contentPane.add(UserProgressBox);
        for (int i = 0; i <= Season; i++) {
            int rating = i ;
            UserProgressBox.addItem(rating);
        }
        UserProgressBox.setBounds(270, 235, 90, 24);

        //---- RatingBox ----
        RatingBox.setFont(new Font("Arial", Font.BOLD, 20));
        RatingBox.setSelectedItem(Float.parseFloat(data.get(0)[5]));
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
            int userProgressDropdownData = UserProgressBox.getSelectedIndex(); // to get the user progress 
            float ratingData = (float) RatingBox.getSelectedItem();  // to get the ratign from the data 
            insertData(title,userProgressDropdownData , ratingData);
        });

        { 
             // compute preferred size of the Contianer ,
                // So, it caluclated the width of all the componenets and then the height of all of the comoponenets in the panel, to deteemine it s width and heigh 
                // this allows for a perfect size of the frame
            // compute preferred size of the Frame ( Refer from tvshows page)
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right; // adding all the widthe 
                preferredSize.height += insets.bottom; // adding all the length 
                contentPane.setMinimumSize(preferredSize); // settign the contentPAnel  minimum size for the size 
                contentPane.setPreferredSize(preferredSize); // setting the panel to that preferred size
        }
        pack();
        setLocationRelativeTo(getOwner());
    }

    private List<String[]> getData(String title) {
        List<String[]> dataList = new ArrayList<>();
        openConnection();

        String Query = "{ call GetTVShowCombinedData( ? ) }"; // Proceedure to get the detials required for the table, by passing the name of the tvshow, 
        //    their multiple entrires we are getting from it, because we get the combined table with directors name and then mulitple names of the actors, thats why we need a string array[] and to strore thost string array we need list 
        try (CallableStatement cstmt = dbConnection.getConnection().prepareCall(Query)) {
            cstmt.setString(1, title); // Set the movie title parameter

            try (ResultSet resultSet = cstmt.executeQuery()) {
                while (resultSet.next()) {
                    /*
                    Getting the director name from the table and then joinning them 
                    getting the actor name from the table and then joining them 
                    gettin all the other details 
                    */
                    String directorFirstName = resultSet.getString("director_first_name");
                    String directorLastName = resultSet.getString("director_last_name");
                    String actorFirstName = resultSet.getString("actor_first_name");
                    String actorLastName = resultSet.getString("actor_last_name");
                    String Genre = resultSet.getString("Genre");
                    int Season = resultSet.getInt("Season");
                    int releaseDate = resultSet.getInt("ReleaseYear");
                    int userProgresss = resultSet.getInt("User_Progress");
                    float ratings = resultSet.getFloat("Rating");

                    String Director = directorFirstName + " " + directorLastName;
                    String Actor = actorFirstName + " " + actorLastName;
                    String[] rowData = { // adding them in the rowData table 
                            Director,
                            Actor,
                            String.valueOf(releaseDate),
                            String.valueOf(Season),
                            String.valueOf(userProgresss),
                            String.valueOf(ratings),
                            Genre
                    };

                    dataList.add(rowData);  // addint this deitals to the list 
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
        // inseeritng the data into the table 
        String updateQuery = "UPDATE tvshows SET User_Progress = ?, Rating = ? WHERE Title = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, userProgressDropdownData);
            preparedStatement.setFloat(2, ratingData);
            preparedStatement.setString(3, title);

            // 
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
        TVShowsUserRatingProgressPage page = new TVShowsUserRatingProgressPage("Breaking Bad");
    }

    private void closeDatabaseConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}
