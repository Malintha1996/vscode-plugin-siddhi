package io.siddhi.langserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code SiddhiDocumentMangerImpl} manage the documents in the workspace.
 */
public class DocumentManagerImpl implements DocumentManager {

    //private static final Logger LOGGER = LoggerFactory.getLogger(DocumentManagerImpl.class);

    private ConcurrentHashMap<Path, String> documents;

    private DocumentManagerImpl() {
        this.documents = new ConcurrentHashMap<>();
    }
    
    private static class InnerSingleton {
        private static final DocumentManagerImpl INSTANCE = new DocumentManagerImpl();
    }
    
    public static DocumentManagerImpl getInstance() {
        return InnerSingleton.INSTANCE;
    }

    /**
     * Checks whether the given file is open in workspace.
     *
     * @param filePath Path of the file
     * @return Returns the list of opened document paths
     */
    @Override
    public boolean isFileOpen(Path filePath) {
        return getPathEntry(filePath) != null;
    }

    /**
     * Opens the given file in document manager.
     *
     * @param filePath Path of the file
     * @param content  Content of the file
     */
    @Override
    public void openFile(Path filePath, String content) {
        if (isFileOpen(filePath)) {
            //LOGGER.warn("File is Already opened");
            return;
        }
        this.documents.put(filePath, content);
    }

    /**
     * Updates given file in document manager with new content.
     *
     * @param filePath       Path of the file
     * @param updatedContent New content of the file
     */
    @Override
    public void updateFile(Path filePath, String updatedContent) {
        Path opened = getPathEntry(filePath);
        if (opened == null) {
            //LOGGER.error("Cannot find the file to update: " + filePath.toString());
            return;
        }

        this.documents.put(opened, updatedContent);
    }

    /**
     * Close the given file in document manager.
     *
     * @param filePath Path of the file
     */
    @Override
    public void closeFile(Path filePath) {
        Path opened = getPathEntry(filePath);
        if (opened == null) {
            //LOGGER.warn("Cannot find open file [" + filePath.toString() + "] to close");
            return;
        }

        this.documents.remove(opened);
    }

    /**
     * Gets uptodate content of the file.
     *
     * @param filePath Path of the file
     * @return Content of the file
     */
    @Override
    public String getFileContent(Path filePath) {
        if (!isFileOpen(filePath)) {
            return null;
        }
        return documents.get(filePath);
    }

    /**
     * Get the path entry for the given file path.
     * 
     * @param filePath          File Path to se
     * @return {@link Path}     Path Entry
     */
    private Path getPathEntry(Path filePath) {
        return this.documents.entrySet().stream().filter(entry -> {
            try {
                return Files.isSameFile(entry.getKey(), filePath);
            } catch (IOException e) {
                //LOGGER.error("Error locating File: " + e.getMessage());
                return false;
            }
        }).map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
