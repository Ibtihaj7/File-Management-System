package edu.najah.cap.File;

public class File {
    private String path;
    private String name;
    private int size;
    private String category;
    private String type;

    public File(String path, String name, int size, String category, String type) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.category = category;
        this.type = type;
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
