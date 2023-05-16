package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;


public class Controller {

     static Customer loggedInCustomer = null;
    @FXML
    Button createNewAccountButton = new Button();
    @FXML
    Button cancelButton = new Button();
    @FXML
    Button signInButton = new Button();
    @FXML
    TextField userName = new TextField();
    @FXML
    PasswordField password = new PasswordField();
    @FXML
    Label messageLabel = new Label();

    public void setSignInButton() throws IOException {
        String user = userName.getText();
        String pass = password.getText();
        loggedInCustomer = login.customerLogin(user,pass);
        if (loggedInCustomer != null)
        {
            System.out.println(loggedInCustomer);
            messageLabel.setText("Login successful");
            Stage stage = (Stage)signInButton.getScene().getWindow();
//            FileInputStream fm = new FileInputStream("C:\\Users\\HP\\IdeaProjects\\JavaFX\\src\\sample\\Table .fxml");
            Parent root = FXMLLoader.load(getClass().getResource("Table .fxml"));
            stage.setScene(new Scene(root));
        }
        else
            messageLabel.setText("Login Failed");
    }

    public void setCreateNewAccountButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        Stage stage = (Stage) createNewAccountButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    public void setCancelButton()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}