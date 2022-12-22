package edu.najah.cap.FileRepository;

public class SystemFile{
    private  String name;
    private  int size;
    private String category;
    private String type;
    private int version;
    private String path;

    public SystemFile(String name, String type, int size, String category, String path, int version) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.category = category;
        this.path = path;
        this.version = version;
    }


    @Override
    public String toString() {
        return "File information{" +
                "name : '" + name + "\t\'" +
                ", size : " + size +"\t\'"+
                ", category : '" + category + "\t\'"+
                ", type : '" + type + "\t\'"+
                ", version : " + version +"\t\'"+
                ", path : '" + path + "\t\'"+
                '}';
    }
}
