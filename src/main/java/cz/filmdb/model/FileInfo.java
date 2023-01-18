package cz.filmdb.model;


public class FileInfo {

    private String url, name;

    public FileInfo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public FileInfo() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
