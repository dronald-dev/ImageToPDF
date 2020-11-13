package ua.dronald.imagetopdf;

import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.nio.file.Path;

public class TableElement {

    private File file;

    private SimpleStringProperty name;
    private SimpleStringProperty date;

    public TableElement(File file, String name, String date) {
        this.file = file;
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String value){
        date.set(value);
    }

    public File getFile() {
        return file;
    }
}
