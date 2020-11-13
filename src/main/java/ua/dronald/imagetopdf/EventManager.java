package ua.dronald.imagetopdf;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.dronald.imagetopdf.controllers.ImageToPDFController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static ua.dronald.imagetopdf.utils.TableViewUtils.swapElements;

public class EventManager {

    private ImageToPDFController controller;
    private SimpleDateFormat simpleDateFormat;
    private double dx, dy;
    private TableElement draggingElement;

    public EventManager(ImageToPDFController imageToPDFController) {
        this.controller = imageToPDFController;


        simpleDateFormat = new SimpleDateFormat(ImageToPDFController.datePattern);

        controller.getButtonSave().setOnMouseClicked(event -> {
            File file = controller.getPdfSave().showSaveDialog(controller.getAnchorPane().getScene().getWindow());
            if(file == null) return;
            controller.getPdfCreator().generate(controller.getListView(), file.getAbsolutePath());
        });

        // Clearing TableView
        controller.getButtonClear().setOnMouseClicked(event -> controller.getListView().clear());
        // Clearing TableView

        controller.getTableView().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE) {
                controller.getListView().remove(controller.getTableView().getSelectionModel().getSelectedItem());
            }
        });

        controller.getTableView().setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY)
                controller.getContextMenu().show(controller.getAnchorPane().getScene().getWindow(),
                        event.getScreenX(), event.getScreenY());
        });

        // About app
        controller.getMenuItemAbout().setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setTitle("About application");
            alert.setHeaderText("The application converts image files to pdf file.");
            alert.setContentText("Created by Victor Sava \"Dronald\" ");

            alert.showAndWait();
        });
        // About app

        // Opening images by using MenuItem
        controller.getMenuItemOpen().setOnAction(event -> {
            FileTime creationTime;
            List<File> files = controller.getImageChooser()
                    .showOpenMultipleDialog(controller.getAnchorPane().getScene().getWindow());
            if(!files.isEmpty()) {
                for (File file : files) {
                    try {
                        creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                        controller.getListView().add(new TableElement(file, file.getName(), simpleDateFormat.format(creationTime.toMillis())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                controller.sortTableView();
            }
        });
        // Opening images by using MenuItem

        // Update TableView
        controller.getMenuBar().getMenus().stream()
                .filter(predicate -> predicate.getItems().size() == controller.getSortGroup().getToggles().size())
                .forEach(action -> action.getItems()
                .forEach(action2 -> action2.setOnAction(event -> controller.sortTableView())));
        // Update TableView

        // Swapping TableElements
        controller.getTableView().setOnMousePressed(event -> {
            dy = event.getY();
            draggingElement = controller.getTableView().getSelectionModel().getSelectedItem();
        });

        controller.getTableView().setOnMouseDragged(event -> {
            if(controller.isCanSwap())
                if(event.getY() - dy >= 25.0) {
                    dy = event.getY();
                    int index = controller.getTableView().getItems().indexOf(draggingElement);
                    if(index == controller.getTableView().getItems().size() - 1) return;
                    swapElements(controller.getTableView(), index, index + 1);
                } else if(event.getY() - dy <= -25.0) {
                    dy = event.getY();
                    int index = controller.getTableView().getItems().indexOf(draggingElement);
                    if(index == 0) return;
                    swapElements(controller.getTableView(), index, index - 1);
                }
        });
        // Swapping TableElements

        // Drag app
        controller.getMenuBar().setOnMousePressed(event -> {
            dx = controller.getAnchorPane().getScene().getWindow().getX() - event.getScreenX();
            dy =  controller.getAnchorPane().getScene().getWindow().getY() - event.getScreenY();
        });

        controller.getMenuBar().setOnMouseDragged(event -> {
            controller.getAnchorPane().getScene().getWindow().setX(event.getScreenX() + dx);
            controller.getAnchorPane().getScene().getWindow().setY(event.getScreenY() + dy);
        });
        // Drag app

        // Drag images
        controller.getTableView().setOnDragOver(event -> {
            if (event.getDragboard().hasFiles())
                if(event.getDragboard().getFiles().stream()
                        .anyMatch(predicate -> Arrays.stream(FileType.values())
                                .anyMatch(predicate2 -> predicate.getName().endsWith(predicate2.getExtension()))))
                    event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        controller.getTableView().setOnDragDropped(event -> {

            FileTime creationTime;
            List<File> files = event.getDragboard().getFiles();
            for(File file : files) {
                try {
                    creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                    controller.getListView().add(new TableElement(file, file.getName(), simpleDateFormat.format(creationTime.toMillis())));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            controller.sortTableView();

            event.consume();
        });
        // Drag images

        // Close app
        controller.getMenuItemX().setOnMouseClicked(mouseEvent -> Platform.exit());
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());
        // Close app

        controller.getContextMenuItemDelete().setOnAction(event -> controller.getListView()
                .remove(controller.getTableView().getSelectionModel().getSelectedItem()));

        controller.getContextMenuItemOpen().setOnAction(event -> {
            try {
                Desktop.getDesktop().open(controller.getTableView().getSelectionModel().getSelectedItem().getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }



}
