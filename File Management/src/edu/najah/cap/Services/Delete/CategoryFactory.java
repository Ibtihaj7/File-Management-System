package edu.najah.cap.Services.Delete;

import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;


public class CategoryFactory {
    private static Delete delete = new Name();
    public static void doDelete(String categoryName, String categoryType, User createdBy) throws Exception{
        if(categoryType.equals("size")){
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName)){
                FileClassifier.getFileSizeRanges().get(categoryName).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("type")){
            if (FileClassifier.getFileTypeRuler().containsKey(categoryName)){
                FileClassifier.getFileTypeRuler().get(categoryName).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("category")){
            if (FileClassifier.getFileCategoryRulers().containsKey(categoryName)){
                FileClassifier.getFileCategoryRulers().get(categoryName).forEach(file->{
                    try {
                        delete.delete(file.getName(), file.getType(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
    }
}
