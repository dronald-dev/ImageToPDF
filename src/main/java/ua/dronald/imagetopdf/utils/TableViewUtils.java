package ua.dronald.imagetopdf.utils;


import javafx.scene.control.TableView;
import ua.dronald.imagetopdf.TableElement;

public class TableViewUtils {

    public static void swapElements(TableView<TableElement> tableView, int from, int to) {
        TableElement tableElement1 = tableView.getItems().get(from);
        TableElement tableElement2 = tableView.getItems().get(to);
        tableView.getItems().set(to, tableElement1);
        tableView.getItems().set(from, tableElement2);
    }

}
