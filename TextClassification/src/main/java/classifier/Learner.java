package classifier;

import analyzer.DataPrepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/6 0006.
 */
public class Learner {

    private static final Logger log = LoggerFactory.getLogger(Learner.class);
    /**
     * Object that stores training data.
     */
    private Instances trainData;
    /**
     * Object that stores the filter
     */
    private StringToWordVector filter;
    /**
     * Object that stores the classifier
     */
    private FilteredClassifier classifier;

    private DataPrepare dataPrepare ;

    public Learner(DataPrepare dataPrepare){
        this.dataPrepare = dataPrepare;
    }

    /**
     * This method loads a dataset in ARFF format. If the file does not exist, or
     * it has a wrong format, the attribute trainData is null.
     * @param fileName The name of the file that stores the dataset.
     */
    public void loadDatasetFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            trainData = arff.getData();
            System.out.println("===== Loaded dataset: " + fileName + " =====");
            reader.close();
        }
        catch (IOException e) {
            log.error(" load Dataset error occured ", e);
        }
    }


    private void initDataSet(){
        this.trainData= dataPrepare.getInstances();
    }

    /**
     * This method evaluates the classifier. As recommended by WEKA documentation,
     * the classifier is defined but not trained yet. Evaluation of previously
     * trained classifiers can lead to unexpected results.
     */
    public void evaluate() {
        try {
            trainData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());
            Evaluation eval = new Evaluation(trainData);
            eval.crossValidateModel(classifier, trainData, 4, new Random(1));
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println("===== Evaluating on filtered (training) dataset done =====");
        }
        catch (Exception e) {
            log.error("Problem found when evaluating",e );
        }
    }

    /**
     * This method trains the classifier on the loaded dataset.
     */
    public void learn() {
        try {
            trainData.setClassIndex(0);
            filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            classifier.setClassifier(new NaiveBayes());
            classifier.buildClassifier(trainData);
            // Uncomment to see the classifier
            // System.out.println(classifier);
            System.out.println("===== Training on filtered (training) dataset done =====");
        }
        catch (Exception e) {
            System.out.println("Problem found when training");
        }
    }

    /**
     * This method saves the trained model into a file. This is done by
     * simple serialization of the classifier object.
     * @param fileName The name of the file that will store the trained model.
     */
    private void saveModel(String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(classifier);
            out.close();
            System.out.println("===== Saved model: " + fileName + " =====");
        }
        catch (IOException e) {
            System.out.println("Problem found when writing: " + fileName);
        }
    }

    public void proAndSaveModel(String fileName){
        initDataSet();
        evaluate();
        learn();
        saveModel(fileName);
    }

    /**
     * Main method. It is an example of the usage of this class.
     * @param args Command-line arguments: fileData and fileModel.
     */
    public static void main (String[] args) {

    }
}
