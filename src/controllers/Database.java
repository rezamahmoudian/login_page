package controllers;

import classes.person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Random;

public class Database {
    private static Connection connection = null;

    public static Statement getStatement() {
        return statement;
    }

    private static Statement statement = null;

    private Database() {
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load MySQL Driver");
        }
    }

    public static void makeConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String databace = "jdbc:mysql://localhost:3306/databace_test?user=root&useUnicode=true&characterEncoding=UTF-8";
            connection = DriverManager.getConnection(databace);
            statement = connection.createStatement();
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void register_user(person p) {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS person2 ( `id` VARCHAR(30) NOT NULL , `name` TEXT NOT NULL , `family` TEXT NOT NULL , `username` TEXT NOT NULL , `password` TEXT NOT NULL , PRIMARY KEY (`id`) ,UNIQUE (`UserName`))";
            getStatement().execute(crtbl);
            //مشکل(ارور) در ثبت نام
        } catch (Exception ex) {
            System.out.println(ex);
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("ERROR");
            alert2.setHeaderText(null);
            alert2.setContentText("Registration Failed pleaes TryAgain");
            alert2.showAndWait();
        }
        //ارسال اطلاعات ثبت نام به دیتابیس
        Random rnd = new Random();
        String id = String.valueOf(rnd.nextInt(1000));
        System.out.println("id = " + id);
        String setinfo = "INSERT INTO person2 (id ,name, family,username, password)  values ('%s','%s','%s','%s','%s')";
        setinfo = String.format(setinfo, p.getID(), p.getFirstName(), p.getLastName(), p.getUsername(), p.getPassword());
        System.out.println(setinfo);

        try {
            getStatement().execute(setinfo);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Registration");
            alert2.setHeaderText(null);
            alert2.setContentText("Successfully Registration!\nyour id is : " + id);
            alert2.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnection();

    }

    public static boolean login_user(String txtusername, String txtpassword) {
        boolean login = false;
        try {
            String mysql = "SELECT id ,name, family,username , password FROM person2";

            ResultSet result = Database.getStatement().executeQuery(mysql);
            while (result.next()) {
                //   String ID = result.getString("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String name = result.getString("name");
                String family = result.getString("family");
                if (txtusername.compareTo(username) == 0 && txtpassword.compareTo(password) == 0) {
                    // ست کردن اطلاعات در کلاس person مطابق با اطلاعات کاربر
                    login = true;
                    String id = result.getString("id");
  //                  LoginPage2_Controller.set_id(id);
                    System.out.println("id geted from databace =" + id);
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return login;
    }

    public static person set_home_items() {
        String id = LoginPage2_Controller.get_id();
        System.out.println("id in dabase class = " + id);
        person person1 = new person();
        try {
            String mysql = "SELECT name, family,username , password FROM person2 WHERE id =" + id;
            System.out.println("mysql=" + mysql);
            ResultSet result = Database.statement.executeQuery(mysql);
            result.next();
            //   String ID = result.getString("id");
            String username = result.getString("username");
            String password = result.getString("password");
            String name = result.getString("name");
            String family = result.getString("family");
            String fullname = (name + " " + family);
            System.out.println("fullname =" + fullname);
   //         person1.setFullname(fullname);
            person1.setFirstName(name);
            person1.setLastName(family );
            person1.setID("آی دی : " + id);
            person1.setUsername(username);
        } catch (Exception e) {
            System.out.println(e);
        }
        return person1;
    }


    public static void create_book_table() {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS `databace_test`.`books` (`id` INT NOT NULL , `amantgirande` TEXT , `name` TEXT NOT NULL ,  `writer` TEXT NOT NULL ,  `date` TEXT NOT NULL ,  `amantdahande` TEXT NOT NULL ,  `date_ms` BIGINT NOT NULL ,  `mohlat` INT NOT NULL , PRIMARY KEY (`id`) ) ENGINE = InnoDB";
            Database.statement.executeQuery(crtbl);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void create_HomeList(String txtamanatgirande , VBox pnitem){
        Node[] nodes = new Node[1000];

        try{
        String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where amantgirande = "+ "\"" +txtamanatgirande+"\"";
        System.out.println(mysql);
        ResultSet result = Database.statement.executeQuery(mysql);
        int i=0;
        while (result.next()) {
            int bookid = result.getInt("id");
            String bookname = result.getString("name");
            String bookwriter = result.getString("writer");
            String date = result.getString("date");
            long date_ms = result.getLong("date_ms");
            String amanatdahande = result.getString("amantdahande");
            String amanatgirande = result.getString("amantgirande");
            java.util.Date date1 = new Date();
            long tenday = 86400000;
            long mohlat = date_ms + tenday - date1.getTime();
            mohlat = (mohlat / 8640000) + 1;
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(Controlleritem.class.getResource("../fxml/item.fxml"));
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
                pnitem.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    } catch (Exception e) {
        System.out.println(e);
    }


    }









}


