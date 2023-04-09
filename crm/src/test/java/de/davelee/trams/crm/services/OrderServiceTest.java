package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Order;
import de.davelee.trams.crm.repository.OrderRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the OrderService class - the OrderRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    /**
     * Test case: save a new order.
     * Expected Result: true.
     */
    @Test
    public void testSaveFeedback() {
        //Test data
        Order order = generateValidOrder();
        //Mock important method in repository.
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        //do actual test.
        assertTrue(orderService.save(order));
    }

    /**
     * Private helper method to generate a valid order.
     * @return a <code>Order</code> object containing valid test data.
     */
    private Order generateValidOrder( ) {
        return Order.builder()
                .id(ObjectId.get())
                .confirmationId("feko04o24")
                .paymentType("Credit Card")
                .quantity(1)
                .ticketTargetGroup("Adult")
                .ticketType("Single")
                .qrCodeText("Adult Single 10.10.2021 12:30")
                .build();
    }

}
