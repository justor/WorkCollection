package com.regression_analysis;
import java.io.*;
import java.util.Random;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;
import weka.core.converters.ArffLoader;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class ModelProducer {



    /**
     * Object that stores training data.
     */
    public  Instances trainData;
    /**
     * Object that stores the classifier
     */
    NaiveBayes classifier;

    /**
     * This method loads a dataset in ARFF format. If the file does not exist, or
     * it has a wrong format, the attribute trainData is null.
     * @param fileName The name of the file that stores the dataset.
     */
    public void loadDataset(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            trainData = arff.getData();
            System.out.println("===== Loaded dataset: " + fileName + " =====");
            reader.close();
        }
        catch (IOException e) {
            System.out.println("Problem found when reading: " + fileName);
        }
    }

    /**
     * This method evaluates the classifier. As recommended by WEKA documentation,
     * the classifier is defined but not trained yet. Evaluation of previously
     * trained classifiers can lead to unexpected results.
     */
    public void evaluate() {
        try {
            trainData.setClassIndex(trainData.numAttributes()-1);
            classifier = new NaiveBayes();
            Evaluation eval = new Evaluation(trainData);
            eval.crossValidateModel(classifier, trainData, 4, new Random(1));
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println("===== Evaluating on filtered (training) dataset done =====");
        }
        catch (Exception e) {
            System.out.println("Problem found when evaluating");
        }
    }

    /**
     * This method trains the classifier on the loaded dataset.
     */
    public void learn() {
        try {
            trainData.setClassIndex(trainData.numAttributes()-1);
            classifier = new NaiveBayes();
            classifier.buildClassifier(trainData);
            // Uncomment to see the classifier
            System.out.println(classifier);
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
    public void saveModel(String fileName) {
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

    /**
     * Main method. It is an example of the usage of this class.
     * @param args Command-line arguments: fileData and fileModel.
     */
    public static void mainFunction (String[] args) {

        ModelProducer learner;
        if (args.length < 2)
            System.out.println("Usage: java Learner <fileData> <fileModel>");
        else {
            learner = new ModelProducer();
            learner.loadDataset(args[0]);
            // Evaluation mus be done before training
            // More info in: http://weka.wikispaces.com/Use+WEKA+in+your+Java+code
            learner.evaluate();
            learner.learn();
            learner.saveModel(args[1]);
        }
    }

    public static void main(String[] args) throws Exception{
             //load data
        ModelProducer learner = new ModelProducer();
        learner.loadDataset("E:/wekadata/house.arff");
        System.out.println(learner.trainData.toString());
        learner.trainData.setClassIndex(learner.trainData.numAttributes() - 1);
        //build model
        LinearRegression model = new LinearRegression();
        //the last instance with missing class is not used
        model.buildClassifier(learner.trainData);
        System.out.println(model);
        //classify the last instance
        Instance myHouse = learner.trainData.lastInstance();
        double price = model.classifyInstance(myHouse);
        System.out.println("My house ("+myHouse+"): "+price);
        }
}
