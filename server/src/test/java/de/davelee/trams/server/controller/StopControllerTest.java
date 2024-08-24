package de.davelee.trams.server.controller;

import de.davelee.trams.server.request.AddStopRequest;
import de.davelee.trams.server.service.StopService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * This class tests the StopController and ensures that the endpoints work successfully. It uses
 * mocks for the service and database layers.
 * @author Dave Lee
 */
@SpringBootTest
public class StopControllerTest {

    @InjectMocks
    private StopController stopController;

    @Mock
    private StopService stopService;

    /**
     * Test the add stop endpoint of this controller.
     */
    @Test
    public void testStopEndpoint() {
        //Mock important method.
        Mockito.when(stopService.addStop(any())).thenReturn(true);
        //Test success stop.
        AddStopRequest addStopRequest = AddStopRequest.builder()
                .company("Example Company")
                .name("Park Avenue")
                .latitude(53.821067)
                .longitude(14.106563)
                .build();
        assertEquals(HttpStatus.CREATED, stopController.addStop(addStopRequest).getStatusCode());
        //Test unsuccessful stop.
        Mockito.when(stopService.addStop(any())).thenReturn(false);
        AddStopRequest addStopRequest2 = new AddStopRequest();
        addStopRequest2.setCompany("Example Company");
        addStopRequest2.setName("Sea Beach");
        addStopRequest2.setLatitude(53.821067);
        addStopRequest2.setLongitude(14.106563);
        assertEquals(500, stopController.addStop(addStopRequest2).getStatusCode().value());
        //Test missing company and name.
        addStopRequest2.setCompany(""); addStopRequest2.setName("");
        assertEquals(HttpStatus.BAD_REQUEST, stopController.addStop(addStopRequest2).getStatusCode());
    }

}
