package analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class DataPrepare {
    private final Logger log = LoggerFactory.getLogger(DataPrepare.class);

    private Attribute  classAttribute  = null;
    private Attribute  textAttribute   = null;
    private Instances  instances       = null;
    private Set<String> types = null;

    public DataPrepare(Set<String>textType){
        types = textType;
        initInstance(textType);
    }

    private void initInstance(Set<String>textType){

        FastVector classAttributeVector = new FastVector();
        classAttributeVector.addAll(textType);
        classAttribute = new Attribute("class", classAttributeVector);
        FastVector inputTextVector = null;           // null -> String type
        textAttribute = new Attribute("text", inputTextVector);
        FastVector thisAttributeInfo = new FastVector(2);
        thisAttributeInfo.addElement(textAttribute);
        thisAttributeInfo.addElement(classAttribute);
        instances = new Instances("data set", thisAttributeInfo, 100);
        instances.setClass(classAttribute);
        instances.setClassIndex(0);
    }

    public void addTrainData(String type , List<String> trainDatas){
        if(types.contains(type))
        for (String data: trainDatas) {
            Instance inst = new DenseInstance(2);
            inst.setValue(textAttribute,data);
            inst.setValue(classAttribute,type);
            instances.add(inst);
        }
        else{
            System.out.println("hava no this type ； "+type);
        }
    }
    public void addTrainData(String type , String trainData){
        if(types.contains(type)){
            Instance inst = new DenseInstance(2);
            inst.setValue(textAttribute,trainData);
            inst.setValue(classAttribute, type);
            instances.add(inst);
        }else {
            System.out.println("hava no this type : "+type);
            log.error("hava no this type : "+type);
        }

    }
    public Instances getInstances(){
        return instances;
    }
    public static void main(String args[]) {
        Set<String> types = new HashSet<>();
        types.add("zhong guo");
        types.add("mie guo");
        types.add("e luo si");
        DataPrepare parser = new DataPrepare(types);
        parser.addTrainData("zhong guo","wo men dou shi zhong guo ren ");
        parser.addTrainData("mei guo","wo men dou shi zhong guo ren ");
        parser.addTrainData("e luo si","wo men dou shi zhong guo ren ");
        System.out.println(parser.getInstances().toString());
    }

}
