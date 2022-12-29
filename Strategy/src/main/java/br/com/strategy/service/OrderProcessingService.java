package br.com.strategy.service;

import br.com.strategy.model.Order;
import br.com.strategy.model.OrderApiResponse;
import reactor.core.publisher.Mono;

public interface OrderProcessingService {
    Mono<OrderApiResponse> processOrder(Order order);
}
