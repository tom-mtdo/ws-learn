
package com.mtdo.learn.myws.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mtdo.learn.myws.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Greeting_QNAME = new QName("http://service.myws.learn.mtdo.com/", "Greeting");
    private final static QName _GreetingResponse_QNAME = new QName("http://service.myws.learn.mtdo.com/", "GreetingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mtdo.learn.myws.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Greeting }
     * 
     */
    public Greeting createGreeting() {
        return new Greeting();
    }

    /**
     * Create an instance of {@link GreetingResponse }
     * 
     */
    public GreetingResponse createGreetingResponse() {
        return new GreetingResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Greeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.myws.learn.mtdo.com/", name = "Greeting")
    public JAXBElement<Greeting> createGreeting(Greeting value) {
        return new JAXBElement<Greeting>(_Greeting_QNAME, Greeting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GreetingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.myws.learn.mtdo.com/", name = "GreetingResponse")
    public JAXBElement<GreetingResponse> createGreetingResponse(GreetingResponse value) {
        return new JAXBElement<GreetingResponse>(_GreetingResponse_QNAME, GreetingResponse.class, null, value);
    }

}
