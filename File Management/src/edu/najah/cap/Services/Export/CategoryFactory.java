package edu.najah.cap.Services.Export;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class CategoryFactory {
    public static ArrayList<SystemFile> getCategoryType(String categoryName, String categoryType) throws Exception {
        final String NOT_FOUND_CATEGORY_EXCEPTION = "'" + categoryName + "' not exist in " + categoryType + " category";
        boolean isSizeCategory = categoryName.equals(Constant.FILE_SIZE_CATEGORY);
        boolean isTypeCategory = categoryName.equals(Constant.FILE_TYPE_CATEGORY);
        boolean isNewCategory = categoryName.equals(Constant.FILE_NEW_CATEGORY);
        boolean isValidSizeCategoryType = FileClassifier.getFileSizeRanges().containsKey(categoryType);
        boolean isValidNewCategoryType = FileClassifier.getFileCategoryRulers().containsKey(categoryType);
        boolean isValidTypeCategoryType = FileClassifier.getFileTypeRuler().containsKey(categoryType);
        if (isSizeCategory && !isValidSizeCategoryType)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);

        if (isSizeCategory)

            return FileClassifier.getFileSizeRanges().get(categoryType);

        if (isTypeCategory && !isValidTypeCategoryType)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);

        if (isTypeCategory)

            return FileClassifier.getFileTypeRuler().get(categoryType);



        if (isNewCategory && !isValidNewCategoryType)
            throw new CategoryNotFoundExeption();
        if (isNewCategory)
            return FileClassifier.getFileCategoryRulers().get(categoryType);
        throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);


    }
}

