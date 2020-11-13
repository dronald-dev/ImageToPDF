package ua.dronald.imagetopdf;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressSave {

    private Label progressLabel;
    private ProgressBar progressBar;
    private double counter;

    public ProgressSave(Label progressLabel, ProgressBar progressBar) {
        this.progressLabel = progressLabel;
        this.progressBar = progressBar;
        this.counter = 0.1; // Default value
    }

    public void setAmountFiles(double amount) {
        counter = 1f / amount;
    }

    public void addProgress() {
        progressBar.setProgress(progressBar.getProgress() + counter);
    }

    public void setMessage(String message) {
        progressLabel.setText(message);
        //progressLabel.setLayoutX(progressLabel.getParent().getLayoutBounds().getWidth() / 2 - progressLabel.getWidth() / 2);
    }

}
