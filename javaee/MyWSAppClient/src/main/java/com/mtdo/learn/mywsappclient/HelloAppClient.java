package com.mtdo.learn.mywsappclient;

import com.mtdo.learn.mywshelloservice.Hello;
import com.mtdo.learn.mywshelloservice.HelloService;
import javax.xml.ws.WebServiceRef;

public class HelloAppClient {
// Method 1 user JEE injection, run in container
//    @WebServiceRef(wsdlLocation = "http://thangs-macbook-pro.local:8080/MyWSHelloService/HelloService?wsdl")
//    static HelloService service;
    
// Method 2 manually create concreate instance of class    
    static HelloService service = new HelloService();
    
    public static void main(String[] args){
        Hello port = service.getHelloPort();
        System.out.println(port.sayHello("world"));
    }
}
