package service.message;

import service.core.ClientInfo;

import java.io.Serializable;

public class QuotationRequestMessage implements Serializable {
    public int id;
    public ClientInfo info;
    public QuotationRequestMessage(int id, ClientInfo info) {
        this.id = id;
        this.info = info;
    }
}