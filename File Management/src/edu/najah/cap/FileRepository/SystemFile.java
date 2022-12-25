package edu.najah.cap.FileRepository;

public class SystemFile{
    private  String name;
    private  int size;
    private String type;
    private int version;
    private String path;

    public SystemFile(String name, String type, int size, String path, int version) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.path = path;
        this.version = version;
    }

    public String getName() { return name; }

    public int getSize() { return size; }

    public String getType() { return type; }

    public String getPath() { return path; }

    @Override
    public String toString() {
        return "File information{" +
                "name : '" + name + "\t\'" +
                ", size : " + size +"\t\'"+
                ", type : '" + type + "\t\'"+
                ", version : " + version +"\t\'"+
                ", path : '" + path + "\t\'"+
                '}';
    }
}
