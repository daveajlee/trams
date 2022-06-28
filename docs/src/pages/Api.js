import React from "react";
import SwaggerUI from "swagger-ui-react"
import "swagger-ui-react/swagger-ui.css"
import {Card, Col, Container, Row, Tab, Tabs} from "react-bootstrap";
import apiImage from "../douglas-lopes-ehyV_XOZ4iA-unsplash.jpg";

const Api = () => (
    <Container fluid>
        <Row>
            <Col>
                <Card>
                    <Card.Img variant="top" className="img-responsive w-25 rounded mx-auto d-block mt-3" src={apiImage} />
                    <Card.Title className="mt-5 text-center">API</Card.Title>
                    <Card.Body className="text-center mb-5">The current APIs of TraMS are displayed below. TraMS is
                        split into three APIs (TraMS Business API, TraMS Customer API and Trams Operations API). Click
                        on a tab to find out what the API is responsible for.
                        Please note that these APIs should only be used as reference documentation. The functionality to
                    try out the APIs are not available on this page. If you would like to test the APIs or test a
                    client you have built for the APIs then you need to download the relevant TraMS server
                        for the API you which to use from the Download page.</Card.Body>
                </Card>
            </Col>
        </Row>
        <Row>
            <Col>
                <Tabs defaultActiveKey="business" id="api-tabs" className="mb-3">
                    <Tab eventKey="business" title="TraMS Business API">
                        <h5>TraMS Business API covers everything to do with the general creation and overview of a company.</h5>
                        <SwaggerUI url="swagger-business.json" />
                    </Tab>
                    <Tab eventKey="crm" title="TraMS CRM API">
                        <h5>TraMS CRM API covers everything to do with the customer and revenue management of a company.</h5>
                        <SwaggerUI url="swagger-crm.json" />
                    </Tab>
                    <Tab eventKey="operations" title="TraMS Operations API">
                        <h5>TraMS Operations API covers everything to do with the day-to-day management of a company.</h5>
                        <SwaggerUI url="swagger-operations.json" />
                    </Tab>
                </Tabs>
            </Col>
        </Row>
    </Container>
);

export default Api;
