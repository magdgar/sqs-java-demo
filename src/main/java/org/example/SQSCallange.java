package org.example;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ChangeMessageVisibilityRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SQSCallange {
    
    public static void main(String[] args) {
        String queueName = "queue" + System.currentTimeMillis();
        SqsClient sqsClient = SqsClient.builder()
            .region(Region.US_WEST_2)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

        // Perform various tasks on the Amazon SQS queue
        String queueUrl= createQueue(sqsClient, queueName );
        System.out.println(queueUrl);
        
        listQueues(sqsClient);
        List<Message> messages = receiveMessages(sqsClient, queueUrl);
        sendBatchMessages(sqsClient, queueUrl);
        deleteMessages(sqsClient, queueUrl, messages) ;
        sqsClient.close();
    }

    /* 
     * Write function that create new queue and returns url of newly created queue
     * 
     */
    public static String createQueue(SqsClient sqsClient,String queueName ) {

        try {
            System.out.println("\nCreate Queue");

            // create CreateQueueRequest


            // use sqs client to create request


        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    public static void listQueues(SqsClient sqsClient) {

        System.out.println("\nList Queues");

        String prefix = "que";

        try {
            // build ListQueuesRequest with que name prefix


            // use sqs client to get filtered queues and print them out

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void sendBatchMessages(SqsClient sqsClient, String queueUrl) {

        System.out.println("\nSend multiple messages");
        try {
            //build SendMessageBatchRequest


            //use sqsClient to send messages

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static List<Message> receiveMessages(SqsClient sqsClient, String queueUrl) {

        System.out.println("\nReceive messages");
        try {
            // build ReceiveMessageRequest

             
            //use sqsClient to receive messages

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    public static void deleteMessages(SqsClient sqsClient, String queueUrl, List<Message> messages) {
        System.out.println("\nDelete Messages");

        try {
            for (Message message : messages) {
                // build deleteMessageRequest


                //use sqsClient to egzecute deleteMessagesRequest    
                
            }

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
   }
}
