package edu.najah.cap.Services.Export;

import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class CategoryFactory {
    public static ArrayList<SystemFile> getCategoryType(String categoryName, String categoryType) throws Exception {
        if (categoryType.equals("size"))
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName))
                return FileClassifier.getFileSizeRanges().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'" + categoryName + "' not exist in " + categoryType + " category");

        if (categoryType.equals("type"))
            if (FileClassifier.getFileTypeRuler().containsKey(categoryName))
                return FileClassifier.getFileTypeRuler().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'" + categoryName + "' not exist in " + categoryType + " category");

        if (categoryType.equals("category"))
            if (FileClassifier.getFileCategoryRulers().containsKey(categoryName))
                return FileClassifier.getFileCategoryRulers().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'" + categoryName + "' not exist in " + categoryType + " category");

        throw new CategoryNotFoundExeption("'" + categoryType + "' category not exist.");
    }
}
