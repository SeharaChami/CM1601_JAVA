package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    @FXML
    public Label welcomeText;
    public Button AddBtn;
    public Button DeleteBtn;
    public Button UpdateBtn;
    public Button ViewInventoryBtn;
    public Button LowStockBtn;
    public Button DealerViewerBtn;

    public void onAddBtnClick(ActionEvent actionEvent) throws IOException {
        Stage previousStage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/addItem-view.fxml")));
        previousStage.setScene(new Scene(root,700,400));
    }

    public void onDeleteBtnClick(ActionEvent actionEvent) throws IOException {
        Stage previousStage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/tuktukapp/addItem-view.fxml")));
        previousStage.setScene(new Scene(root,700,400));
    }

    public void onUpdateBtnClick(ActionEvent actionEvent) {
    }

    public void onviewBtnClick(ActionEvent actionEvent) {
    }

    public void onDealerBtnClick(ActionEvent actionEvent) {
    }
}
