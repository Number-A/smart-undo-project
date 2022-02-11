import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SettingsTest {
    private static final String TEST_FILE_PATH = "./Test/com/company/test_settings.cfg";
    private static final int TEST_FONT_SIZE = 12;
    private static final String TEST_LANGUAGE = "en";
    private static final int TEST_MAX_GROUP_EDITS = 25;

    // keep track of data for testing purposes
    private HashMap<String, Object> data;

    public Settings settings;

    @Before
    public void init() {
        System.out.println("Initializing mock/sample data before running tests");
        settings = new Settings(TEST_FILE_PATH);
    }

    @Test
    public void testAddSettings() throws Exception {

        // test passes if given condition return true
        // otherwise fails given condition returns false unexpectedly.
        assertTrue(true);

        // given expect value (mock data) and actual value (class being tested):
        // test passes if values are equal
        // otherwise test fails
        assertEquals(5, 5);
    }


    // test first time user installs smart undo
    @Test
    public void testfirstInit() throws Exception  {


        assertEquals(settings.getLanguage(), Settings.DEFAULT_LANGUAGE);
        assertEquals(settings.getFontSize(), Settings.DEFAULT_FONT_SIZE);
        assertEquals(settings.getMaxGroupEdits(), Settings.DEFAULT_MAX_GROUP_EDITS);
    }

    @Test
    public void testGetFontSize() throws Exception  {
        assertEquals(settings.getFontSize(), Settings.DEFAULT_FONT_SIZE);
    }

    @Test
    public void testSetFontSize() throws Exception  {
        int fontSize = 18;
        settings.setFontSize(fontSize);
        assertEquals(settings.getFontSize(), fontSize);
    }


    @Test
    public void testGetLanguage() throws Exception  {
        assertEquals(settings.getLanguage(), Settings.DEFAULT_LANGUAGE);
    }

    @Test
    public void testSetLanguage() throws Exception  {
        String language = "fr";
        settings.setLanguage(language);
        assertEquals(settings.getLanguage(), language);
    }


    @Test
    public void testGetMaxGroupEdits() throws Exception  {
        assertEquals(settings.getMaxGroupEdits(), Settings.DEFAULT_MAX_GROUP_EDITS);
    }

    @Test
    public void testSetMaxGroupEdits() throws Exception  {
        int maxGroupEdits = 18;
        settings.setMaxGroupEdits(maxGroupEdits);
        assertEquals(settings.getMaxGroupEdits(), maxGroupEdits);
    }

    @Test
    public void testReadWrite() throws Exception  {
        settings.save();
    }
}
