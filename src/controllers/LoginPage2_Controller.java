package controllers;

import classes.person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class  LoginPage2_Controller {

    @FXML
    private Label lbl_fullname;

    @FXML
    private JFXButton exit_btn;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private JFXPasswordField txt_Field_Password_R;

    @FXML
    private JFXPasswordField txt_Field_ConfirmPassword;

    @FXML
    private JFXButton btn_Register;

    @FXML
    private Label btn_SignUp;

    @FXML
    private Label btn_ForgetPassword;

    @FXML
    private JFXTextField txt_Field_ID;

    @FXML
    private JFXTextField txt_Field_LastName;

    @FXML
    private JFXTextField txtfield_UserName;

    @FXML
    private JFXTextField txt_Field_FirstName;

    @FXML
    private JFXPasswordField txtfield_Password;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;
    @FXML
    private JFXTextField txt_Field_UserName_R;

    @FXML
    private JFXButton exit_btn_regis;

    @FXML
    void press_Exit_btn(ActionEvent event) {
           Stage stage = (Stage) exit_btn.getScene().getWindow();
           stage.close();
    }

    public void press_ExitRegis_btn(ActionEvent actionEvent) {
        Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
        stage2.close();
    }
    @FXML
    void exiteRegis_clicked(ActionEvent event) {
        Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
        stage2.close();

    }

    public void exit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_Login.getScene().getWindow();
        stage.close();
    }

    public void press_Back_btn(ActionEvent actionEvent) {
        pane2.setVisible(false);
        pane1.setVisible(true);
    }


    person librarian1 = new person();
    //انجام ثبت نام
    public void press_Registration_btn(ActionEvent actionEvent) {
    //چک کردن خالی نبودن فیلد های مورد نیاز برای ثبت نام
        if(txt_Field_FirstName.getText().compareTo("")==0 || txt_Field_LastName.getText().compareTo("")==0 ||
                txt_Field_Password_R.getText().compareTo("")==0 ||
                txt_Field_ConfirmPassword.getText().compareTo("")==0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("complate all the fields");
            alert.showAndWait();
        }
        //چک کردن برابر بودن پسوورد و تاییدیه ی پسوورد
        else if(!(txt_Field_Password_R.getText().equals(txt_Field_ConfirmPassword.getText()))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Enter confirmPassword correctly");
            alert.showAndWait();
        }
        else{
            //ست کردن یک نمونه از کلاس person با اطلاعات ثبت شده توسط کاربر
            librarian1.setFirstName(txt_Field_FirstName.getText());
            librarian1.setLastName(txt_Field_LastName.getText());
            librarian1.setPassword(txt_Field_Password_R.getText());
            librarian1.setUsername(txt_Field_UserName_R.getText());
            System.out.println("Password =" +librarian1.getPassword());

        }

        try {
            //اتصال به دیتابیس
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String database = "jdbc:mysql://localhost:3307/databace_test?user=root";
            Connection connect = DriverManager.getConnection(database);
            Statement state = connect.createStatement();
            try {
                //ساختن تیبل مورد نیاز در دیتابیس
                String crtbl = "CREATE TABLE  IF NOT EXISTS person2 ( `id` VARCHAR(30) NOT NULL , `name` TEXT NOT NULL , `family` TEXT NOT NULL , `username` TEXT NOT NULL , `password` TEXT NOT NULL , PRIMARY KEY (`id`) ,UNIQUE (`UserName`))";
                state.execute(crtbl);
                //مشکل(ارور) در ثبت نام
            }catch(Exception ex){
                System.out.println(ex);
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("ERROR");
                alert2.setHeaderText(null);
                alert2.setContentText("Registration Failed pleaes TryAgain");
                alert2.showAndWait();
            }
            //ارسال اطلاعات ثبت نام به دیتابیس
            Random rnd = new Random();
            String id =String.valueOf(rnd.nextInt(1000));
            System.out.println("id = "+id);
            String setinfo = "INSERT INTO person2 (id ,name, family,username, password)  values ('%s','%s','%s','%s','%s')";
            setinfo = String.format(setinfo, id, txt_Field_FirstName.getText(), txt_Field_LastName.getText(), txt_Field_UserName_R.getText(), txt_Field_Password_R.getText());
            System.out.println(setinfo);

            state.execute(setinfo);
            state.close();
            connect.close();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Registration");
            alert2.setHeaderText(null);
            //خالی کردن تکست فیلدها
            txt_Field_FirstName.setText("");
            txt_Field_LastName.setText("");
            txt_Field_UserName_R.setText("");
            txt_Field_Password_R.setText("");
            txt_Field_ConfirmPassword.setText("");

            alert2.setContentText("Successfully Registration!\nyour id is : "+id);
            alert2.showAndWait();
            pane2.setVisible(false);
            pane1.setVisible(true);
            // مشکل در ثبت نام
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("ERROR");
            alert2.setHeaderText(null);
            alert2.setContentText("Registration Failed pleaes TryAgain");
            alert2.showAndWait();
        }
    }

    public void press_ForgetPassword(MouseEvent mouseEvent) {

    }

    //باز کردن صفحه ی ثبت نام
    public void open_registration(MouseEvent mouseEvent) {
        pane1.setVisible(false);
        pane2.setVisible(true);
    }


    static String id;
    person librarian2 = new person();
    private double x, y;
    public void press_Login_btn(ActionEvent actionEvent) {
        //چک کردن پر بودن فیلد های مورد نیاز
        if(txtfield_UserName.getText().equals("") || txtfield_Password.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("complate all the fields");
            alert.showAndWait();
        }
        else{
            // کانکشن به دیتابیس
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String database = "jdbc:mysql://localhost:3307/databace_test?user=root";
                Connection connect = DriverManager.getConnection(database);
                Statement state = connect.createStatement();
                String mysql = "SELECT id ,name, family,username , password FROM person2";
                boolean login = false;
                ResultSet result = state.executeQuery(mysql);
                while(result.next()){
                 //   String ID = result.getString("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String name = result.getString("name");
                    String family = result.getString("family");
                //چک کردن درستی یوزرنیم و پسورد
                    if(txtfield_UserName.getText().compareTo(username)==0 && txtfield_Password.getText().compareTo(password)==0){
                 // ست کردن اطلاعات در کلاس person مطابق با اطلاعات کاربر
                        login = true;
                        id = result.getString("id");
                        System.out.println("id geted from databace ="+id);
                        librarian2.setFirstName(name);
                        librarian2.setLastName(family);
                        librarian2.setUsername(username);
                        librarian2.setPassword(password);

                        Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                        alert5.setTitle("Hey You");
                        alert5.setContentText("Welcome to YAR ");
                        alert5.showAndWait();

                        //////////////////////////////////////////
                        // بستن لاگین پیج و باز شدن صفحه ی اصلی برنامه
                        Stage stage = (Stage) btn_Login.getScene().getWindow();
                        stage.close();
                        connect.close();
                        Stage primaryStage = new Stage();
                        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../fxml/Home.fxml"));
                        Scene scene = new Scene(root ,1050 ,576 );
                        primaryStage.setScene(scene);
                        primaryStage.initStyle(StageStyle.UNDECORATED);
                        primaryStage.show();
                        break;
                    }
                }
                state.close();
                connect.close();
                //آلارم غلط بودن یوزر یا پسوورد
                if (login==false){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("username or password is wrong");
                    alert.showAndWait();
                    txtfield_Password.setText("");
                }
                //مشکل و ارور در لاگین
            }catch(Exception e) {
                System.out.println(e);
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("ERROR");
                alert2.setHeaderText(null);
                alert2.setContentText("Login Failed pleaes TryAgain");
                alert2.showAndWait();

            }
        }
    }
    //گرفتن آی دی برای استفاده در کلاسهای دیگر
    public static String get_id(){
        System.out.println("returned id ="+ id);
        return id;
    }

}
