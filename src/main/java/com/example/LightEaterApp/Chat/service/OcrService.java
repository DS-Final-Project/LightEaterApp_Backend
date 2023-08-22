package com.example.LightEaterApp.Chat.service;
/*
import com.google.cloud.vision.v1.*;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//ocrService.detectText();
@Slf4j
@Service
public class OcrService {
    static {
        // UTF-8 인코딩 설정
        System.setProperty("file.encoding", "UTF-8");
    }

    public static void detectText() throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        String inputFilePath = "C:/hyeon/workspace/lighter_demo/demo/src/main/resources/testImg2.jpg";
        String outputFilePath = "C:/hyeon/lighter_Data/output.txt";
        //detectText(inputFilePath, outputFilePath);
        detectText2(inputFilePath);
        detectText3(inputFilePath, outputFilePath);
    }

    // 이상한 친구
    public static void detectText(String inputFilePath, String outputFilePath) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(inputFilePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests.
        // This client only needs to be created once and can be reused for multiple requests.
        // After completing all of your requests, call the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);

            // Write the full JSON response to the output file
            //try (FileWriter writer = new FileWriter(outputFilePath)) {
            //    writer.write(response.toString());
            //}

            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    //System.out.format("Error: %s%n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position : %s%n", annotation.getBoundingPoly());
                    try (FileWriter writer = new FileWriter(outputFilePath)) {
                        writer.write(annotation.getDescription().toString());
                        writer.write(annotation.getBoundingPoly().toString());
                    }
                }
            }


            System.out.println("Text detection completed. Full JSON response written to: " + outputFilePath);
        }
    }

    // println로그에 프린트
    public static void detectText2(String filePath) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position : %s%n", annotation.getBoundingPoly());
                }
            }
        }
    }

    // 텍스트 파일로 만들어서 저장하는 메소드
    public static void detectText3(String inputFilePath, String outputFilePath) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(inputFilePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            // Open the file for writing the detected text
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        // Handle error...
                        return;
                    }

                    for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                        // Write the description and bounding poly to the file
                        writer.write("Text: " + annotation.getDescription() + "\n");
                        //flask에 보내줘야 하는 값
                        writer.write("Position : " + annotation.getBoundingPoly() + "\n\n");
                    }
                }

                System.out.println("Text detection completed. Results written to: " + outputFilePath);
            }
        }
    }
}

 */