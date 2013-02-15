package com.badtuned.telnetecho.io;

import java.io.File;
import java.io.IOException;

/**
 * Simple callback pattern to execute
 * 'actions' via an interface.
 * 
 * @author Janne Valkealahti
 *
 * @param <T> Return type
 */
public interface FileCallback<T> {

    /**
     * Action or callback to do something.
     */
    T doWithFile(File file) throws IOException;

}