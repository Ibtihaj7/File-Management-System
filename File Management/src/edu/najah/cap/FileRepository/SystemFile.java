package edu.najah.cap.FileRepository;

public class SystemFile implements Ifile {
    private String name;
    private int size;
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
    
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
