
package com.mtdo.learn.mywshelloservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Hello {

    public Hello() {
    }
    
    @WebMethod
    public String sayHello(String name){
        return "Hello " + name + ".";
    }
}
