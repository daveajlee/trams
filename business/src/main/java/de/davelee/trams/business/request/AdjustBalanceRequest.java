package de.davelee.trams.business.request;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a request to adjust the balance
 * by either crediting or withdrawing money for a particular company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdjustBalanceRequest {

    /**
     * The name of the company to adjust the balance.
     */
    private String company;

    /**
     * The value to either subtract (minus) or credit (plus) to the balance.
     */
    private double value;

}
