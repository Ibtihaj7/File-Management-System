package edu.najah.cap.FileClassification.impl;
import edu.najah.cap.FileRepository.Ifile;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class Folder implements Ifile {

    private String name;
    private ArrayList<Ifile> files;


    public Folder(String name){
        files = new ArrayList<>();
        this.name = name;
    }

    public int getSize() {
        int size = 0;
        for (Ifile file : files) {
            size += file.getSize();
        }
        return size;
    }

    @Override
    public void printName() {
        System.out.println("Folder name is : " + this.name);
        for (Ifile file : files) {
            file.printName();
        }
        System.out.println();
    }


    public void addFile(Ifile file) {
        files.add(file);
    }

}
