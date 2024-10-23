package org.example;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.classifiers.Classifier;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // Đường dẫn tới thư mục chứa ảnh
        String imageFolderPath = "E:\\xulyanh\\hinhanh";

        // Tạo danh sách đặc trưng và nhãn (classes)
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < 10000; i++) { // Giả sử bạn có 10000 đặc trưng từ ảnh
            attributes.add(new Attribute("pixel" + i));
        }

        // Thêm thuộc tính nhãn
        ArrayList<String> classLabels = new ArrayList<>();
        classLabels.add("class1");
        classLabels.add("class2"); // Thêm các lớp phù hợp với bài toán của bạn
        attributes.add(new Attribute("class", classLabels));

        // Tạo tập dữ liệu
        Instances data = new Instances("ImageDataset", attributes, 0);
        data.setClassIndex(data.numAttributes() - 1); // Đặt nhãn là cột cuối cùng

        // Đọc các tệp ảnh từ thư mục và trích xuất đặc trưng
        File folder = new File(imageFolderPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".jpg")) {
                // Trích xuất đặc trưng từ ảnh
                float[] features = ImageProcessing.extractImageFeatures(file.getAbsolutePath());

                // Tạo instance mới cho mỗi ảnh
                DenseInstance instance = new DenseInstance(data.numAttributes());
                for (int i = 0; i < features.length; i++) {
                    instance.setValue(attributes.get(i), features[i]);
                }
                // Đặt nhãn cho ảnh (giả sử từ tên file hoặc cấu trúc thư mục)
                instance.setValue(attributes.get(attributes.size() - 1), "class1"); // Hoặc class2

                data.add(instance);
            }
        }

        // Tạo các classifier (SVM, KNN, Decision Tree)
        Classifier svm = ImageClassifier.createSVM();
        Classifier knn = ImageClassifier.createKNN();
        Classifier decisionTree = ImageClassifier.createDecisionTree();

        // So sánh SVM
        System.out.println("Evaluating SVM...");
        long startTime = System.currentTimeMillis();
        ClassifierEvaluation.evaluateModel(svm, data);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for SVM: " + (endTime - startTime) + " ms");

        // So sánh KNN
        System.out.println("\nEvaluating KNN...");
        startTime = System.currentTimeMillis();
        ClassifierEvaluation.evaluateModel(knn, data);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for KNN: " + (endTime - startTime) + " ms");

        // So sánh Decision Tree
        System.out.println("\nEvaluating Decision Tree...");
        startTime = System.currentTimeMillis();
        ClassifierEvaluation.evaluateModel(decisionTree, data);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for Decision Tree: " + (endTime - startTime) + " ms");
    }
}
