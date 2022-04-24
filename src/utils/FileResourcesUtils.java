package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 * Utility class that reads a .txt file and returns its content in a String in HTML format.
 * Taken from https://mkyong.com/java/java-read-a-file-from-resources-folder/
 *
 */
public class FileResourcesUtils {

    private static final String SEP = File.separator;

    /**
     * 
     * @param file the name of the file to read from
     * @return the content of the file in a String in HTML format.
     */
    public static String readFromFile(final String file) {
        final FileResourcesUtils app = new FileResourcesUtils();
        String str = "";
        try {
            final List<Path> result = app.getPathsFromResourceJAR(file);
            for (final Path path : result) {
                String filePathInJAR = path.toString();
                if (filePathInJAR.startsWith(SEP)) {
                    filePathInJAR = filePathInJAR.substring(1, filePathInJAR.length());
                }
                try (InputStream is = app.getFileFromResourceAsStream(filePathInJAR)) {
                    str = FileResourcesUtils.getInputStream(is);
                } catch (IllegalArgumentException e) {
                    return "ERROR FOR FILE " + file;
                }
            }
        } catch (URISyntaxException | IOException e) {
            return "ERROR FOR FILE " + file;
        }
        return str;
    }
 
    /**
     * 
     * @param fileName the name of the file
     * @return the InputStream of the file from the resources folder.
     */
    private InputStream getFileFromResourceAsStream(final String fileName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    /**
     * 
     * @param folder the where the files to be read are
     * @return all paths from a folder inside the JAR file.
     * @throws URISyntaxException
     * @throws IOException
     */
    private List<Path> getPathsFromResourceJAR(final String folder)
        throws URISyntaxException, IOException {
        List<Path> result;
        final String jarPath = getClass().getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        final URI uri = URI.create("jar:file:" + jarPath);
        try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
            result = Files.walk(fs.getPath(folder))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;

    }

    /**
     * 
     * @param is the InputStream to read
     * @return the InputStream content as a String in HTML format.
     */
    private static String getInputStream(final InputStream is) {
        String result = "<html><p width=\\\"500\\\">";
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line = reader.readLine();
            while (line != null) {
                result = result.concat(line + "<br>");
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result + "</p></html>";
    }

}
