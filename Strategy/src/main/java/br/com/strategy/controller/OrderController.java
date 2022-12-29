package br.com.strategy.controller;

import br.com.strategy.model.Order;
import br.com.strategy.model.OrderApiResponse;
import br.com.strategy.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final Map<String, OrderProcessingService> orderProcessingServices;

    public OrderController(
            Map<String, OrderProcessingService> orderProcessingServices) {
        this.orderProcessingServices = orderProcessingServices;
    }

    @PostMapping("/order")
    public Mono<OrderApiResponse> onNewOrder(@RequestBody Order receivedOrder) {

        return this.orderProcessingServices.get(receivedOrder.getOrderType().name())
                .processOrder(receivedOrder);

    }
}
