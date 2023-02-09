package com.example.demo.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.springboot.sns.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Causes Lombok to generate a logger field.
@Slf4j
@RestController
public class SnsController {

    // Topic arn. Developers are free to choose their topic arn.
    private static final String TOPIC_ARN = "TOPIC_ARN";

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    // URL - http://localhost:10093/addSubscription/+911234567890
    // NOTE - In this tutorial, we are skipping the phone number validation part. Trust that you'll add a valid phone number.
    @PostMapping(value = "/addSubscription/{phoneNumber}")
    public ResponseEntity<String> addSubscription(@PathVariable final String phoneNumber) {
        log.info("Adding new phone number subscription = {} to the topic.", phoneNumber);
        final SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "sms", phoneNumber);
        amazonSNSClient.subscribe(subscribeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL - http://localhost:10093/sendNotification
    // Sample request body -
    //  {
    //      "message": "Lorem Ipsum is simply dummied text of the printing and typesetting industry."
    //  }
    @PostMapping(value = "/sendNotification")
    public ResponseEntity<String> publishMessageToTopic(@RequestBody final Notification notification) {
        log.info("Publishing the notification = {} to the topic.", notification.toString());
        final PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, notification.getMessage());
        amazonSNSClient.publish(publishRequest);
        return new ResponseEntity<>("Notification sent successfully!!", HttpStatus.OK);
    }
}
