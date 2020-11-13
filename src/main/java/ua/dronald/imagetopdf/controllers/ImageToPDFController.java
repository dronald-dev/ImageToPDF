package ua.dronald.imagetopdf.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ua.dronald.imagetopdf.*;

import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ImageToPDFController implements Initializable {

    private ObservableList<TableElement> listView = FXCollections.observableArrayList();
    private ToggleGroup sortGroup;
    public static final String datePattern = "dd.MM.yyyy HH:mm:ss";
    private boolean canSwap;
    private FileChooser imageChooser;
    private FileChooser pdfSave;
    private ContextMenu contextMenu;
    private MenuItem contextMenuItemOpen;
    private MenuItem contextMenuItemDelete;
    private MenuItem contextMenuItemShow;
    private ProgressSave progressSave;
    private PDFCreator pdfCreator;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelProgress;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<TableElement> tableView;

    @FXML
    private ImageView menuItemX;

    @FXML
    private MenuItem menuItemOpen;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private RadioMenuItem menuItemDate;

    @FXML
    private RadioMenuItem menuItemName;

    @FXML
    private RadioMenuItem menuItemCustom;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sortGroup = new ToggleGroup();
        menuItemDate.setToggleGroup(sortGroup);
        menuItemName.setToggleGroup(sortGroup);
        menuItemCustom.setToggleGroup(sortGroup);

        menuItemX.setPickOnBounds(true); // Mouse clicking when cursor on clear background

        imageChooser = new FileChooser();
        Arrays.stream(FileType.values()).forEach(action -> imageChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(action.getStr().toUpperCase(), "*" + action.getExtension())));

        pdfSave = new FileChooser();
        pdfSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        contextMenu = new ContextMenu();

        contextMenuItemOpen = new MenuItem("Open");
        contextMenuItemShow = new MenuItem("Show");
        contextMenuItemDelete = new MenuItem("Delete");
        contextMenu.getItems().addAll(contextMenuItemOpen, contextMenuItemShow, contextMenuItemDelete);

        progressSave = new ProgressSave(labelProgress, progressBar);
        pdfCreator = new PDFCreator(progressSave);


        new EventManager(this);

        getTableView().setItems(getListView());

    }

    public void sortTableView() {
        if(sortGroup.getSelectedToggle() == menuItemName) {
            canSwap = false;
            listView.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
            listView.sort((o1, o2) -> o1.getName().length() > o2.getName().length() ? 1 : 0);
        } else if(sortGroup.getSelectedToggle() == menuItemDate) {
            canSwap = false;
            listView.sort((Comparator.comparing(o -> LocalDateTime.parse(o.getDate(),
                    DateTimeFormatter.ofPattern(ImageToPDFController.datePattern)))));
        } else if(sortGroup.getSelectedToggle() == menuItemCustom) {
            canSwap = true;
        }
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public MenuItem getContextMenuItemOpen() {
        return contextMenuItemOpen;
    }

    public MenuItem getContextMenuItemDelete() {
        return contextMenuItemDelete;
    }

    public MenuItem getContextMenuItemShow() {
        return contextMenuItemShow;
    }

    public Button getButtonClear() {
        return buttonClear;
    }

    public Button getButtonSave() {
        return buttonSave;
    }

    public FileChooser getImageChooser() {
        return imageChooser;
    }

    public boolean isCanSwap() {
        return canSwap;
    }

    public ToggleGroup getSortGroup() {
        return sortGroup;
    }

    public ObservableList<TableElement> getListView() {
        return listView;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public TableView<TableElement> getTableView() {
        return tableView;
    }

    public ImageView getMenuItemX() {
        return menuItemX;
    }

    public MenuItem getMenuItemOpen() {
        return menuItemOpen;
    }

    public MenuItem getMenuItemClose() {
        return menuItemClose;
    }

    public MenuItem getMenuItemAbout() {
        return menuItemAbout;
    }

    public RadioMenuItem getMenuItemDate() {
        return menuItemDate;
    }

    public RadioMenuItem getMenuItemName() {
        return menuItemName;
    }

    public RadioMenuItem getMenuItemCustom() {
        return menuItemCustom;
    }

    public FileChooser getPdfSave() {
        return pdfSave;
    }

    public Label getLabelProgress() {
        return labelProgress;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ProgressSave getProgressSave() {
        return progressSave;
    }

    public PDFCreator getPdfCreator() {
        return pdfCreator;
    }
}
