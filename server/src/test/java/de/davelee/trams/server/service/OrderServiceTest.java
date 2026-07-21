package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Order;
import de.davelee.trams.server.repository.OrderRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the OrderService class - the OrderRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
        Order order = new Order();
        order.setId(ObjectId.get());
        order.setConfirmationId("feko04o24");
        order.setPaymentType("Credit Card");
        order.setQuantity(1);
        order.setTicketTargetGroup("Adult");
        order.setTicketType("Single");
        order.setQrCodeText("Adult Single 10.10.2021 12:30");
        return order;
    }

}
