package com.badtuned.telnetecho.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Helper class that simplifies file access operations.
 * 
 * @author Janne Valkealahti
 *
 */
public class FileTemplate {

    /**
     * Gets an array of file names in sorted order
     * from a given directory.
     * 
     * @param directory the directory to work against
     * @return Array of files in sorted order
     */
    public String[] filesWithModifyOrder(File directory) {
        return execute(new FileCallback<String[]>() {
            @Override
            public String[] doWithFile(File file) {
                File[] files = file.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File lhs, File rhs) {
                        return Long.valueOf(lhs.lastModified()).compareTo(rhs.lastModified());
                    }
                });
                String[] sorted = new String[files.length];
                for(int i = 0; i<files.length; i++) {
                    sorted[i] = files[i].getName();
                }
                return sorted;
            }            
        }, directory);
    }
    
    /**
     * Simple pass-through to {@link File#isFile()} method.
     * 
     * @param name the file name
     * @param directory the directory to work against
     * @return True if resolved file is a file, false otherwise.
     */
    public boolean isFile(String name, File directory) {
        final File file = new File(directory, name);
        return execute(new FileCallback<Boolean>() {
            @Override
            public Boolean doWithFile(File file) {
                return file.isFile();
            }            
        }, file);
    }
    
    /**
     * Simple pass-through to {@link File#isDirectory()} method.
     * 
     * @param name the directory name
     * @param directory the directory to work against
     * @return True if resolved file is a directory, false otherwise.
     */
    public boolean isDirectory(String name, File directory) {
        final File file = new File(directory, name);
        return execute(new FileCallback<Boolean>() {
            @Override
            public Boolean doWithFile(File file) {
                return file.isDirectory();
            }            
        }, file);
    }    

    /**
     * Creates a directory with given name and
     * base directory.
     * 
     * @param name the directory name
     * @param directory the directory to work against
     * @return True if create succeed, false otherwise.
     */
    public boolean mkdir(String name, File directory) {
        final File file = new File(directory, name);
        return execute(new FileCallback<Boolean>() {
            @Override
            public Boolean doWithFile(File file) {
                return file.mkdir();
            }            
        }, file);
    }    
    
    /**
     * Gets directory path.
     * @param directory the directory to work against
     * @return Absolute path of the directory
     */
    public String directoryPath(File directory) {
        return execute(new FileCallback<String>() {
            @Override
            public String doWithFile(File file) {
                return file.getAbsolutePath();
            }            
        }, directory);
    }    
    
    /**
     * Execute the action specified by the given callback object within a {@link File}.
     * 
     * @param callback callback object that specifies the File action
     * @param file file handle to work with
     * @return a result object returned by the callback, or null
     * @throws DataAccessException in case of file errors
     */
    public <T> T execute(FileCallback<T> callback, File file) throws DataAccessException {
        try {
            return callback.doWithFile(file);
        } catch (RuntimeException e) {
            throw new DataAccessException(e.getMessage(), e);
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage(), e);
        }   
    }
    
}
