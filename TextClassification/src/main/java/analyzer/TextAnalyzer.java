package analyzer;

import classreader.FileTool;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class TextAnalyzer {

    private Map<String,List<File>> fileTypes;
    public static void main(String[] args){
        List<Term> termList = NotionalTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        System.out.println(termList);
        for(Term term : termList){
            System.out.println(term.word);
        }
    }


    private String getAnalyzedContent(String content){
        StringBuffer buffer = new StringBuffer();
        List<Term> termList = NotionalTokenizer.segment(content);
        for(Term term : termList){
            buffer.append(term.word);
            buffer.append(" ");
        }
        return buffer.toString();
    }


    public Map<String,List<String>> getContents() throws IOException {
        Map<String,List<String>> typeContents = new HashMap<>();
        Set<String> types = this.fileTypes.keySet();
        for(String type : types){
            List<File> files = this.fileTypes.get(type);
            List<String> contents = new ArrayList();
            for(File file : files){
                String content = FileTool.readFile(file);
                contents.add(getAnalyzedContent(content));
            }
            typeContents.put(type,contents);
        }
        return typeContents;
    }
}
