package br.com.strategy.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderApiResponse {
    private int status;
    private String orderStatus;
    private Order order;
}
