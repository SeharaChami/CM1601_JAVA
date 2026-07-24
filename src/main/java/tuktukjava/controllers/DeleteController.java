package tuktukjava.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tuktukjava.FileManager;
import tuktukjava.Inventory;
import tuktukjava.Item;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DeleteController {

    @FXML private TextField searchCodeField;
    @FXML private VBox previewBox;
    @FXML private VBox confirmBox;
    @FXML private Label previewTitleLabel;
    @FXML private Label previewDetailLabel1;
    @FXML private Label previewDetailLabel2;
    @FXML private Label confirmTitleLabel;
    @FXML private Label errorLabel;
    @FXML private Label msgLabel;
    @FXML
    private Button deleteBtn;
    private Inventory inventory;
    private Item current ;

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    @FXML
    public void onFindBtnClick(ActionEvent actionEvent) throws IOException {
        String code = searchCodeField.getText().trim().toUpperCase();
        current = inventory.searchByCode(code);

        if(current == null){
            errorLabel.setText("Item "+code+" not found");
            hidePreview();
            return;
        }
        errorLabel.setText("");
        previewTitleLabel.setText(current.item[0] + " — " + current.item[1]);
        previewDetailLabel1.setText("Brand: " + current.item[2] + " | Price: " + current.item[3] + " | Qty: " + current.item[4] + " | Category: " + current.item[5]);
        previewDetailLabel2.setText("Date: " + current.item[6] + " | Image: " + current.item[7]);
        confirmTitleLabel.setText("Delete " + current.item[0] + "?");

        showPreview();

    }
    private void showPreview() {
        previewBox.setVisible(true);
        previewBox.setManaged(true);
        confirmBox.setVisible(true);
        confirmBox.setManaged(true);
        deleteBtn.setVisible(true);
        deleteBtn.setManaged(true);
    }

    private void hidePreview() {
        previewBox.setVisible(false);
        previewBox.setManaged(false);
        confirmBox.setVisible(false);
        confirmBox.setManaged(false);
        deleteBtn.setVisible(false);
        deleteBtn.setManaged(false);
    }
@FXML
    public void onBackBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/tuktukapp/homePage-view.fxml")));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        controller.setInventory(this.inventory);
        stage.setScene(new Scene(root, 730, 500));
        stage.show();
    }
@FXML
    public void onDeleteBtnClick(ActionEvent actionEvent) throws IOException {
        if (current == null){return;}
        inventory.delete(current);

        msgLabel.setText("Item " + current.item[0] + " deleted.");
        hidePreview();
        searchCodeField.clear();
        current = null;
    }
}
