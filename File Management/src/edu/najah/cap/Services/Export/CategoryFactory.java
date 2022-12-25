package edu.najah.cap.Services.Export;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class CategoryFactory {
    public static ArrayList<SystemFile> getCategoryType(String categoryName, String categoryType) throws Exception {
        final String NOT_FOUND_CATEGORY_EXCEPTION = "'" + categoryName + "' not exist in " + categoryType + " category";
        boolean isSizeCategory = categoryType.equals(Constant.FILE_SIZE_CATEGORY);
        boolean isTypeCategory = categoryType.equals(Constant.FILE_TYPE_CATEGORY);
        boolean isNewCategory = categoryType.equals(Constant.FILE_NEW_CATEGORY);
        boolean isValidCategoryType = FileClassifier.getFileSizeRanges().containsKey(categoryName);
        if (isSizeCategory && !isValidCategoryType)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);

        if (isSizeCategory)

            return FileClassifier.getFileSizeRanges().get(categoryName);

        if (isTypeCategory && !isValidCategoryType)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);

        if (isTypeCategory)

            return FileClassifier.getFileTypeRuler().get(categoryName);



        if (isNewCategory && !isValidCategoryType)
            throw new CategoryNotFoundExeption();
        if (isNewCategory)
            return FileClassifier.getFileCategoryRulers().get(categoryName);
        throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);


    }
}

