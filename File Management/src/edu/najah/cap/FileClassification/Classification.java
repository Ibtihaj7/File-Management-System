package edu.najah.cap.FileClassification;

import edu.najah.cap.FileClassification.Interface.IClassification;
import edu.najah.cap.FileClassification.impl.NewCategoryClassification;
import edu.najah.cap.FileClassification.impl.SizeClassification;
import edu.najah.cap.FileClassification.impl.TypeClassification;
import edu.najah.cap.FileRepository.SystemFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Classification {
    private ClassificationType classificationType;
    private IClassification classification;

    public Classification() {
        this.classificationType = new ClassificationType();
    }
    public void classifySize(String pathName) throws SQLException {
        classification = new SizeClassification();
        classification.addClassification(pathName);
    }

    public void classifyType(String pathName) throws SQLException {
        classification = new TypeClassification();
        classification.addClassification(pathName);
    }

    public void classifyNewCategory(String pathName) throws SQLException {
        classification = new NewCategoryClassification();
        classification.addClassification(pathName);
    }

    public ClassificationType getClassificationType() { return classificationType; }

    public void displaySizeClassification(){
       HashMap<Integer, ArrayList<SystemFile>> filesWithSize =  classificationType.getSizeClasification();
        filesWithSize.forEach((size,files) -> {
            System.out.println("Files whose size is equal to "+size+" : ");
            files.forEach(file -> {
                System.out.println(file);
            });
        });

    }
}
