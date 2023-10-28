package com.example.LightEaterApp.Chat.service;

import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Image;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import java.awt.*;
//import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class OcrService {
    static {
        // UTF-8 인코딩 설정
        System.setProperty("file.encoding", "UTF-8");
    }
    public static String detectText(String imgUrl) throws IOException {

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(imgUrl));

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
                String resultImgChat = "";
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return res.getError().getMessage();
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position: %s%n", annotation.getBoundingPoly());
                    resultImgChat = resultImgChat + "Text :" +annotation.getDescription() + "\n" + "Position :" + annotation.getBoundingPoly() +"\n" ;

                }

                return resultImgChat;
            }
        }
        return "null_error";
    }

    //경로말고 파일 byte에서 바로 ocr
    public static String detectText2(byte[] imgByte) throws IOException {

        List<AnnotateImageRequest> requests = new ArrayList<>();

        //ByteString imgBytes = ByteString.readFrom(new FileInputStream(imgUrl));

        //Image img = Image.newBuilder().setContent(imgByte).build();
        ByteString imgBytes = ByteString.copyFrom(imgByte);
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
                String resultImgChat = "";
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return res.getError().getMessage();
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position: %s%n", annotation.getBoundingPoly());
                    resultImgChat = resultImgChat + "Text :" +annotation.getDescription() + "\n" + "Position :" + annotation.getBoundingPoly() +"\n" ;

                }

                return resultImgChat;
            }
        }
        return "null_error";
    }
}