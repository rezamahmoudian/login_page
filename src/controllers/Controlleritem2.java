package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Controlleritem2 {

    @FXML
    private Label item2_namebook;

    @FXML
    private Label item2_vaziyat;

    @FXML
    private Label item2_writername;

    @FXML
    private Label item2_amanatdahande;

    @FXML
    private Label item2_bookID;

    @FXML
    private HBox itemC;


    public void set_bookname(String a){
        item2_namebook.setText(a);
    }

    public void set_bookwriter(String a){
        item2_writername.setText(a);
    }

    public void set_amanatdahande(String a){
        item2_amanatdahande.setText(a);
    }

    public void set_vaziyat(String a){
        item2_vaziyat.setText(a);
    }

    public void set_bookID(String a) {
        item2_bookID.setText(a);
    }
}
