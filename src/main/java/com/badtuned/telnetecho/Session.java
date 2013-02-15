package com.badtuned.telnetecho;

import java.io.File;

/**
 * Holder object for client session data.
 * Only current directory is now supported.
 * 
 * @author Janne Valkealahti
 *
 */
public class Session {

    /** Current shell directory */
    private File dir;
    
    /**
     * Construct session with user's home
     * directory defined by java's 'user.home'
     * property.
     */
    public Session() {
        changeToHome();
    }

    /**
     * Change directory back to user's home.
     */
    public void changeToHome() {
        dir = new File(System.getProperty("user.home"));        
    }
    
    /**
     * Gets current session directory.
     * @return
     */
    public File getDir() {
        return dir;
    }

    /**
     * Sets session directory.
     * @param dir directory to set
     */
    public void setDir(File dir) {
        this.dir = dir;
    }

}
