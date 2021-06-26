package controllers;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private VBox pnItems = null;

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

    final ObservableList<String>bookInfo = FXCollections.observableArrayList();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id = LoginPage2_Controller.get_id();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();
            String mysql = "SELECT name, family,username , password FROM person2 WHERE id ="+id;
            System.out.println("mysql=" +mysql);
            ResultSet result = state.executeQuery(mysql);
            while (result.next()) {
                //   String ID = result.getString("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String name = result.getString("name");
                String family = result.getString("family");
                String fullname = (name + " " + family);
                System.out.println("fullname =" + fullname);
                lbl_fullname.setText(fullname);
                lbl_name.setText(name+" : نام");
                lbl_family.setText(family+" : نام خانوادگی");
                lbl_ID.setText("آی دی : "+id);
                lbl_AccessLevel.setText("سطح دسترسی : کتابدار");
                lbl_UserName.setText(username + " : نام کاربری");
                //         setLbl_book_name();
            }
        }catch (Exception e){
            System.out.println(e);
        }
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

    public void homeList(){
        pnItems.getChildren().clear();
        Node[] nodes = new Node[1000];
        // for (int i = 0; i < nodes.length; i++) {
        try {
            //اتصال به دیتابیس
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();
            try {
                //ساختن تیبل مورد نیاز در دیتابیس
                String crtbl = "CREATE TABLE  IF NOT EXISTS `databace_test`.`books` (`id` INT NOT NULL , `amantgirande` TEXT , `name` TEXT NOT NULL ,  `writer` TEXT NOT NULL ,  `date` TEXT NOT NULL ,  `amantdahande` TEXT NOT NULL ,  `date_ms` BIGINT NOT NULL ,  `mohlat` INT NOT NULL , PRIMARY KEY (`id`) ) ENGINE = InnoDB";
                state.execute(crtbl);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where amantgirande = "+ "\"" +lbl_fullname.getText()+"\"";
            System.out.println(mysql);
            ResultSet result = state.executeQuery(mysql);
            int i=0;
            while (result.next()) {
                int bookid = result.getInt("id");
                String bookname = result.getString("name");
                String bookwriter = result.getString("writer");
                String date = result.getString("date");
                long date_ms = result.getLong("date_ms");
                String amanatdahande = result.getString("amantdahande");
                String amanatgirande = result.getString("amantgirande");
                Date date1 = new Date();
                long tenday = 86400000;
                long mohlat = date_ms + tenday - date1.getTime();
                mohlat = (mohlat / 8640000) + 1;

                try {

                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item.fxml"));
                    //nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                    Parent root = (Parent) loader.load();

                    Controlleritem a = loader.getController();
                    a.setname(bookname);
                    a.setwriter(bookwriter);
                    a.setdate(date);
                    a.setmohlat(String.valueOf(mohlat));
                    a.set_bookID(String.valueOf(bookid));

                    nodes[i] = root;
                    //give the items some effect

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                    pnItems.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
            state.close();
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void btn_BookList_clicked(ActionEvent actionEvent) {
        pane_Info.setVisible(false);
        pane_Home.setVisible(false);
        pane_BooksList.setVisible(true);
        booklists();
    }


    public void booklists(){
        pnItems_booklist.getChildren().clear();
        Node[] nodes = new Node[1000];

        try {
            //اتصال به دیتابیس
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();
            try {
                //ساختن تیبل مورد نیاز در دیتابیس
                String crtbl = "CREATE TABLE  IF NOT EXISTS `databace_test`.`books` ( `id` INT NOT NULL , `amantgirande` TEXT , `name` TEXT NOT NULL ,  `writer` TEXT NOT NULL ,  `date` TEXT NOT NULL ,  `amantdahande` TEXT NOT NULL ,  `date_ms` BIGINT NOT NULL ,  `mohlat` INT NOT NULL , PRIMARY KEY (`id`) ) ENGINE = InnoDB";
                state.execute(crtbl);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            String mysql = "SELECT id , amantgirande,  name, writer , date, date_ms , amantdahande , mohlat FROM books";
            System.out.println(mysql);
            ResultSet result = state.executeQuery(mysql);
            int i=0;
            while (result.next()) {
                String bookname = result.getString("name");
                String bookwriter = result.getString("writer");
                String date = result.getString("date");
                long date_ms = result.getLong("date_ms");
                String amanatdahande = result.getString("amantdahande");
                String amanatgirande = result.getString("amantgirande");
                Date date1 = new Date();
                long tenday = 86400000;
                int bookid1 = result.getInt("id");

                long mohlat = date_ms + tenday - date1.getTime();
                mohlat = (mohlat / 8640000) + 1;
                try {
                    final int j = i;
                    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/itemBook.fxml"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item2.fxml"));
                    //nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                    Parent root = (Parent) loader.load();
                    Controlleritem2 a = loader.getController();
                    System.out.println("bookid = "+ bookid1);
                    a.set_bookname(bookname);
                    a.set_bookwriter(bookwriter);
                    a.set_amanatdahande(amanatdahande);
                    a.set_vaziyat("mojod");
                    a.set_bookID(String.valueOf(bookid1));
                    //itemBookCtrl a = loader.getController();
                    nodes[i] = root;
                    //give the items some effect
                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                    pnItems_booklist.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
            state.close();
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void btn_search_clicked(ActionEvent actionEvent) {

        
    }

    public void btn_addBoock_clicked(ActionEvent actionEvent) {
        try {
            //اتصال به دیتابیس
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();
            try {
                //ساختن تیبل مورد نیاز در دیتابیس
                String crtbl = "CREATE TABLE  IF NOT EXISTS `databace_test`.`books` ( `id` INT NOT NULL , `amantgirande` TEXT , `name` TEXT NOT NULL ,  `writer` TEXT NOT NULL ,  `date` TEXT NOT NULL ,  `amantdahande` TEXT NOT NULL ,  `date_ms` BIGINT NOT NULL ,  `mohlat` INT NOT NULL , PRIMARY KEY (`id`) ) ENGINE = InnoDB";
                state.execute(crtbl);
                //مشکل(ارور)
            }catch(Exception ex) {
                System.out.println(ex);
            }

            Date date = new Date();
            SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
            String dateformat = fr.format(date);

            //delete date_ms later if dont use
            String addbook= "INSERT INTO books (name, writer , date, date_ms , amantdahande , mohlat , id)  values ('%s','%s','%s','%s','%s','%s','%d')";
            System.out.println("bookid = "+pnItems_booklist.getChildren().size());

            int book_id = pnItems_booklist.getChildren().size();
            System.out.println("namebook = " + nameBook_field.getText() );
            //int book_id = Integer.parseInt(String.valueOf(state.executeQuery(getid)));
            addbook = String.format(addbook, nameBook_field.getText() , name_writer.getText() , dateformat ,date.getTime() , lbl_fullname.getText(), 10 , book_id );
            System.out.println(addbook);
            state.execute(addbook);

            state.close();
            connect.close();
        }catch (Exception e){
            System.out.println(e);
        }
        booklists();
        nameBook_field.setText("");
        name_writer.setText("");
    }

    public void btn_amanatgiri_clicked(ActionEvent actionEvent) {
        //txtField_bookid_foramanatgiri

        String bookid = bookid_foramanatgiri.getText();
        System.out.println("id in txtfield" + bookid);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();

            String mysql = "SELECT id FROM books";
            System.out.println(mysql);

            state.execute(mysql);

            //ResultSet result = state.executeQuery(mysql);
            //int book_id = result.getInt("id");

            String mysql1 = ("UPDATE books SET amantgirande = '"+lbl_fullname.getText()+"' where id="+ Integer.parseInt(bookid) );
            System.out.println(mysql1);

            state.execute(mysql1);

        }catch (Exception e){
            System.out.println(e);
        }

    }

    //جستجو در صفحه ی بوک لیست بر اساس نام کتاب
    public void btn_search_BkList_clicked(ActionEvent actionEvent) {
        String bookname = search_Bklist_txtfield.getText();

        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();

            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where name = "+ "\""+ bookname +"\"";
            System.out.println(mysql);
            ResultSet result = state.executeQuery(mysql);

            int i=0;
            while (result.next()) {
                int bookid = result.getInt("id");
                bookname = result.getString("name");
                String bookwriter = result.getString("writer");
                String date = result.getString("date");
                long date_ms = result.getLong("date_ms");
                String amanatdahande = result.getString("amantdahande");
                String amanatgirande = result.getString("amantgirande");
                Date date1 = new Date();
                long tenday = 86400000;


                long mohlat = date_ms + tenday - date1.getTime();
                mohlat = (mohlat / 8640000) + 1;

                //ست کردن دوباره ی جول نمایش کتاب ها بر اساس نام سرچ شده
                try {
                    final int j = i;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item2.fxml"));
                    //nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
                    Parent root = (Parent) loader.load();

                    Controlleritem2 a = loader.getController();
                    a.set_bookname(bookname);
                    a.set_bookwriter(bookwriter);
                    a.set_amanatdahande(amanatdahande);
                    a.set_bookID(String.valueOf(bookid));


                    nodes[i] = root;
                    //give the items some effect

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                    pnItems_booklist.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
            state.close();
            connect.close();
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

