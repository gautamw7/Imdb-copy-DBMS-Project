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

    private int pagecount = 0 ;
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
        movieButtons = new JButton[]{TVShowsButton1, TVShowsButton2, TVShowsButton3, TVShowsButton4, TVShowsButton5};
        int buttonCounter = 0;
        for (int i = intialPage; i < entries.size() && buttonCounter < DisplayPageCount; i++) {
            movieButtons[buttonCounter].setText(entries.get(i));
            buttonCounter++;
            pagecount++;

        }

        // Adding the buttons to the buttonGroup
        buttonGroup.add(TVShowsButton1);
        buttonGroup.add(TVShowsButton2);
        buttonGroup.add(TVShowsButton3);
        buttonGroup.add(TVShowsButton4);
        buttonGroup.add(TVShowsButton5);
        ActionListener commonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                // Handle the button click here
                String TVshowTitle = clickedButton.getText();
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
                    TVShowsPage object = new TVShowsPage(pagecount);
                    dispose();
                } else {
                    // All movies have been displayed
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
            this2.pack();
            this2.setLocationRelativeTo(this2.getOwner());
        }
    }


    private java.util.List<String> getEntries() {
        List<String> entries = new ArrayList<>();
        openDatabaseConnection();
        try {
            Connection connection = dbConnection.getConnection();
            String query = "SELECT Title FROM tvshows";

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

    public static void main(String[] args){
        TVShowsPage object = new TVShowsPage(0);
    }

}
