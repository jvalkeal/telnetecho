package com.badtuned.telnetecho;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;

import junit.framework.TestCase;

import com.badtuned.telnetecho.io.FileTemplate;

/**
 * Various tests for {@link FileTemplate}.
 * 
 * @author Janne Valkealahti
 *
 */
public class FileTemplateTest extends TestCase {

    private FileTemplate template;
    private File home;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        template = new FileTemplate();
        home = new File(System.getProperty("user.home"));
        File f = new File(home, "FileTemplateTest");
        f.delete();
    }
    
    public void testOperations() {
        assertThat(0, lessThan(template.directoryPath(home).length()));
        assertThat(true, is(template.mkdir("FileTemplateTest", home)));
        assertThat(false, is(template.mkdir("FileTemplateTest", home)));
        assertThat(true, is(template.isDirectory("FileTemplateTest", home)));
        assertThat(false, is(template.isFile("FileTemplateTest", home)));
    }

}
