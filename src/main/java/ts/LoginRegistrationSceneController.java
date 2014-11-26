/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import com.cloudshare.useroperations.webservices.LoginDTO;
import com.cloudshare.useroperations.webservices.LoginResponse;
import com.cloudshare.useroperations.webservices.RegistrationDTO;
import com.cloudshare.useroperations.webservices.RegistrationResponse;
import com.cloudshare.useroperations.webservices.UserOperationsService_Service;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author ravjotsingh
 */
public class LoginRegistrationSceneController implements Initializable, IScreenController {

    private SceneNavigator myController;

    @FXML
    TextField loginEmailAddressTextField;
    @FXML
    PasswordField loginPasswordField;
    @FXML
    TextField registrationFullNameField;
    @FXML
    TextField registrationEmailAddressField;
    @FXML
    PasswordField registratonPasswordField;
    @FXML
    PasswordField registrationRePasswordField;
    @FXML
    BorderPane registrationPane;
    @FXML
    BorderPane forgotPasswordPane;
    @FXML
    BorderPane resetPasswordPane;
    @FXML
    TextField forgotPasswordEmailAddressField;
    @FXML
    TextField forgotPasswordPassCodeField;
    @FXML
    PasswordField forgotPasswordPasswordField;
    @FXML
    PasswordField forgotPasswordRePasswordField;

    private Stage stage;

    private LoginRegistrationBinding loginRegistrationBinding = new LoginRegistrationBinding();

    private String requestType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bindings.bindBidirectional(loginEmailAddressTextField.textProperty(), loginRegistrationBinding.loginEmailAddressProperty());
        Bindings.bindBidirectional(loginPasswordField.textProperty(), loginRegistrationBinding.loginPasswordProperty());

        Bindings.bindBidirectional(registrationFullNameField.textProperty(), loginRegistrationBinding.registrationFullNameProperty());
        Bindings.bindBidirectional(registrationEmailAddressField.textProperty(), loginRegistrationBinding.registrationEmailAddressProperty());
        Bindings.bindBidirectional(registratonPasswordField.textProperty(), loginRegistrationBinding.registrationPasswordProperty());
        Bindings.bindBidirectional(registrationRePasswordField.textProperty(), loginRegistrationBinding.registrationRePasswordProperty());

        Bindings.bindBidirectional(forgotPasswordEmailAddressField.textProperty(), loginRegistrationBinding.forgotPasswordEmailAddressProperty());
        Bindings.bindBidirectional(forgotPasswordPassCodeField.textProperty(), loginRegistrationBinding.forgotPasswordPassCodeProperty());
        Bindings.bindBidirectional(forgotPasswordPasswordField.textProperty(), loginRegistrationBinding.forgotPasswordPasswordProperty());
        Bindings.bindBidirectional(forgotPasswordRePasswordField.textProperty(), loginRegistrationBinding.forgotPasswordRePasswordProperty());

        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(true);
    }

    @Override
    public void setScreenParent(SceneNavigator screenPage) {
        this.myController = screenPage;
    }

    @FXML
    public void loginUser() {
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        registrationFullNameField.setText(null);
        registrationEmailAddressField.setText(null);
        registratonPasswordField.setText(null);
        registrationRePasswordField.setText(null);
        System.out.println(loginRegistrationBinding.getLoginEmailAddress());
        System.out.println(loginRegistrationBinding.getLoginPassword());
        try {
            if (loginRegistrationBinding.getLoginEmailAddress() != null && loginRegistrationBinding.getLoginEmailAddress().trim().length() > 0) {
                if (loginRegistrationBinding.getLoginPassword() != null && loginRegistrationBinding.getLoginPassword().trim().length() > 0) {
                    Task task = new Task<Void>() {
                        @Override
                        public Void call() {
                            try {
                                UserOperationsService_Service port = new UserOperationsService_Service();
                                LoginDTO dto = new LoginDTO();
                                dto.setPassword(loginRegistrationBinding.getLoginPassword());
                                dto.setUserEmailAddress(loginRegistrationBinding.getLoginEmailAddress());
                                dto.setRequestType("LOGIN");
                                LoginResponse response = port.getUserOperationsPort().authenticateUser(dto);
                                System.out.println(response.getRequestStatus());
                                if (response.getRequestStatus() != null && response.getRequestStatus().equals("SUCCESS")) {
                                    //message("Success", "Welcome to CloudDrive");
                                    hideWindow();
                                    new ExecuteCheck(loginRegistrationBinding.getLoginEmailAddress(), loginRegistrationBinding.getLoginPassword()).start();
                                } else {
                                    message("Error", response.getErrorMessage());
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                message("Error", "Oops! Something went wrong");
                            }
                            return null;
                        }
                    };
                    new Thread(task).start();
                } else {
                    InvokeAnimation.attentionSeekerWobble(loginPasswordField);
                    loginPasswordField.setPromptText("Enter Password");
                }
            } else {
                InvokeAnimation.attentionSeekerWobble(loginEmailAddressTextField);
                loginEmailAddressTextField.setPromptText("Enter EmailID");
            }

        } catch (Exception ex) {
            Logger.getLogger(LoginRegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void registerUser() {
        loginEmailAddressTextField.setText(null);
        loginPasswordField.setText(null);
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        System.out.println(loginRegistrationBinding.getRegistrationEmailAddress());
        System.out.println(loginRegistrationBinding.getRegistrationFullName());
        System.out.println(loginRegistrationBinding.getRegistrationPassword());
        System.out.println(loginRegistrationBinding.getRegistrationRePassword());
        try {

            if (loginRegistrationBinding.getRegistrationEmailAddress() != null && loginRegistrationBinding.getRegistrationEmailAddress().trim().length() > 0) {
                if (loginRegistrationBinding.getRegistrationFullName() != null && loginRegistrationBinding.getRegistrationFullName().trim().length() > 0) {
                    if (loginRegistrationBinding.getRegistrationRePassword() != null && loginRegistrationBinding.getRegistrationRePassword().trim().length() > 0
                            && loginRegistrationBinding.getRegistrationPassword() != null && loginRegistrationBinding.getRegistrationPassword().trim().length() > 0) {
                        if (loginRegistrationBinding.getRegistrationRePassword().equals(loginRegistrationBinding.getRegistrationPassword())) {
                            Task task = new Task<Void>() {
                                @Override
                                public Void call() {
                                    try {
                                        RegistrationDTO request = new RegistrationDTO();
                                        request.setUserFullName(loginRegistrationBinding.getRegistrationFullName());
                                        request.setUserPassword(loginRegistrationBinding.getRegistrationPassword());
                                        request.setRequestType("REGISTRATION");
                                        request.setUserEmailAddress(loginRegistrationBinding.getRegistrationEmailAddress());
                                        UserOperationsService_Service port = new UserOperationsService_Service();
                                        RegistrationResponse response = port.getUserOperationsPort().registerUser(request);
                                        System.out.println(response.getRequestStatus());
                                        if (response.getRequestStatus() != null && response.getRequestStatus().equals("SUCCESS")) {
                                            message("Success", "Thank you for registering. An email is sent to " + loginRegistrationBinding.getRegistrationEmailAddress() + " for email verification");
                                        } else {
                                            message("Error", response.getErrorMessage());
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        message("Error", "Oops! Something went wrong");
                                    }
                                    return null;
                                }
                            };
                            new Thread(task).start();
                        } else {
                            InvokeAnimation.attentionSeekerShake(registratonPasswordField);
                            InvokeAnimation.attentionSeekerShake(registrationRePasswordField);
                            registratonPasswordField.setPromptText("Password Do Not Match");
                            registrationRePasswordField.setPromptText("Password Do Not Match");
                        }
                    } else {
                        InvokeAnimation.attentionSeekerWobble(registratonPasswordField);
                        registratonPasswordField.setPromptText("Enter Password");

                        InvokeAnimation.attentionSeekerWobble(registrationRePasswordField);
                        registrationRePasswordField.setPromptText("Enter Password");
                    }
                } else {
                    InvokeAnimation.attentionSeekerWobble(registrationFullNameField);
                    registrationFullNameField.setPromptText("Enter Full Name");
                }

            } else {
                InvokeAnimation.attentionSeekerWobble(registrationEmailAddressField);
                registrationEmailAddressField.setPromptText("Enter EmailID");

            }
        } catch (Exception ex) {
            Logger.getLogger(LoginRegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hideWindow() {
        Platform.runLater(new Runnable() {
            public void run() {
                loginPasswordField.getScene().getWindow().hide();
            }
        });
    }

   

    public void successMoveToResetPassword() {
        moveToResetPassword();
    }

  

    public void message(final String title, final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Button b = new Button("Ok.");
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        dialogStage.hide();
                    }
                });
                dialogStage.setScene(new Scene(VBoxBuilder.create().
                        children(new Text(message), b).
                        alignment(Pos.CENTER).padding(new Insets(5)).build()));
                dialogStage.show();
                Platform.setImplicitExit(false);

                dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        event.consume();
                    }
                });

//Dialogs.showErrorDialog(null, null, message, "Error");
            }
        });
    }

    @FXML
    public void goToForgotPassword() {
        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(true);
        registrationPane.setVisible(false);
    }

    @FXML
    public void forgotPassword() {

    }

    public void moveToResetPassword() {
        resetPasswordPane.setVisible(true);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(false);
    }

    @FXML
    public void resetPassword() {

    }

    @FXML
    public void viewTermsAndConditions() {
        stage = new Stage();
        Group root = new Group();
        root.getChildren().addAll(myController.getNodeScreen("tncTextViewerScreen"));
        root.autosize();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Terms And Conditions");
        //stage.getIcons().add(new Image(GlobalConstants.stageIconImageLocation));
        stage.centerOnScreen();
        stage.setFocused(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void viewAboutUs() {
        stage = new Stage();
        Group root = new Group();
        root.getChildren().addAll(myController.getNodeScreen("aboutusTextViewerScreen"));
        root.autosize();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("About Us");
        //stage.getIcons().add(new Image(GlobalConstants.stageIconImageLocation));
        stage.centerOnScreen();
        stage.setFocused(true);
        stage.show();
    }

    @FXML
    public void returnToSignUp() {
        loginEmailAddressTextField.setText(null);
        loginPasswordField.setText(null);
        registrationFullNameField.setText(null);
        registrationEmailAddressField.setText(null);
        registratonPasswordField.setText(null);
        registrationRePasswordField.setText(null);
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(true);
    }

    @Override
    public Object populateDTO() {
        return null;
    }

    @Override
    public boolean validateRequest() throws Exception {
        return true;
    }
}
