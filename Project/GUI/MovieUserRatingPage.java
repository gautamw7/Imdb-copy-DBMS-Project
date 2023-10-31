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
public class MovieUserRatingPage extends JFrame {

    private DatabaseConnection dbConnection;
    private JLabel Titlelabel;
    private JLabel ActorLabel1, ActorLabel2, Directorlabel1;
    private JCheckBox WatchedCheckBox;
    private JLabel YearLabel, RatingLabel, GenreLabel ;
    private JComboBox RatingBox;
    private JLabel label7;
    private JButton SubmitButton;


    public MovieUserRatingPage(String Title) {
        initComponents(Title);
    }
    private void initComponents(String title) {
        setVisible(true);
        Titlelabel = new JLabel();
        Directorlabel1 = new JLabel();
        ActorLabel1 = new JLabel();
        ActorLabel2 = new JLabel();
        WatchedCheckBox = new JCheckBox();
        YearLabel = new JLabel();
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
        Titlelabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contentPane.add(Titlelabel);
        Titlelabel.setBounds(55, 40, 300, 35);

        //---- Directorlabel1 ----
        Directorlabel1.setText("Director : "+ data.get(0)[0]);
        Directorlabel1.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contentPane.add(Directorlabel1);
        Directorlabel1.setBounds(55, 245, 500, 35);

        //---- ActorLabel1 ----
        ActorLabel1.setText("Actor : " + data.get(0)[1]);
        ActorLabel1.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contentPane.add(ActorLabel1);
        ActorLabel1.setBounds(55, 330, 500, 35);

        //---- ActorLabel2 ----
        ActorLabel2.setText("Actor : " + data.get(1)[1]);
        ActorLabel2.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contentPane.add(ActorLabel2);
        ActorLabel2.setBounds(55, 370, 500, 35);

        //---- WatchedCheckBox ----
        Boolean watched = Boolean.parseBoolean(data.get(0)[3]);
        WatchedCheckBox.setText("Watched ");
        WatchedCheckBox.setSelected(watched);
        WatchedCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 24));
        WatchedCheckBox.setBackground(new Color(0xf0d0b3));
        contentPane.add(WatchedCheckBox);
        WatchedCheckBox.setBounds(55, 190, 190, WatchedCheckBox.getPreferredSize().height);

        //---- YearLabel ----
        YearLabel.setText("(" + data.get(0)[2] + ")");
        YearLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
        contentPane.add(YearLabel);
        YearLabel.setBounds(350, 40, 165, 35);

        //---- GenreLabel ----
        GenreLabel.setText(data.get(0)[5]);
        GenreLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        contentPane.add(GenreLabel);
        GenreLabel.setBounds(60, 95, 500, 35);

        //---- RatingBox ----
        RatingBox.setFont(new Font("Arial", Font.PLAIN, 20));
        RatingBox.setBackground(new Color(0xff394d));
        contentPane.add(RatingBox);
        for (int i = 0; i <= 100; i++) {
            float rating = i/10.0f;
            RatingBox.addItem(rating);
        }
        RatingBox.setBounds(230, 150, 90, 24);

        //---- RatingLabel ----
        RatingLabel.setText("Rating");
        RatingLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
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
            boolean userProgressDropdownData = WatchedCheckBox.isSelected();
            float ratingData = (float) RatingBox.getSelectedItem();
            insertData(title, userProgressDropdownData, ratingData);
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

        String Query = "{ call GetMovieCombinedData( ? ) }"; // Note the correct stored procedure name
        try (CallableStatement cstmt = dbConnection.getConnection().prepareCall(Query)) {
            cstmt.setString(1, title); // Set the movie title parameter

            try (ResultSet resultSet = cstmt.executeQuery()) {
                while (resultSet.next()) {
                    String directorFirstName = resultSet.getString("director_first_name");
                    String directorLastName = resultSet.getString("director_last_name");
                    String actorFirstName = resultSet.getString("actor_first_name");
                    String actorLastName = resultSet.getString("actor_last_name");
                    String Genre = resultSet.getString("genres");
                    Date releaseDate = resultSet.getDate("release_date");
                    boolean watched = resultSet.getBoolean("watched");
                    float ratings = resultSet.getFloat("ratings");

                    String Director = directorFirstName + " " + directorLastName;
                    String Actor = actorFirstName + " " + actorLastName;
                    String[] rowData = {
                            Director,
                            Actor,
                            String.valueOf(releaseDate),
                            String.valueOf(watched),
                            String.valueOf(ratings),
                            Genre
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



    private void insertData(String title, boolean userProgressDropdownData, float ratingData) {
        openConnection();

        String updateQuery = "UPDATE movies SET watched = ?, ratings = ? WHERE Title = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setBoolean(1, userProgressDropdownData);
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

    public static void main(String[] args){
        MovieUserRatingPage obj = new MovieUserRatingPage("Oppenheimer");
        obj.setVisible(true);
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
