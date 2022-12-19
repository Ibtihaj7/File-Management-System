package edu.najah.cap.File;

public class SystemFile {
    private String name;
    private int size;
    private String category;
    private String type;
    private String version;
    private String path;

    public SystemFile(String name, String type, int size, String category, String path, String version) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
