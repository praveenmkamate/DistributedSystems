package service.message;

import service.core.Quotation;

import java.io.Serializable;

public class QuotationResponseMessage implements Serializable {
    public int id;
    public Quotation quotation;
    public QuotationResponseMessage(int id, Quotation quotation) {
        this.id = id;
        this.quotation = quotation;
    }
}