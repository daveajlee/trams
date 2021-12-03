package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Order;
import de.davelee.trams.crm.model.Ticket;
import de.davelee.trams.crm.request.PurchaseTicketRequest;
import de.davelee.trams.crm.response.PurchaseTicketResponse;
import de.davelee.trams.crm.services.OrderService;
import de.davelee.trams.crm.services.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * This class defines the endpoints for the REST API which manipulate a single order and delegates the actions to the OrderService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/api/order")
@RequestMapping(value="/api/order")
public class OrderController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Order a ticket", notes="Method to process an order for a ticket")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successful operation"), @ApiResponse(code=400,message="Bad request"),
            @ApiResponse(code=500,message="Payment could not be processed")})
    /**
     * Order a ticket based on the supplied request. Credit card number must be fake for data protection.
     * @param purchaseTicketRequest a <code>PurchaseTicketRequest</code> object containing the ticket to be purchased.
     * @return a <code>ResponseEntity</code> object which is either 200, 400 or 500 depending on
     * whether request was valid and payment was successful.
     */
    public ResponseEntity<PurchaseTicketResponse> orderTicket (@RequestBody final PurchaseTicketRequest purchaseTicketRequest ) {
        //First of all check that fields are not empty.
        if (StringUtils.isBlank(purchaseTicketRequest.getTicketType()) || StringUtils.isBlank(purchaseTicketRequest.getCompany())
                || StringUtils.isBlank(purchaseTicketRequest.getTicketTargetGroup()) || StringUtils.isBlank(purchaseTicketRequest.getCreditCardExpiryDate())
                || StringUtils.isBlank(purchaseTicketRequest.getCreditCardNumber()) || StringUtils.isBlank(purchaseTicketRequest.getCreditCardType())
                || StringUtils.isBlank(purchaseTicketRequest.getCreditCardSecurityCode())) {
            return ResponseEntity.badRequest().build();
        }
        //Second check that credit card is only 12 characters for data protection.
        if ( purchaseTicketRequest.getCreditCardNumber().length() != 12 ) {
            return ResponseEntity.badRequest().build();
        }
        //Third, ensure that credit card expiry is not in the past.
        String[] monthYearSplit = purchaseTicketRequest.getCreditCardExpiryDate().split("/");
        YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(monthYearSplit[1]), Integer.parseInt(monthYearSplit[0]));
        int daysInMonth = yearMonthObject.lengthOfMonth();
        LocalDate expiryDate = LocalDate.of(Integer.parseInt(monthYearSplit[1]), Integer.parseInt(monthYearSplit[0]), daysInMonth);
        if ( LocalDate.now().isAfter(expiryDate) ) {
            return ResponseEntity.badRequest().build();
        }
        //Fourth, ensure that the price matches the expected payment.
        List<Ticket> ticketList = ticketService.findByCompanyAndType(purchaseTicketRequest.getCompany(), purchaseTicketRequest.getTicketType());
        if ( ticketList.size() != 1 ) {
            return ResponseEntity.badRequest().build();
        } else {
            BigDecimal price = ticketList.get(0).getPriceList().get(purchaseTicketRequest.getTicketTargetGroup());
            if ( price == null || (price.doubleValue()* purchaseTicketRequest.getQuantity()) != (purchaseTicketRequest.getPrice()* purchaseTicketRequest.getQuantity())) {
                return ResponseEntity.badRequest().build();
            }
        }

        Order order = Order.builder()
                .ticketType(purchaseTicketRequest.getTicketType())
                .ticketTargetGroup(purchaseTicketRequest.getTicketTargetGroup())
                .quantity(purchaseTicketRequest.getQuantity())
                .paymentType("Credit Card")
                .confirmationId(UUID.randomUUID().toString())
                .qrCodeText(generateQRCode(purchaseTicketRequest))
                .build();
        boolean result = orderService.save(order);
        return result ? ResponseEntity.ok(PurchaseTicketResponse.builder()
                .success(true)
                .qrCode(encodeQRCode(generateQRCode(purchaseTicketRequest)))
                .build()) :
                ResponseEntity.internalServerError().body(PurchaseTicketResponse.builder()
                        .success(false)
                        .errorMessage("Could not save in database")
                        .build());
    }

    /**
     * This is a private helper method which generates the qr code text for the ticket
     * @param purchaseTicketRequest a <code>PurchaseTicketRequest</code> with details of the ticket bought
     * @return a <code>String</code> with the text of the qr code for the ticket
     */
    private String generateQRCode ( final PurchaseTicketRequest purchaseTicketRequest ) {
        StringBuilder qrCodeBuilder = new StringBuilder();
        qrCodeBuilder.append(purchaseTicketRequest.getTicketTargetGroup());
        qrCodeBuilder.append(" ");
        qrCodeBuilder.append(purchaseTicketRequest.getTicketType());
        qrCodeBuilder.append(" ");
        qrCodeBuilder.append(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()));
        qrCodeBuilder.append(" ");
        qrCodeBuilder.append(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()));
        return qrCodeBuilder.toString();
    }

    /**
     * This is a private helper method which encodes the qr code image to base 64
     * @param qrCodeText a <code>String</code> with details of the ticket bought
     * @return a <code>String</code> with the encoded image in base 64
     */
    private String encodeQRCode ( final String qrCodeText ) {
        File file = QRCode.from(qrCodeText).file();
        try {
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch ( Exception e ) {
            return "";
        }
    }

}
