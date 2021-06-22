package controllers;

import classes.personbooks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.ResourceBundle;

public class itemBookCtrl implements Initializable {

    @FXML
    private HBox itemBook;

    @FXML
    private Label bookNumLBL;

    @FXML
    private Label bookNameLBL;

    @FXML
    private Label bookAuthorLBL;

    @FXML
    private Label bookStatusLBL;

    @FXML
    private Button editBTN;

    @FXML
    private Button detailsBTN;

    @FXML
    private Button deleteBTN;


/*
    public void setItemBook(personbooks book){
        bookNumLBL.setText(book.getbookid());
        bookNameLBL.setText(book.getKtbName());
        bookAuthorLBL.setText(book.getKtbNevisande());
        bookStatusLBL.setText(book.getKtbVazeit());

    }

 */


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editBTN.setOnAction(e ->{
            bookNameLBL.setText("کتاب " + bookNumLBL.getText());
        });

        detailsBTN.setOnAction(e ->{
            System.out.println(bookNumLBL.getText());
        });

        deleteBTN.setOnAction(e ->{
            System.out.println(bookNameLBL.getText());
            itemBook.setVisible(false);
        });


    }
}

