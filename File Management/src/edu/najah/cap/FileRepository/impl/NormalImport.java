package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.Import;

import java.io.File;


public class NormalImport implements Import {
    @Override
    public void importFile(String pathName) {
      File file = new File(pathName);
      String name = file.getName().split("\\.")[0];
      String type = file.getName().split("\\.")[1];
      int size = (int) file.length();
//      SystemFile systemFile = new SystemFile(name,size,"",type);
        MySQLDatabase mySQLDatabase = MySQLDatabase.getInstance();
        mySQLDatabase.execute("INSERT INTO files VALUES ('" + 1 + "','" + name + "','" + size + "','" + type + "','" + " " + "')");
        System.out.println("The file has been imported successfully");
    }
}
