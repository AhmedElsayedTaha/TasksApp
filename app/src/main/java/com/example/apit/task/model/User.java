package com.example.apit.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("USER_NO")
    @Expose
    private Integer uSERNO;
    @SerializedName("USER_NICK_NAME")
    @Expose
    private String uSERNICKNAME;
    @SerializedName("USER_NAME")
    @Expose
    private String uSERNAME;
    @SerializedName("USER_ID_NUMBER")
    @Expose
    private String uSERIDNUMBER;
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;

    public Integer getUSERNO() {
        return uSERNO;
    }

    public void setUSERNO(Integer uSERNO) {
        this.uSERNO = uSERNO;
    }

    public String getUSERNICKNAME() {
        return uSERNICKNAME;
    }

    public void setUSERNICKNAME(String uSERNICKNAME) {
        this.uSERNICKNAME = uSERNICKNAME;
    }

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getUSERIDNUMBER() {
        return uSERIDNUMBER;
    }

    public void setUSERIDNUMBER(String uSERIDNUMBER) {
        this.uSERIDNUMBER = uSERIDNUMBER;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
