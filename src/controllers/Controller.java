package controllers;

import classes.Books;
import classes.person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Controller implements Initializable {
    static String id;

    @FXML
    private Pane pane_vorod;

    @FXML
    private Pane rightPane;

    @FXML
    private StackPane leftPane;

    @FXML
    private JFXButton btn_vorod;

    @FXML
    public VBox pnItems = null;

    @FXML
    private VBox pnItems_booklist = null;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    ////////////////
    @FXML
    private Button btn_home;

    @FXML
    private Label lbl_N_A_B;

    @FXML
    private Label lbl_FullName;

    @FXML
    private Pane pane_Home;

    @FXML
    private Button btn_Signout;

    @FXML
    private Label lbl_N_G_B;

    @FXML
    private Button btn_info;

    @FXML
    private Pane pane_BooksList;

    @FXML
    private VBox pnItems1;

    @FXML
    private Button btn_addBoock;

    @FXML
    private JFXButton btn_search;

    @FXML
    private Pane pane_Info;

    @FXML
    private Button btn_BookList;

    @FXML
    private Label lbl_ID;

    @FXML
    private Label lbl_UserName;

    @FXML
    private TextField search_Bklist_txtfield;

    @FXML
    private TextField name_writer;

    @FXML
    private Label lbl_AccessLevel;

    @FXML
    private Label lbl_name;

    @FXML
    private JFXButton btn_search_BkList;

    @FXML
    private Label lbl_family;

    @FXML
    private TextField nameBook_field;

    @FXML
    private Label lbl_DateofRegis;

    @FXML
    private TextField search_field;
    /////////
    @FXML
    private Label lbl_list;

    @FXML
    private Label lbl_fullname;

    @FXML
    private TextField bookid_foramanatgiri;

    @FXML
    private JFXListView<String> listView_Home;

    final ObservableList<String> bookInfo = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        person person1 = new person();

        try {
            Database.makeConnection();
            person1 = Database.set_home_items();
            Database.closeConnection();
            lbl_fullname.setText(person1.getFullname());
            lbl_name.setText(person1.getFirstName() + " : نام");
            lbl_family.setText(person1.getLastName() + " : نام خانوادگی");
            lbl_ID.setText("آی دی : " + person1.getID());
            lbl_AccessLevel.setText("سطح دسترسی : کتابدار");
            lbl_UserName.setText(person1.getUsername() + " : نام کاربری");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        //
        pane_vorod.setVisible(false);
        leftPane.setVisible(true);
        rightPane.setVisible(true);
        homeList();
    }


    @FXML
    void btn_vorod_clicked(ActionEvent event) {
        pane_BooksList.setVisible(false);
        pane_Info.setVisible(false);
        pane_Home.setVisible(true);
    }

    public void btn_Home_clicked(ActionEvent actionEvent) {
        pane_BooksList.setVisible(false);
        pane_Info.setVisible(false);
        pane_Home.setVisible(true);
        homeList();
    }

    public void homeList() {
        pnItems.getChildren().clear();

        int i = 0;
        try {
            //اتصال به دیتابیس
            Database.makeConnection();
            //ساختن تیبل مورد نیاز در دیتابیس
            Database.create_book_table();
            String amanatgirande = lbl_fullname.getText();
            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where amantgirande = " + "\"" + amanatgirande + "\"";
            showbooks_homepage(Database.create_bookList(mysql));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Database.closeConnection();
    }

    public void showbooks_homepage(List <Books> books) {
        pnItems.getChildren().clear();
        Node[] nodes = new Node[1000];
        int i = 0;
        if(books != null) {
            try {
                for (Books book : books) {
                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item.fxml"));
                    Parent root = (Parent) loader.load();
                    Controlleritem bookitem = loader.getController();
                    bookitem.setitems(book);

                    nodes[i] = root;
                    //give the items some effect
                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                    pnItems.getChildren().add(nodes[i]);
                    i++;
                    System.out.println(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void showbooks(List<Books> books){
        System.out.println("books    " + books);
        pnItems.getChildren().clear();
        Node[] nodes = new Node[1000];
        int i = 0;
        if(books != null) {
            try {
                for (Books book : books) {
                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item2.fxml"));
                    Parent root = (Parent) loader.load();
                    Controlleritem2 bookitem = loader.getController();
                    bookitem.set_items(book);
                    nodes[i] = root;

                    //give the items some effect
                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                    pnItems_booklist.getChildren().add(nodes[i]);
                    i++;
                    System.out.println(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
        public void btn_BookList_clicked(ActionEvent actionEvent) throws ClassNotFoundException {
        pane_Info.setVisible(false);
        pane_Home.setVisible(false);
        pane_BooksList.setVisible(true);
        booklists();
    }

    public void booklists() throws ClassNotFoundException {
        pnItems_booklist.getChildren().clear();
        try {
            //اتصال به دیتابیس
            Database.makeConnection();
            //ساختن تیبل مورد نیاز در دیتابیس
            Database.create_book_table();
            String mysql = "SELECT id , amantgirande,  name, writer , date, date_ms , amantdahande , mohlat FROM books";
            showbooks(Database.create_bookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void btn_search_clicked(ActionEvent actionEvent) {

        
    }

    public void btn_addBoock_clicked(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        addBookPage addBookPage = new addBookPage();
        addBookPage.showpage(lbl_fullname.getText());

        booklists();
    }

    public void btn_amanatgiri_clicked(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        //txtField_bookid_foramanatgiri

        String bookid = bookid_foramanatgiri.getText();
        System.out.println("id in txtfield" + bookid);
        String amanatgirande = lbl_fullname.getText();

        Database.makeConnection();
        Database.amanatgiri(amanatgirande, bookid);


    }

    //جستجو در صفحه ی بوک لیست بر اساس نام کتاب
    public void btn_search_BkList_clicked(ActionEvent actionEvent) {
        String bookname = search_Bklist_txtfield.getText();

        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];

            Database.makeConnection();

            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where name = "+ "\""+ bookname +"\"";
            showbooks(Database.create_bookList(mysql));

            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void btn_info_clicked(ActionEvent actionEvent) {
        pane_Home.setVisible(false);
        pane_BooksList.setVisible(false);
        pane_Info.setVisible(true);
    }

    public void btn_Signout_clicked(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) btn_Signout.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../fxml/login_page2.fxml"));
        Scene scene = new Scene(root ,800 ,600 );
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }


    public void setLbl_name(String a) {
        this.lbl_name.setText(a);
    }

    public void setLbl_N_A_B(String a) {
        this.lbl_N_A_B.setText(a);
    }

    public void setlbl_fullname(String a) {
        this.lbl_fullname.setText(a);
    }

    public void setLbl_ID(String a) {
        this.lbl_ID.setText(a);
    }

    public void setLbl_UserName(String a) {
        this.lbl_UserName.setText(a);
    }

    public void setLbl_AccessLevel(String a) {
        this.lbl_AccessLevel.setText(a);
    }

    public void setLbl_family(String a) {
        this.lbl_family.setText(a);
    }

    public void setLbl_DateofRegis(String a) {
        this.lbl_DateofRegis.setText(a);
    }
}


/*
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn_home) {
            btn_home.setStyle("-fx-background-color : #1620A1");
            btn_home.toFront();
        }
        if (actionEvent.getSource() == btn_BookList) {
            btn_BookList.setStyle("-fx-background-color : #53639F");
            btn_BookList.toFront();
        }
        if (actionEvent.getSource() == btn_info) {
            btn_info.setStyle("-fx-background-color : #02030A");
            btn_info.toFront();
        }
        if(actionEvent.getSource()==btnSignout)
        {
            btnSignout.setStyle("-fx-background-color : #464F67");
            btnSignout.toFront();
        }
    }

 */

