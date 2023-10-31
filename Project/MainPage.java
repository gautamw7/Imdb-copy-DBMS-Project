
package Project;

import Project.GUI.AnimePage;
import Project.GUI.MoviePage;
import Project.GUI.TVShowsPage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author lenovo
 */
public class MainPage extends JFrame {

    private JFrame MainPage;
    private JButton Movies;
    private JButton Anime;
    private JButton TVShow;
    private JLabel label1;
    private int intialPage = 0;

    public MainPage() {
        initComponents();
        setSize(1020, 690);
        setVisible(true);
        // to close only the frame that is opened seprately like chrome
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the close operation for this frame
                dispose(); // Close the current frame
            }
        });
    }

    private void initComponents() {

        MainPage = new JFrame();
        Movies = new JButton();
        Anime = new JButton();
        TVShow = new JButton();
        label1 = new JLabel();

        //======== MainPage ========
        {
            MainPage.setTitle("IMDB(DBMS Project)");
            MainPage.setIconImage(new ImageIcon("E:\\photo\\9-6.jpg").getImage());
            var contentPane = getContentPane();
            contentPane.setLayout(null);

            //---- Movies ----
            Movies.setBackground(new Color(0x001bcc));
            Movies.setIcon(new ImageIcon("E:\\Gautam\\DBMSimage.jpg"));
            Movies.setFont(new Font("Roboto Light", Font.PLAIN, 48));
            Movies.addActionListener(e ->{
                MoviePage page = new MoviePage(intialPage);
            } );
            contentPane.add(Movies);
            Movies.setBounds(35, 90, 380, 195);

            //---- Anime ----
            Anime.setFont(new Font("Roboto Light", Font.PLAIN, 48));
            Anime.setIcon(new ImageIcon("E:\\Gautam\\OIG.jpg"));
            Anime.addActionListener(e ->
            {
                AnimePage page = new AnimePage(intialPage);
            });
            contentPane.add(Anime);
            Anime.setBounds(515, 110, 480, 160);

            //---- TVShow ----
            TVShow.setFont(new Font("Roboto Light", Font.PLAIN, 48));
            TVShow.setIcon(new ImageIcon("E:\\Gautam\\DBMSimageTVShow.jpg"));
            TVShow.addActionListener(e -> {
                TVShowsPage page = new TVShowsPage(intialPage);
            });
            contentPane.add(TVShow);
            TVShow.setBounds(245, 320, 570, 220);

            //---- label1 ----
            label1.setIcon(new ImageIcon("E:\\photo\\4k-captain-america-1g.jpg"));
            contentPane.add(label1);
            label1.setBounds(0, 0, 1020, 690);

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
            MainPage.pack();
            MainPage.setLocationRelativeTo(MainPage.getOwner());
        }
    }
    public static void main(String[] args ){
        MainPage page = new MainPage();
    }

}
