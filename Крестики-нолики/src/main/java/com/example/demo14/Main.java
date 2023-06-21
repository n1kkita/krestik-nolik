package com.example.demo14;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    private static String turn = "Хрестик";
    private GridPane gridPane;
    private int moveCounter = 0;
    private boolean gameOver = false;
    private Button reset = new Button("Сбросить");
    private Label showTurn = new Label("Ходят: " + turn);
    private List<Button> buttonList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Создание GridPane
        Pane pane = new Pane();
        gridPane = new GridPane();
        gridPane.setLayoutX(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setGridLinesVisible(true);


        // Создание кнопок и добавление их в сетку
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                buttonList.add(button);
                // Установка стилей для кнопки
                button.setStyle("-fx-background-color: rgba(38,38,38,0); -fx-text-fill: white;");
                button.setFont(Font.font(30.0));
                button.setOnMouseClicked(event -> {

                    if(button.getText().equals("")&&!gameOver) {
                        if (turn.equals("Хрестик")) {
                            button.setText("X");
                            turn = "Нолик";
                            moveCounter++;
                        } else {
                            turn = "Хрестик";
                            button.setText("O");
                            moveCounter++;
                        }
                        showTurn.setText("Ходят: " + turn);
                        checkWin();
                        if(moveCounter == 9 && !gameOver){
                            showWinnerDialog("Ничья!");
                        }
                    }
                });
                button.setMinSize(80, 80);
                gridPane.add(button, col, row);
            }
        }
        reset.setLayoutX(200);
        reset.setLayoutY(280);
        reset.setFont(Font.font(12.0));
        reset.setStyle("-fx-background-color: #5b5b5b; -fx-border-color: white; -fx-text-fill: white;");

        showTurn.setLayoutX(10);
        showTurn.setLayoutY(280);
        showTurn.setFont(Font.font(15));
        showTurn.setStyle("-fx-text-fill: white;");

        reset.setOnMouseClicked(event -> {
            gameOver = false;
            turn = "Хрестик";
            showTurn.setText("Ходят: " + turn);
            moveCounter = 0;
            buttonList.stream().forEach(button -> button.setText(""));
        });

        pane.setBackground(new Background(new BackgroundFill(Color.rgb(109,79,187), null, null)));
        pane.getChildren().add(gridPane);
        pane.getChildren().add(reset);
        pane.getChildren().add(showTurn);

        // Создание сцены и отображение
        Scene scene = new Scene(pane,280,330);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Крестики нолики");
        primaryStage.show();
    }

    void checkWin(){
        Button[][] buttonMatrix = new Button[3][3];

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer rowIndex = GridPane.getRowIndex(button);
                Integer columnIndex = GridPane.getColumnIndex(button);

                if (rowIndex != null && columnIndex != null) {
                    buttonMatrix[rowIndex][columnIndex] = button;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!buttonMatrix[i][0].getText().isEmpty() && buttonMatrix[i][0].getText().equals(buttonMatrix[i][1].getText())
                    && buttonMatrix[i][1].getText().equals(buttonMatrix[i][2].getText())) {
                showWinnerDialog(buttonMatrix[i][0].getText() + " Победили! По ряду");
                break;
            } else if (!buttonMatrix[0][i].getText().isEmpty() && buttonMatrix[0][i].getText().equals(buttonMatrix[1][i].getText())
                    && buttonMatrix[1][i].getText().equals(buttonMatrix[2][i].getText())) {
                showWinnerDialog(buttonMatrix[0][i].getText() + " Победили! По столбцу");
                break;
            } else if (!buttonMatrix[0][0].getText().isEmpty() && buttonMatrix[0][0].getText().equals(buttonMatrix[1][1].getText())
                    && buttonMatrix[1][1].getText().equals(buttonMatrix[2][2].getText())) {
                showWinnerDialog(buttonMatrix[0][0].getText() + " Победили! По главной диагонали");
                break;
            } else if (!buttonMatrix[1][1].getText().isEmpty() && buttonMatrix[0][2].getText().equals(buttonMatrix[1][1].getText())
                    && buttonMatrix[1][1].getText().equals(buttonMatrix[2][0].getText())) {
                showWinnerDialog(buttonMatrix[0][2].getText() + " Победили! По второстепенной диагонали");
                break;
            }
        }
    }
    private void showWinnerDialog(String message) {
        String[] str = message.split("");
        boolean win = Arrays.stream(str).anyMatch(s -> s.equals("X") || s.equals("O"));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(win) {
            str = Arrays.stream(str).map(s -> {
                if (s.equals("X")) {
                    s = "Хрестики";
                } else if (s.equals("O")) {
                    s = "Нолики";
                }
                return s;
            }).collect(Collectors.toList()).toArray(new String[0]);
        } else {
            message = "Ничья!";
        }
        gameOver = true;
        message = Arrays.stream(str).collect(Collectors.joining());
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
