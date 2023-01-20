# App

This project contains a maven application with [AWS Java SDK 2.x](https://github.com/aws/aws-sdk-java-v2) dependencies.

## Prerequisites
- log-in to your account on event engine
- create Cloud9 environment (with t3.medium instance)
- in Cloud9 terminal run `vi set_up_script.sh`
- in vi go type `:set paste` then click enter and `i`
- and copy paste script

```
#!/bin/bash
date
echo LANG=en_US.utf-8 >> /etc/environment
echo LC_ALL=en_US.UTF-8 >> /etc/environment
. /home/ec2-user/.bashrc
echo '=== INSTALL and CONFIGURE CLI ==='
pip install --user --upgrade awscli
echo Installing Maven
sudo wget https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.tar.gz
tar xvf apache-maven-3.8.7-bin.tar.gz
sudo mv apache-maven-3.8.7  /usr/local/apache-maven
echo 'export M2_HOME=/usr/local/apache-maven' >> ~/.bashrc
echo 'export M2=$M2_HOME/bin' >> ~/.bashrc
echo 'export PATH=$M2:$PATH' >> ~/.bashrc
. /home/ec2-user/.bashrc
echo done 
echo Cloning SQSJavaDemo repo
git clone https://github.com/magdgar/sqs-java-demo.git
echo done 
echo '=== Setup AWS CLI ==='
sudo -i -H -u ec2-user bash -c "mkdir -p ~/.aws"
sudo -i -H -u ec2-user bash -c "echo -e '[default]\nregion=eu-west-1\n' > ~/.aws/config"
echo '=== Setting up code ==='
echo '=== DONE ==='
```
- click esc and type `: wq!`
- execute script `. set_up_script.sh` 
- run `. ~/.bashrc`
- and change directory to sqs-java demo by runing `cd sqs-java-demo/`

## Development

Below is the structure of project.

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── package
│   │   │       ├── SQSCallange.java
│   │   │       └── SQSExample.java
│   │   └── resources
│   │       └── simplelogger.properties
│   └── test
│       └── java
│           └── package
│               └── SQSExampleTest.java
```

- `SQSCallange.java`: main entry of the application
- `SQSExample.java`: sample solutions for challanges

#### Building the project
```
mvn clean package
```

#### Running java class
```
mvn compile exec:java -Dexec.mainClass="org.example.SQSCallange"
```


# SQS Java Lab

See 
[SQS Java docs](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/package-summary.html) for more details

Please, make sure that your `sqsClient` is configured to use **the same region** as your Cloud9 environment.

## Challange 1: Create sqs queue using java sdk
#### Hint
Use 
[SQSClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/SqsClient.html) and [CreateQueueRequest](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/model/CreateQueueRequest.html)

#### Solution 
<details>

```
    CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
        .queueName(queueName)
        .build();

    sqsClient.createQueue(createQueueRequest);

    System.out.println("\nGet queue url");

    GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
    return getQueueUrlResponse.queueUrl();
```
</details>

## Challange 2: List existing queues
#### Hint
Use 
[SQSClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/SqsClient.html) and [ListQueuesRequest](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/model/ListQueuesRequest.html)

#### Solution 
<details>

```
    ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().queueNamePrefix(prefix).build();
    ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);
    for (String url : listQueuesResponse.queueUrls()) {
        System.out.println(url);
    }
```
</details>

## Challange 3: Send bach messages
#### Hint
Use 
[SQSClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/SqsClient.html) and [SendMessageBatchRequest](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/model/SendMessageBatchRequest.html)

#### Solution 
<details>

```
    SendMessageBatchRequest sendMessageBatchRequest = SendMessageBatchRequest.builder()
        .queueUrl(queueUrl)
        .entries(SendMessageBatchRequestEntry.builder().id("id1").messageBody("Hello from msg 1").build(),
            SendMessageBatchRequestEntry.builder().id("id2").messageBody("msg 2").delaySeconds(10).build())
        .build();
    sqsClient.sendMessageBatch(sendMessageBatchRequest);
```
</details>

## Challange 4: Receive Messages
#### Hint
Use 
[SQSClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/SqsClient.html) and [ReceiveMessageRequest](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/model/ReceiveMessageRequest.html)


#### Solution 
<details>

```
    ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
        .queueUrl(queueUrl)
        .maxNumberOfMessages(5)
        .build();
    return sqsClient.receiveMessage(receiveMessageRequest).messages();
```
</details>

## Challange 5: Delete Messages
#### Hint
Use 
[SQSClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/SqsClient.html) and [DeleteMessageRequest](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/model/DeleteMessageRequest.html)

#### Solution 
<details>

```
    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
        .queueUrl(queueUrl)
        .receiptHandle(message.receiptHandle())
        .build();
    sqsClient.deleteMessage(deleteMessageRequest);
```
</details>

## Links

[AWS Java Developer Guide for version 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/java_sqs_code_examples.html).

