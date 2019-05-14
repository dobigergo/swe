package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import util.guice.PersistanceModule;
import game.GameResult;
import game.GameResultDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import word.Vocab;
import word.Word;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Collections;


public class GameController {
    private Vocab vocab;
    private Label[] labels;
    private int players_Point;
    private int counter;
    private int max;
    private Word word;
    private String selectedTopic;
    private GameResultDao gameResultDao;
    @FXML
    Label word1;
    @FXML
    Label word2;
    @FXML
    Label word3;
    @FXML
    Label point2;
    @FXML
    Label point1;
    @FXML
    Label errorLabel;
    @FXML
    TextField answer;
    @FXML
    Pane popPane;
    @FXML
    Label finalPointLabel;
    @FXML
    Label finalTextLabel;
    @FXML
    Button ok;
    @FXML
    AnchorPane mainPane;


    public void initialize(String selectedTopic) throws UnsupportedEncodingException {
        this.selectedTopic=selectedTopic;
        vocab = new Vocab("/"+selectedTopic);
        Collections.shuffle(vocab.getAll());
        max=vocab.cnt();
        point2.setText(""+vocab.cnt()*2);
        Injector injector = Guice.createInjector(new PersistanceModule("jpa-persistence-unit-1"));
        gameResultDao = injector.getInstance(GameResultDao.class);



        counter=0;

        labels = new Label[]{word1,word2,word3};
        players_Point =0;
        point1.setText(""+players_Point);

        gameState(vocab);
    }


    public void gameState(Vocab vocab) {

        for (Label l : labels)
            l.setText("");
        if (counter == max) {
            popPane.setVisible(true);
            finalPointLabel.setVisible(true);
            finalPointLabel.setText(String.valueOf(players_Point));
            finalTextLabel.setVisible(true);
            ok.setVisible(true);
            mainPane.setOpacity(0.4);
            mainPane.setDisable(true);
        }
        else{
            word = vocab.getAll().get(counter);
            for (int i = 0; i < 3; i++)
                if (i < word.getHun().size())
                    labels[i].setText(word.getHun().get(i));
    }
        point1.setText(""+ players_Point);
        answer.setText("");
        counter++;
    }

    public void answer(){
        errorLabel.setText("");
        if(answer.getText().equals(""))
           errorLabel.setText("Nem adtál meg választ");
       else {
           int point = word.checkPoint(answer.getText());
           players_Point+=point;
           if(point != 2)
               errorLabel.setText(word.getEng());
           gameState(vocab);
       }
    }

    private GameResult getResult(){
        GameResult result = GameResult.builder().point(this.players_Point).topic(this.selectedTopic).created(ZonedDateTime.now()).build();
        return result;
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        gameResultDao.persist(getResult());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/View.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Angol gyakorlás");
        stage.show();
    }


}
