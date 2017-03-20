package classreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/2/8 0008.
 */
public class FileTool {
    private final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);

    private String parentPath;
    private Map<String,List<File>> fileTypes = new HashMap<>();

    /**
     * filePath is the parent path of the types as filePath--->types--->file1...filen
     * @param parentPath
     */
    public FileTool(String parentPath){
        this.parentPath = parentPath;
    }

    public void initFileTypes(){
        File parentFile = new File(parentPath);
        if(!parentFile.isDirectory())
            LOGGER.error("the parentPath is not a directory...");
        File[] types=parentFile.listFiles();
        if(types!=null){
            for(File type :types){
                if(type.isDirectory()){
                    File[] childFiles = type.listFiles();
                    List<File> files = new ArrayList<>();
                    files.addAll(Arrays.asList(childFiles));
                    fileTypes.put(type.getName(),files);
                }
            }
        }
    }

    public Map<String,List<File>> getFileTypes(){
        return fileTypes;
    }

    public static String readFile(File file) throws FileNotFoundException,IOException{
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            buffer.append(line);
        }
        return buffer.toString();
    }


}
