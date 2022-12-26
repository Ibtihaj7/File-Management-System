package edu.najah.cap.Services.Delete;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;


public class CategoryFactory {
    private static Delete delete = new NameDeleter();

    public static void doDelete(String categoryName, String categoryType, User createdBy) throws Exception{
        App.LOGGER.info("Received request to delete category: " + categoryName + " of type: " + categoryType +
                " created by: " + createdBy.getUsername());

         final  String NOT_FOUND_CATEGORY_EXCEPTION="'"+categoryName+"' not exist in "+categoryType+" category";
         boolean isSizeCategory=categoryName.equals(Constant.FILE_SIZE_CATEGORY);
         boolean isTypeCategory= categoryName.equals(Constant.FILE_TYPE_CATEGORY);
         boolean isNewCategory=categoryName.equals(Constant.FILE_NEW_CATEGORY);
       boolean isValidCategoryType=FileClassifier.getFileTypeRuler().containsKey(categoryType);
        boolean isValidCategorySize=FileClassifier.getFileSizeRanges().containsKey(categoryType);
        boolean isValidCategoryNew=FileClassifier.getFileCategoryRulers().containsKey(categoryType);
       if(isSizeCategory&& !isValidCategorySize)
           throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);

        if(isSizeCategory) {
            FileClassifier.getFileSizeRanges().get(categoryType).forEach(file -> {
                try {
                    delete.delete(file.getName(), file.getType(), createdBy);
                    App.LOGGER.info("Successfully deleted category: " + categoryName);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            });
        }

        if(isTypeCategory&&!isValidCategoryType)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);
            if(isTypeCategory){
        FileClassifier.getFileTypeRuler().get(categoryType).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                        App.LOGGER.info("Successfully deleted category: " + categoryName);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }

        if(isNewCategory&&!isValidCategoryNew)
            throw new CategoryNotFoundExeption(NOT_FOUND_CATEGORY_EXCEPTION);
            if(isNewCategory){
                FileClassifier.getFileCategoryRulers().get(categoryType).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                        App.LOGGER.info("Successfully deleted category: " + categoryName);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });

            }

    }
}
