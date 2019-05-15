package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.scene.control.TableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.guice.PersistanceModule;
import game.GameResult;
import game.GameResultDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatController {

    GameResultDao gameResultDao;
    @FXML
    public TableColumn dateColumn;
    @FXML
    public TableColumn topicColumn;
    @FXML
    public TableColumn pointColumn;
    @FXML
    private TableView<GameResult> gameResultTable;

    private Logger logger = LoggerFactory.getLogger(StatController.class);

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/View.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Angol gyakorlás");
        stage.show();
    }

    @FXML
    public void initialize(){
        try {
            Injector injector = Guice.createInjector(new PersistanceModule("jpa-persistence-unit-1"));
            gameResultDao = injector.getInstance(GameResultDao.class);

            List<GameResult> toptenList = gameResultDao.findMostRecent(20);

            topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
            pointColumn.setCellValueFactory(new PropertyValueFactory<>("point"));

            dateColumn.setCellFactory(column -> {
                TableCell<GameResult, ZonedDateTime> cell = new TableCell<GameResult, ZonedDateTime>() {
                    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss (Z)");

                    @Override
                    protected void updateItem(ZonedDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        }
                        else {
                            setText(item.format(formatter));
                        }
                    }
                };

                return cell;
            });



            ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
            observableResult.addAll(toptenList);
            gameResultTable.setItems(observableResult);
            logger.info("Sikeres kapcsolódás az adatbázishoz");
        }catch (Exception e){
            logger.error("Az adatbázishoz való kapcsolódás sikertelen");
        };

    }






}
