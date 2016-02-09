// ISumService.aidl
package com.example.mtdo.aidlprimitive;

import com.example.mtdo.aidlprimitive.IClientListener;

interface ISumService {
    long sum(long n);
    void addListener(IClientListener listener);
}
