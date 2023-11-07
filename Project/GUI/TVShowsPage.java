/*
 * Created by JFormDesigner on Tue Oct 31 10:24:59 IST 2023
 */

package Project.GUI;

import Project.DatabaseConnection;
import Project.MainPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author lenovo
 */
public class TVShowsPage extends JFrame  {
    private DatabaseConnection dbConnection;


    private JFrame this2;
    private JButton HomePageButton, BackPageButton, TVShowsButton1,TVShowsButton2,TVShowsButton3,TVShowsButton4,TVShowsButton5,NextPageButton;
    private ButtonGroup buttonGroup;
    private JLabel label7;
    private JButton[] movieButtons;
    private int DisplayPageCount = 5;

    private int pagecount = 0 ; // to the count the number movies displayed 
    public TVShowsPage(int intialPage){
        initComponents(getEntries(), intialPage);
        setVisible(true);
        setSize(500, 420);
        // to close only the frame that is opened seprately like chrome
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the close operation for this frame
                dispose(); // Close the current frame
            }
        });
    }
    private void initComponents(List<String> entries ,  int intialPage ) {
                // initial all the elements.
        pagecount = intialPage;
        buttonGroup = new ButtonGroup();
        this2 = new JFrame();
        TVShowsButton1 = new JButton();
        TVShowsButton2 = new JButton();
        TVShowsButton3 = new JButton();
        TVShowsButton4 = new JButton();
        TVShowsButton5 = new JButton();
        NextPageButton = new JButton();
        HomePageButton = new JButton();
        BackPageButton = new JButton();
        label7 = new JLabel();

        // The Labeling of the Movies to the Tvshow Page.
        movieButtons = new JButton[]{TVShowsButton1, TVShowsButton2, TVShowsButton3, TVShowsButton4, TVShowsButton5};        // make 5 movieButton 

        int buttonCounter = 0;
        for (int i = intialPage; i < entries.size() && buttonCounter < DisplayPageCount; i++) {  // entries.size() of list of the tvshows, and buttonCounter < 5 
            movieButtons[buttonCounter].setText(entries.get(i)); // setting the buttonns name 
            buttonCounter++;
            buttonCounter++;
            pagecount++;

        }

        // Adding the buttons to the buttonGroup
        buttonGroup.add(TVShowsButton1);
        buttonGroup.add(TVShowsButton2);
        buttonGroup.add(TVShowsButton3);
        buttonGroup.add(TVShowsButton4);
        buttonGroup.add(TVShowsButton5);
                // adding a comomon ActionListinere for all the buttons 

        ActionListener commonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource(); // getting the button that was ckicked 
                // Handle the button click here
                String TVshowTitle = clickedButton.getText();         // getting the button's text 
                // opeining the rating page 
                TVShowsUserRatingProgressPage object = new TVShowsUserRatingProgressPage(TVshowTitle);
            }
        };

        //======== this2 ========
        {

            this2.setIconImage(new ImageIcon("E:\\photo\\2pac.jpg").getImage());
            this2.setTitle("Movie Page");
            var contentPane = getContentPane();
            contentPane.setLayout(null);

            //---- TVShowsButton1 ----

            TVShowsButton1.setBackground(new Color(0x00ccdb));
            contentPane.add(TVShowsButton1);
            TVShowsButton1.setBounds(20, 20, 180, 35);
            TVShowsButton1.addActionListener(commonActionListener);
            //---- TVShowsButton2 ----

            TVShowsButton2.setBackground(new Color(0x00ccdb));
            contentPane.add(TVShowsButton2);
            TVShowsButton2.setBounds(20, 75, 180, 35);
            TVShowsButton2.addActionListener(commonActionListener);

            //---- TVShowsButton3 ----

            TVShowsButton3.setBackground(new Color(0x00ccdb));
            contentPane.add(TVShowsButton3);
            TVShowsButton3.setBounds(20, 125, 180, 35);
            TVShowsButton3.addActionListener(commonActionListener);

            //---- TVShowsButton4 ----

            TVShowsButton4.setBackground(new Color(0x00ccdb));
            contentPane.add(TVShowsButton4);
            TVShowsButton4.setBounds(20, 175, 180, 35);
            TVShowsButton4.addActionListener(commonActionListener);

            //---- TVShowsButton5 ----

            TVShowsButton5.setBackground(new Color(0x00ccdb));
            contentPane.add(TVShowsButton5);
            TVShowsButton5.setBounds(20, 225, 180, 35);
            TVShowsButton5.addActionListener(commonActionListener);

            //---- NextPageButton ----
            NextPageButton.setText("NEXT");
            NextPageButton.setBackground(new Color(0x00ccdb));
            contentPane.add(NextPageButton);
            NextPageButton.setBounds(350, 305, 110, 35);
            NextPageButton.addActionListener(e ->{
                if (pagecount < entries.size()) {
                                        // opeing the page again but sending the current count of the pages 
                    TVShowsPage object = new TVShowsPage(pagecount);
                    dispose();
                } else {
                    // All tvshows  have been displayed
                    NextPageButton.setText("Finished");
                    System.out.println("All movies displayed");
                }
            });

            //---- HomePageButton ----
            HomePageButton.setText("Home ");
            HomePageButton.setBackground(new Color(0x00ccdb));
            HomePageButton.setIcon(null);
            contentPane.add(HomePageButton);
            HomePageButton.setBounds(175, 305, 110, 35);
            HomePageButton.addActionListener(e -> {
                MainPage object = new MainPage();
            });

            //---- BackPageButton ----
            BackPageButton.setText("BACK");
            BackPageButton.setBackground(new Color(0x00ccdb));
            contentPane.add(BackPageButton);
            BackPageButton.setBounds(35, 305, 95, 35);
            BackPageButton.addActionListener(e -> {
                if(pagecount <= 5){
                    BackPageButton.setText("Page 1..");
                    pagecount = 5;
                }else{
                    pagecount -= DisplayPageCount;
                    TVShowsPage object = new TVShowsPage(pagecount - 5);
                    dispose();
                }
            });



            //---- label7 ----
            label7.setText("text");
            label7.setIcon(new ImageIcon("E:\\photo\\4k-captain-america-1g.jpg"));
            contentPane.add(label7);
            label7.setBounds(0, 0, 500, 420);

            {
                // compute preferred size of the Contianer ,
                // So, it caluclated the width of all the componenets and then the height of all of the comoponenets in the panel, to deteemine it s width and heigh 
                // this allows for a perfect size of the frame
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
            this2.pack();
            this2.setLocationRelativeTo(this2.getOwner());
        }
    }


    private java.util.List<String> getEntries() {
                // getting the entres from the database
        List<String> entries = new ArrayList<>();
        openDatabaseConnection();
        try {
            Connection connection = dbConnection.getConnection();
            String query = "SELECT Title FROM tvshows";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {// creating the connection using the method described below                 ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String entry = resultSet.getString("Title"); // to select only the title from the mvoeis table in the database
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
        dbConnection = new DatabaseConnection();  // funciton to open the database from the databseconnectivity lcass 
    }

    private void closeDatabaseConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }

    public static void main(String[] args){
        TVShowsPage object = new TVShowsPage(0);
    }

}
