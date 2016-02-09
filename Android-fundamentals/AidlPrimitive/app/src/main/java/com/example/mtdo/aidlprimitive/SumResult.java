package com.example.mtdo.aidlprimitive;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thangdo on 23/12/2015.
 */
public class SumResult implements Parcelable{
    private int         intSumResult;
    private String      strSumResult;

    @Override
    public String toString() {
        return "SumResult{" +
                "intSumResult=" + intSumResult +
                ", strSumResult='" + strSumResult + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.intSumResult);
        dest.writeString(this.strSumResult);
    }

    public static final Parcelable.Creator<SumResult> CREATOR = new Creator<SumResult>() {
        @Override
        public SumResult createFromParcel(Parcel source) {
            return new SumResult(source);
        }

        @Override
        public SumResult[] newArray(int size) {
            return new SumResult[size];
        }
    };

    public SumResult() {
    }

    public SumResult(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in){
        this.intSumResult = in.readInt();
        this.strSumResult = in.readString();
    }

    public int getIntSumResult() {
        return intSumResult;
    }

    public String getStrSumResult() {
        return strSumResult;
    }

    public void setIntSumResult(int intSumResult) {
        this.intSumResult = intSumResult;
    }

    public void setStrSumResult(String strSumResult) {
        this.strSumResult = strSumResult;
    }

}
