package ua.dronald.imagetopdf;

public enum FileType {
    JPG("jpg"),
    PNG("png");

    private final String str;

    FileType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public String getExtension() {
        return "." + str;
    }

    @Override
    public String toString() {
        return "FileType{" +
                "str='" + str + '\'' +
                '}';
    }
}