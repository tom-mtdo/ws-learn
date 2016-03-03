package com.mtdo.learn.mywsappclient;

import com.mtdo.learn.mywshelloservice.Hello;
import com.mtdo.learn.mywshelloservice.HelloService;
import javax.xml.ws.WebServiceRef;

public class HelloAppClient {
    public static void main(String[] args){
        HelloService port = new HelloService();
        Hello stub = port.getHelloPort();
        System.out.println(stub.sayHello("world"));
    }
}
