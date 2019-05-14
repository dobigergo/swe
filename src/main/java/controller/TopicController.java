package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import topic.Topic;
import topic.TopicLoader;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopicController implements Initializable {

    @FXML
    private TableView<Topic> topics;
    @FXML
    private TableColumn<Topic,String> topic;
    @FXML
    private TableColumn<Topic,String> level;
    @FXML
    private Label errorLabel;

    private String selectedTopic;

    private static Logger logger = LoggerFactory.getLogger(TopicController.class);

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/View.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Angol gyakorlás");
        stage.show();
    }

    public void  start(ActionEvent actionEvent) throws IOException {
        if (selectedTopic==null) {
            errorLabel.setText("Nincs választott Téma");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().initialize(selectedTopic);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(selectedTopic);
            stage.show();
        }
    }


    @FXML
    public void clickItem(int event){
        topics.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                selectedTopic = topics.getSelectionModel().getSelectedItem().getName();
            }
        });
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            TopicLoader topicLoader = new TopicLoader();
            logger.info("A témakörök sikeresen betöltve");
            ObservableList<Topic> data = FXCollections.observableArrayList(topicLoader.getTopics());
            topic.setCellValueFactory(new PropertyValueFactory<>("name"));
            level.setCellValueFactory(new PropertyValueFactory<>("level"));
            topics.setItems(data);
            clickItem(MouseEvent.MOUSE_CLICKED);
        } catch (UnsupportedEncodingException e) {
            logger.error("A témakörök betöltése sikertelen");
        }
    }

}
