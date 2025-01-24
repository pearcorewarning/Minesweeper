package com.angry.minesweeper;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class Lattice extends Button {
    private boolean hasMine = false;
    private boolean clicked = false;
    private boolean guessed = false;
    private byte value = 0;
    private final Lattice[][] lattices;
    private final int x;
    private final int y;
    private final int mine;
    private long doubleClickTime = 0L;

    public Lattice(Lattice[][] lattices, int x, int y, int mine) {
        super();

        this.lattices = lattices;
        this.x = x;
        this.y = y;
        this.mine = mine;

        this.setPrefSize(40, 40);
        this.setFont(new Font("Comic Sans MS", 15));
        this.setStyle("-fx-background-color: #00adff;" +
                "-fx-border-color: #000000;");

        this.setOnMouseClicked(e -> {
            if(clicked) {
                if(System.currentTimeMillis() - doubleClickTime < 300) {
                    if(value == getGuessValue())
                        roundClick();
                }
                doubleClickTime = System.currentTimeMillis();
            } else {
                if(e.getButton() == MouseButton.SECONDARY) {
                    if(guessed)
                        this.setStyle("-fx-background-color: #00adff;" +
                                "-fx-border-color: #000000;");
                    else
                        this.setStyle("-fx-background-color: #ffbf00;" +
                                "-fx-border-color: #000000;");
                    guessed = !guessed;
                } else {
                    if(!guessed) {
                        clicked = true;
                        onClick();
                    }
                }
            }
        });
    }

    public void onClick() {
        if(guessed)
            return;
        clicked = true;
        this.setStyle("-fx-background-color: #FFFFFF;" +
                "-fx-border-color: #000000;");
        if (hasMine) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Lattice.class.getResource("Boom.wav"))));
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ignored) {}
            this.setStyle("-fx-background-color: #FF0000;" +
                    "-fx-border-color: #000000;");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("你输了");
            alert.setHeaderText("你碰到了位于" + (x+1) + "行" + (y+1) + "列的雷！");
            alert.showAndWait();
            Main.over();
        } else {
            int countClicked = 0;
            if (value == 0 && !getHasMine())
                roundClick();
            else
                this.setText(String.valueOf(value));
            for (Lattice[] lattice : lattices)
                for (int traversHeight = 0; traversHeight < lattices[0].length; traversHeight++)
                    if (lattice[traversHeight].getClicked())
                        countClicked++;
            if(lattices.length*lattices[0].length-countClicked == mine) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("你赢了");
                alert.setHeaderText("恭喜！");
                alert.showAndWait();
                Main.over();
            }
        }
    }

    private void roundClick() {
        try {
            if (!lattices[x - 1][y - 1].getClicked())
                lattices[x - 1][y - 1].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x][y - 1].getClicked())
                lattices[x][y - 1].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x + 1][y - 1].getClicked())
                lattices[x + 1][y - 1].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x - 1][y].getClicked())
                lattices[x - 1][y].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x + 1][y].getClicked())
                lattices[x + 1][y].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x - 1][y + 1].getClicked())
                lattices[x - 1][y + 1].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x][y + 1].getClicked())
                lattices[x][y + 1].onClick();
        } catch (Exception ignored) {}
        try {
            if (!lattices[x + 1][y + 1].getClicked())
                lattices[x + 1][y + 1].onClick();
        } catch (Exception ignored) {}
    }

    private byte getGuessValue() {
        byte guessValue = 0;
        try {
            if (lattices[x - 1][y - 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x][y - 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x + 1][y - 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x - 1][y].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x + 1][y].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x - 1][y + 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x][y + 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        try {
            if (lattices[x + 1][y + 1].getGuessed())
                guessValue++;
        } catch (Exception ignored) {}
        return guessValue;
    }

    public void setHasMine() {
        hasMine = true;
    }

    public boolean getHasMine() {
        return hasMine;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public boolean getClicked() {
        return clicked;
    }

    public boolean getGuessed() {
        return guessed;
    }
}
