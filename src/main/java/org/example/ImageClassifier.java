package org.example;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;  // SVM
import weka.classifiers.lazy.IBk;  // KNN
import weka.classifiers.trees.J48;  // Decision Tree
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ImageClassifier {

    public static Classifier createSVM() throws Exception {
        SMO svm = new SMO();
        return svm;
    }

    public static Classifier createKNN() throws Exception {
        IBk knn = new IBk(3); // Số lượng lân cận (K)
        return knn;
    }

    public static Classifier createDecisionTree() throws Exception {
        J48 tree = new J48(); // Decision Tree
        return tree;
    }

    public static void evaluateClassifier(Classifier classifier, Instances trainData, Instances testData) throws Exception {
        // Huấn luyện mô hình
        classifier.buildClassifier(trainData);

        // Đo lường hiệu suất
        int correct = 0;
        for (int i = 0; i < testData.numInstances(); i++) {
            Instance instance = testData.instance(i);
            double actual = instance.classValue();
            double predicted = classifier.classifyInstance(instance);

            if (actual == predicted) {
                correct++;
            }
        }
        double accuracy = 100.0 * correct / testData.numInstances();
        System.out.println("Accuracy: " + accuracy + "%");
    }
}
