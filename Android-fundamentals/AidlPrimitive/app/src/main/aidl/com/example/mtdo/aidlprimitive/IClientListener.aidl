// IClientListener.aidl
package com.example.mtdo.aidlprimitive;

import com.example.mtdo.aidlprimitive.SumResult;

// Declare any non-default types here with import statements

interface IClientListener {
    void showResult(in SumResult objResult);
}
