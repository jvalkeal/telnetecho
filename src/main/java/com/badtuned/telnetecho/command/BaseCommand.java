package com.badtuned.telnetecho.command;

import com.badtuned.telnetecho.io.FileTemplate;

/**
 * Common base class for command implementations.
 * 
 * @author Janne Valkealahti
 * 
 */
public abstract class BaseCommand implements CommandProcessor {

    /** Handle to filetemplate helper */
    private FileTemplate template;

    /**
     * Constructor creating a default {@link FileTemplate}.
     */
    public BaseCommand() {
        template = new FileTemplate();
    }

    /**
     * Gets the used {@link FileTemplate}.
     * 
     * @return File template
     */
    public FileTemplate getTemplate() {
        return template;
    }

}
