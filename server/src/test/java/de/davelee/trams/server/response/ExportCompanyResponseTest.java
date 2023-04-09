package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the ExportCompanyResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class ExportCompanyResponseTest {

    /**
     * Ensure that a ExportCompanyResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        ExportCompanyResponse exportCompanyResponse = new ExportCompanyResponse();
        exportCompanyResponse.setName("Mustermann GmbH und Co");
        exportCompanyResponse.setPlayerName("Max Mustermann");
        exportCompanyResponse.setDrivers("{name=\"Max Mustermann\"}");
        exportCompanyResponse.setMessages("{subject=\"Test\"}");
        exportCompanyResponse.setRoutes("{number=\"1A\"}");
        exportCompanyResponse.setVehicles("{Type=\"Bus\"}");
        exportCompanyResponse.setBalance(20000.0);
        exportCompanyResponse.setDifficultyLevel("HARD");
        exportCompanyResponse.setSatisfactionRate(100.0);
        exportCompanyResponse.setScenarioName("Landuff");
        exportCompanyResponse.setTime("28-12-2020 14:22");
        assertEquals("ExportCompanyResponse(name=Mustermann GmbH und Co, balance=20000.0, playerName=Max Mustermann, satisfactionRate=100.0, time=28-12-2020 14:22, scenarioName=Landuff, difficultyLevel=HARD, routes={number=\"1A\"}, drivers={name=\"Max Mustermann\"}, vehicles={Type=\"Bus\"}, messages={subject=\"Test\"})", exportCompanyResponse.toString());
    }

}
