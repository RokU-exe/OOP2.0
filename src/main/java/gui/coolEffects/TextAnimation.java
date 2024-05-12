package gui.coolEffects;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.Random;

public class TextAnimation implements Runnable {

    private String text;
    private int animationTime;
    private Text textField;

    private Random random = new Random();

    public TextAnimation(String text, int animationTime, Text textField) {
        this.text = text;
        this.animationTime = animationTime;
        this.textField = textField;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= text.length(); i++) {
                final int currentIndex = i;
                Platform.runLater(() -> textField.setText(text.substring(0, currentIndex)));
                Thread.sleep(animationTime + random.nextInt(150));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
