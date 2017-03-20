package classreader;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class FileToolTest {

    @Test
    public void initFileTypes() throws Exception {
        String path = "E:\\360云";
        FileTool fileTool = new FileTool(path);
        fileTool.initFileTypes();
        Map<String,List<File>> fileTpyes = fileTool.getFileTypes();
        Set<String> types = fileTpyes.keySet();
        for(String type :types ){
            List<File> files = fileTpyes.get(type);
            System.out.println("the type is "+ type+".............................");
            for(File file : files){
                System.out.println(file.getName());
            }
            System.out.println("the type is "+ type+".............................");
        }

    }

}