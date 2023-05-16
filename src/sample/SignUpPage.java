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
import org.w3c.dom.Text;

import java.io.IOException;

public class SignUpPage {

    @FXML
    Button signInButton = new Button();
    @FXML
    Button cancelButton = new Button();
    @FXML
    Button signUpButton = new Button();
    @FXML
    TextField nameField = new TextField();
    @FXML
    TextField emailField = new TextField();
    @FXML
    TextField mobileField = new TextField();
    @FXML
    PasswordField passwordField = new PasswordField();
    @FXML
    TextField addressField = new TextField();
    @FXML
    Label message = new Label();


    public void setSignUpButton()
    {
        boolean ans = signUp(nameField.getText(),emailField.getText(),mobileField.getText(),passwordField.getText(),addressField.getText());
        if(ans)
        {
            message.setText("Sign up successful");
        }
        else {
            message.setText("Problem in Sign up...retry");
        }
    }
    public  boolean signUp(String name, String email,String mobile,String password,String address)
    {
        if(!password.isEmpty() && !email.isEmpty()) {
            String query = "insert into customer(name,email,mobile,password,address) values('" + name + "','" + email + "','" + mobile + "','" + password + "','" + address + "');";
            System.out.println(query);
            return DatabaseConnection.insertUpdate(query);
        }
        else
            return false;
    }

    public void setCancelButton()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setSignInButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
