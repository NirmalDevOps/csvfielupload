package com.jjdf;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.jjdf.entity.ApplicantTrackRecord;
import java.nio.charset.StandardCharsets;

public class S3EventHandler implements RequestHandler<S3Event, String> {
    /** Provide the AWS region which your DynamoDB table is hosted. */
    //Region AWS_REGION = Region.getRegion(Regions.US_EAST_1);
    private AmazonDynamoDB amazonDynamoDB;

    private static AmazonS3 s3Client = null;

    private DynamoDBMapper dynamoDBMapper;

    @Override
    public String handleRequest(S3Event input, Context context) {
        final LambdaLogger logger = context.getLogger();
        logger.log("Inside handler");
        int recordCount=1;
        int forLoopCount=1;
        //check if are getting any record
        if (input.getRecords().isEmpty()) {
            logger.log("No records found");
            return "No records found";
        }

        //process the records
        for (S3EventNotification.S3EventNotificationRecord record : input.getRecords()) {
            logger.log("forLoop count is "+ forLoopCount++);
            String currentLineOfFile="";
            String bucketName = record.getS3().getBucket().getName();
            String fileName = record.getS3().getObject().getKey();
            logger.log(bucketName+"<--bucketName pass 1 objectKey-->"+fileName);
            //1. we create S3 client
            //2. invoking GetObject
            //3. processing the InputStream from S3
            initS3Client();
            S3Object s3Object = s3Client.getObject(bucketName, fileName);
            logger.log("pass 2");
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            logger.log("pass 3");
            ApplicantTrackRecord applicantTrackRecord = new ApplicantTrackRecord();
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//                br.lines().skip(1)
//                        .forEach(line -> logger.log(line + "\n"));
                logger.log("pass 4");
                this.initDynamoDB();
                logger.log("pass 5");
                logger.log(" br is "+br);
                while((currentLineOfFile= br.readLine()) !=null){
                    logger.log("Inside while currentLineOfFile: "+currentLineOfFile);
                    String records[]=currentLineOfFile.split(",");
                    printRecords(records);
                    applicantTrackRecord=parseToObject(applicantTrackRecord, records);
                    logger.log("applicantTrackRecord obj is "+applicantTrackRecord);
                    dynamoDBMapper.save(applicantTrackRecord);
                    logger.log(recordCount+" Record saved to DB.");
                    recordCount++;
                }
                logger.log("pass 6");

            } catch (IOException e) {
                logger.log("Error occurred in Lambda:" + e.getMessage());
                return "Failed to upload Record";
            }

        }
        return "Record successfully uploaded!!";
    }

    private void printRecords(String[] records) {

        for(String record:records){
            System.out.println(record);
        }
    }

    private ApplicantTrackRecord parseToObject(ApplicantTrackRecord applicantTrackRecord, String[] records) {
        applicantTrackRecord.setCustomerId(records[0]);
        applicantTrackRecord.setLoanId(records[1]);
        applicantTrackRecord.setContractId(records[2]);
        applicantTrackRecord.setPrimaryApplicantName(records[3]);
        applicantTrackRecord.setTrackRecord(records[4]);
        applicantTrackRecord.setEligibility(records[5]);
        applicantTrackRecord.setAreaOffice(records[6]);
        applicantTrackRecord.setStatus(records[7]);
        applicantTrackRecord.setTbe(records[8]);
        applicantTrackRecord.setAbm(records[9]);
        applicantTrackRecord.setTbeName(records[10]);
        return applicantTrackRecord;
    }

    private void initDynamoDB(){
        AmazonDynamoDB client= AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper=new DynamoDBMapper(client);
    }
    private void initS3Client(){
        s3Client= AmazonS3ClientBuilder.standard().build();
    }
//    private void initDynamoDbClient() {
//        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(REGION)
//                .build();
//    }
}
