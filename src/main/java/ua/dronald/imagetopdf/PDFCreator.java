package ua.dronald.imagetopdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;

public class PDFCreator {

    private ProgressSave progressSave;
    
    public PDFCreator(ProgressSave progressSave) {
        this.progressSave = progressSave;
    }
    
    public void generate(ObservableList<TableElement> tableElements, String path) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            progressSave.setAmountFiles(tableElements.size() + 3); // + 3 - for create pdf file and close its, successful create
            progressSave.setMessage("Creating pdf file...");
            progressSave.addProgress();
            document.open();
            for(TableElement tableElement : tableElements) {
                progressSave.setMessage("%i".replace("%i", Integer.toString(tableElements.indexOf(tableElement))));
                progressSave.addProgress();
                Image image = Image.getInstance(tableElement.getFile().toURI().toURL());
                image.setAbsolutePosition(0, 0);
                image.scaleAbsolute(document.getPageSize());
                document.add(image);
                document.newPage();
            }

        } catch(Exception exception) {
            exception.printStackTrace();
        }
        progressSave.setMessage("Closing pdf file...");
        progressSave.addProgress();
        document.close();
        progressSave.setMessage("PDF file has been created successful.");
        progressSave.addProgress();
    }
}
