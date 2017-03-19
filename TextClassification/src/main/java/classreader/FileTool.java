package classreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/8 0008.
 */
public class FileTool {
    private final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);

    private String parentPath;
    private Map<String,List<String>> fileTypes;

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
                    List<String> files = new ArrayList<>();
                    for(File childFile : childFiles ){
                        files.add(childFile.getAbsolutePath());
                    }
                    fileTypes.put(type.getAbsolutePath(),files);
                }
            }
        }
    }


}
