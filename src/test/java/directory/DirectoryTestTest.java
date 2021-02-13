package directory;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DirectoryTestTest {

    @Test
    public void whenStringThenString() {
        String string = "Hello";
        assertThat(DirectoryTest.myString(string), is("Hello"));
    }
}