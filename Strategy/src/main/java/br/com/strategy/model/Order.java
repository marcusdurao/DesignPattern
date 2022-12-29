package br.com.strategy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    public enum MethodOfPayment {
        CREDIT_CARD,
        DEBIT_CARD,
        CORPORATE_ACCOUNT
    }

    public enum OrderType{
        CORPORATE,
        INDIVIDUAL
    }

    private Customer customer;
    private OrderType orderType;
    private String corpCode;
    private MethodOfPayment methodOfPayment;
    private Corporation corporation;
    private List<String> articles;
}
