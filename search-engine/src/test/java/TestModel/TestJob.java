package TestModel;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import bfh.ch.entities.Job;

class TestJob {

    private final static Job mockJob = new Job();
    private final static LocalDate date = LocalDate.now();

    @BeforeAll
    static void setUpJobObject() {
        mockJob.setDate(date.toString());
        mockJob.setDescription("All jobs are great");
        mockJob.setLanguage("DE");
        mockJob.setLocation("BE");
        mockJob.setTitle("Software Entwickler");
        mockJob.setUrl("www.bfh.ch");
    }

    @Test
    void testJob() {
        assertNotEquals(null, mockJob);
    }

    @Test
    void testGetTitle() {
        assertEquals(mockJob.getTitle(), "Software Entwickler");
    }

    @Test
    void testGetDescription() {
        assertEquals(mockJob.getDescription(), "All jobs are great");
    }

    @Test
    void testGetLocation() {
        assertEquals(mockJob.getLocation(), "BE");
    }

    @Test
    void testGetLanguage() {
        assertEquals(mockJob.getLanguage(), "DE");
    }

    @Test
    void testGetUrl() {
        assertEquals(mockJob.getUrl(), "www.bfh.ch");
    }

    @Test
    void testGetDate() {
        assertEquals(mockJob.getDate(), LocalDate.now().toString());
    }

    @Test
    void testSetTitle() {
        assertEquals(mockJob.getTitle(), "Software Entwickler");
    }

    @Test
    void testSetDescription() {
        assertEquals(mockJob.getDescription(), "All jobs are great");
    }

    @Test
    void testSetLocation() {
        assertEquals(mockJob.getLocation(), "BE");
    }

    @Test
    void testSetLanguage() {
        assertEquals(mockJob.getLanguage(), "DE");
    }

    @Test
    void testSetUrl() {
        assertEquals(mockJob.getUrl(), "www.bfh.ch");
    }

    @Test
    void testSetDate() {
        assertEquals(mockJob.getDate(), LocalDate.now().toString());
    }

    @Test
    void testToString() {
        assertNotEquals(mockJob.toString(), null);
    }

}
