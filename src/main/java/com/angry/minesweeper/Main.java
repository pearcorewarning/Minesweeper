package com.angry.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private static Stage menuWindow;
    private static Stage playWindow;

    @Override
    public void start(Stage window) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 640, 500);
        menuWindow = window;
        window.setTitle("JavaFX扫雷");
        window.setScene(scene);
        window.setMinWidth(350);
        window.setMinHeight(400);
        window.show();

        Label title = new Label("扫雷");
        Label titleEN = new Label("Minesweeper");
        title.setStyle("-fx-font-size: 100px;" +
                       "-fx-font-family: \"KaiTi\"");
        titleEN.setStyle("-fx-font-size: 30px;" +
                         "-fx-font-family: \"Comic Sans MS\"");

        Button easy = new Button("简单");
        Button medium = new Button("中等");
        Button difficult = new Button("困难");
        Button custom = new Button("自定义");
        easy.setStyle("-fx-border-color: #000000;" +
                      "-fx-background-color: #baba18;");
        medium.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #baba18;");
        difficult.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #baba18;");
        custom.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #baba18;");
        easy.setPrefSize(350, 30);
        medium.setPrefSize(350, 30);
        difficult.setPrefSize(350, 30);
        custom.setPrefSize(350, 30);

        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);
        root.getChildren().addAll(title, titleEN, easy, medium, difficult, custom);

        easy.setOnMouseEntered(e -> easy.setPrefSize(400, 40));
        easy.setOnMouseExited(e -> easy.setPrefSize(350, 30));
        easy.setOnMouseClicked(e -> {
            window.hide();
            play(9, 9, 10);
        });
        medium.setOnMouseEntered(e -> medium.setPrefSize(400, 40));
        medium.setOnMouseExited(e -> medium.setPrefSize(350, 30));
        medium.setOnMouseClicked(e -> {
            window.hide();
            play(16, 16, 40);
        });
        difficult.setOnMouseEntered(e -> difficult.setPrefSize(400, 40));
        difficult.setOnMouseExited(e -> difficult.setPrefSize(350, 30));
        difficult.setOnMouseClicked(e -> {
            window.hide();
            play(30, 16, 99);
        });
        custom.setOnMouseEntered(e -> custom.setPrefSize(400, 40));
        custom.setOnMouseExited(e -> custom.setPrefSize(350, 30));
        custom.setOnMouseClicked(e -> custom());

        scene.heightProperty().addListener((observable, oldValue, newValue) -> root.setSpacing(scene.heightProperty().get()/20));
    }

    private void custom() {
        Stage customWindow = new Stage();
        VBox root = new VBox();
        HBox widthLine = new HBox();
        HBox heightLine = new HBox();
        HBox mineLine = new HBox();
        HBox buttonLine = new HBox();
        Label title = new Label("自定义");
        Label width = new Label("宽：");
        Label height = new Label("高：");
        Label mine = new Label("雷：");
        TextField widthInput = new TextField();
        TextField heightInput = new TextField();
        TextField mineInput = new TextField();
        Button menu = new Button("主菜单");
        Button play = new Button("开始游戏");

        customWindow.setScene(new Scene(root, 550, 350));
        customWindow.setTitle("自定义");
        customWindow.show();

        title.setStyle("-fx-font-size: 50px;" +
                "-fx-font-family: \"KaiTi\";");

        width.setStyle("-fx-font-size: 20px;" +
                "-fx-font-family: \"FangSong\"");
        height.setStyle("-fx-font-size: 20px;" +
                "-fx-font-family: \"FangSong\"");
        mine.setStyle("-fx-font-size: 20px;" +
                "-fx-font-family: \"FangSong\"");

        menu.setPrefSize(250, 30);
        menu.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #baba18;");
        play.setPrefSize(250, 30);
        play.setStyle("-fx-border-color: #000000;" +
                "-fx-background-color: #baba18;");

        widthInput.setPromptText("(9~30)");
        heightInput.setPromptText("(9~24)");
        mineInput.setPromptText("(10~668)");

        widthLine.getChildren().addAll(width, widthInput);
        heightLine.getChildren().addAll(height, heightInput);
        mineLine.getChildren().addAll(mine, mineInput);
        buttonLine.getChildren().addAll(menu, play);

        widthLine.setAlignment(Pos.CENTER);
        heightLine.setAlignment(Pos.CENTER);
        mineLine.setAlignment(Pos.CENTER);
        buttonLine.setAlignment(Pos.CENTER);
        buttonLine.setSpacing(25);

        root.getChildren().addAll(title, widthLine, heightLine, mineLine, buttonLine);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(40);

        menu.setOnMouseEntered(e -> menu.setPrefSize(300, 35));
        menu.setOnMouseExited(e -> menu.setPrefSize(250, 30));
        menu.setOnMouseClicked(e -> customWindow.close());
        play.setOnMouseEntered(e -> play.setPrefSize(300, 35));
        play.setOnMouseExited(e -> play.setPrefSize(250, 30));
        play.setOnMouseClicked(e -> {
            int widthInt = Integer.parseInt(widthInput.getText());
            int heightInt = Integer.parseInt(heightInput.getText());
            int mineInt = Integer.parseInt(mineInput.getText());

            if(9>widthInt || widthInt>30 || 9>heightInt || heightInt>24 || 10>mineInt || mineInt>widthInt*heightInt-52) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误！");
                alert.setHeaderText("数值超出范围！");
                alert.showAndWait();
            } else {
                play(widthInt, heightInt, mineInt);
                customWindow.close();
            }
        });
    }

    private void play(int width, int height, int mine) {
        playWindow = new Stage();
        VBox root = new VBox();
        GridPane center = new GridPane();
        Button menu = new Button("主菜单");
        Lattice[][] lattices = new Lattice[width][height];

        playWindow.setScene(new Scene(root, width*20, height*40));
        playWindow.setTitle("游戏开始啦！");
        playWindow.setMinWidth(width*40);
        playWindow.setMinHeight(height*45);
        playWindow.setFullScreen(true);
        playWindow.show();

        for(int traverseWidth = 0; traverseWidth < width; traverseWidth++) {
            for(int traverseHeight = 0; traverseHeight < height; traverseHeight++) {
                lattices[traverseWidth][traverseHeight] = new Lattice(lattices, traverseWidth, traverseHeight, mine);
                center.add(lattices[traverseWidth][traverseHeight], traverseWidth, traverseHeight);
            }
        }

        for(int index = 0;index < mine;index++) {
            int x = new Random().nextInt(width);
            int y = new Random().nextInt(height);

            if(lattices[x][y].getHasMine())
                index--;
            else
                lattices[x][y].setHasMine();
        }

        for(int traverseWidth = 0; traverseWidth < width; traverseWidth++) {
            for(int traverseHeight = 0; traverseHeight < height; traverseHeight++) {
                byte value = getValue(lattices, traverseWidth, traverseHeight);
                lattices[traverseWidth][traverseHeight].setValue(value);
            }
        }

        menu.setPrefSize(250, 30);
        menu.setStyle("-fx-border-color: #000000;" +
                      "-fx-background-color: #baba18;");

        root.getChildren().addAll(center, menu);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);

        center.setAlignment(Pos.CENTER);

        menu.setOnMouseEntered(e -> menu.setPrefSize(300, 35));
        menu.setOnMouseExited(e -> menu.setPrefSize(250, 30));
        menu.setOnMouseClicked(e -> {
            menuWindow.show();
            playWindow.close();
        });
    }

    private static byte getValue(Lattice[][] lattices, int traverseWidth, int traverseHeight) {
        byte value = 0;
        try {
            if (lattices[traverseWidth - 1][traverseHeight - 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            if (lattices[traverseWidth][traverseHeight - 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            assert lattices[traverseWidth + 1] != null;
            if (lattices[traverseWidth + 1][traverseHeight - 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            if (lattices[traverseWidth - 1][traverseHeight].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            assert lattices[traverseWidth + 1] != null;
            if (lattices[traverseWidth + 1][traverseHeight].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            if (lattices[traverseWidth - 1][traverseHeight + 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            if (lattices[traverseWidth][traverseHeight + 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        try {
            assert lattices[traverseWidth + 1] != null;
            if (lattices[traverseWidth + 1][traverseHeight + 1].getHasMine())
                value++;
        } catch (Exception ignored) {}
        return value;
    }

    public static void over() {
        menuWindow.show();
        playWindow.close();
    }

    public static void main(String[] args) {
        launch();
    }
}