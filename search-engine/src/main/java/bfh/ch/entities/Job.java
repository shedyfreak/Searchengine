package bfh.ch.entities;

/**
 * class to represent the job offers in our dataset.
 */
public class Job {
    private String title;
    private String description;
    private String location;
    private String language;
    private String url;
    private String date;

    /**
     *constructor.
     */
    public Job() {
    }
//==============================================getters===============================================================
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

//======================================================setters===================================================
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * to string method for the Job object
     * used to display the Job object in a readable manner
     *
     * @return a string that presents the job
     */
    @Override
    public String toString() {
        return "\n" + this.getTitle()
                + "\n \n" + this.getDate() + " || " + this.getLanguage().toUpperCase();
    }
}
