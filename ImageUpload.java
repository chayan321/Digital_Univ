package advaitam.digitaluniv;

/**
 * Created by CHAYAN_PC on 20-03-2018.
 */

public class ImageUpload {
    public String name;
    public String url;

    public ImageUpload() {
    }
    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
