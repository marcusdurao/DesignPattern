package br.com.strategy.service;

import br.com.strategy.model.Corporation;
import br.com.strategy.model.Order;
import br.com.strategy.model.OrderApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component("CORPORATE")
public class CorporateOrderProcessingService implements OrderProcessingService{
    private Map<String, Corporation> corporationMap;

    public CorporateOrderProcessingService() {
        this.corporationMap = new HashMap<>();
        this.corporationMap
                .put("GOOGLE", Corporation.builder().companyName("Google Inc").corpCode("GOOGLE").build());
        this.corporationMap
                .put("MICRO", Corporation.builder().companyName("Microsoft").corpCode("MICRO").build());
    }

    @Override
    public Mono<OrderApiResponse> processOrder(Order order) {
        return Mono.just(order)
                .handle(this.handleOrder());
    }

    private BiConsumer<Order, SynchronousSink<OrderApiResponse>> handleOrder() {
        return (order, sink) -> {
            if (order.getCorpCode() == null) {
                sink.next(OrderApiResponse.builder().order(order).orderStatus("INVALID - No Corp Code")
                        .status(HttpStatus.BAD_REQUEST.value()).build());
            } else {
                if (this.corporationMap.containsKey(order.getCorpCode())) {
                    order.setCorporation(this.corporationMap.get(order.getCorpCode()));
                    order.setMethodOfPayment(Order.MethodOfPayment.CORPORATE_ACCOUNT);
                    sink.next(OrderApiResponse.builder().order(order).orderStatus("PROCESSED")
                            .status(HttpStatus.ACCEPTED.value()).build());
                    return;
                }
                sink.next(
                        OrderApiResponse.builder().order(order).orderStatus("INVALID - Unknown Corp Code ")
                                .status(HttpStatus.BAD_REQUEST.value()).build());
            }
        };
    }
}
