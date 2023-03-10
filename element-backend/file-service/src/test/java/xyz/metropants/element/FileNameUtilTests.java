package xyz.metropants.element;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.metropants.element.util.FileNameUtils;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@SpringBootTest
public class FileNameUtilTests {

    @Test
    public void testFormatFileName() {
        final String fileName = "test file name.png";

        assertEquals(null, "test-file-name.png", FileNameUtils.formatFileName(fileName));
        assertNotEquals(null, "test.file-name.png", FileNameUtils.formatFileName(fileName));
    }

}
