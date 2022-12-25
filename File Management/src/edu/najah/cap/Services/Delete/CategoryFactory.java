package edu.najah.cap.Services.Delete;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;


public class CategoryFactory {
    private static Delete delete = new Name();

    public static void doDelete(String categoryName, String categoryType, User createdBy) throws Exception{

         final  String NOT_FOUND_CATEGORY_EXCEPTION="'"+categoryName+"' not exist in "+categoryType+" category";
         boolean isSizeCategory=categoryType.equals(Constant.FILE_SIZE_CATEGORY);
         boolean isTypeCategory= categoryType.equals(Constant.FILE_TYPE_CATEGORY);
         boolean isNewCategory=categoryType.equals(Constant.FILE_NEW_CATEGORY);
       boolean isValidCategoryType=FileClassifier.getFileSizeRanges().containsKey(categoryName);
       if(isSizeCategory&& !isValidCategoryType)
           throw new CategoryNotFoundExeption("'" + categoryName + "' not exist in " + categoryType + " category");

        if(isSizeCategory) {
            FileClassifier.getFileSizeRanges().get(categoryName).forEach(file -> {
                try {
                    delete.delete(file.getName(), file.getType(), createdBy);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            });
        }

        if(isTypeCategory&&!isValidCategoryType)
            throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            if(isTypeCategory){
        FileClassifier.getFileTypeRuler().get(categoryName).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }

        if(isNewCategory&&!isValidCategoryType)
            throw new CategoryNotFoundExeption();
            if(isNewCategory){
                FileClassifier.getFileCategoryRulers().get(categoryName).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });

            }

    }
}
