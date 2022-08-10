package com.example.donatetoy.model;

public class RequestFriend {
    private String transmitterUid;
    private String receiverUid;

    public RequestFriend(String transmitterUid, String receiverUid) {
        this.transmitterUid = transmitterUid;
        this.receiverUid = receiverUid;
    }

    public String getTransmitterUid() {
        return transmitterUid;
    }

    public void setTransmitterUid(String transmitterUid) {
        this.transmitterUid = transmitterUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }
}
