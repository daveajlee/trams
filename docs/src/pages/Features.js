import React from "react";
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import roadmapImage from "../jess-bailey-mexeVPlTB6k-unsplash.jpg";

const Features = () => (
    <Container fluid>
        <Row>
            <Col>
                <Card>
                    <Card.Img variant="top" className="img-responsive w-25 rounded mx-auto d-block mt-3" src={roadmapImage} />
                    <Card.Title className="mt-5 text-center">Features</Card.Title>
                    <Card.Body className="text-center mb-5">This page contains information about features that are already
                    implemented in TraMS. <br/> Please visit the Roadmap page if you would like to find out more
                    about new features that are planned.</Card.Body>
                </Card>
            </Col>
        </Row>
        <Row>
            <Col>
                <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Companies</Accordion.Header>
                        <Accordion.Body>
                            Each company has a name, a balance, the name of the player running the company,
                            the scenario that the user is playing and the difficulty level. There are three scenarios
                            which are shipped as part of TraMS. The difficulty level affects the number of delays to
                            vehicles belonging to the company within a period of time. It is possible to export company
                            data but it is not yet possible to import the data (see roadmap).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Simulation and Passenger Satisfaction</Accordion.Header>
                        <Accordion.Body>
                            The player can advance the simulated day at any point that they wish to. During the
                            simulation, drivers and vehicles will attempt to run to timetable but delays may occur.
                            This will cause the passenger satisfaction rate (%) to drop. The player can then decide to
                            stop the simulation and improve the situation for passengers by redirecting drivers and vehicles.
                            This after a while will cause the passenger satisfaction rate (%) to go up again.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>Customer Feedback</Accordion.Header>
                        <Accordion.Body>
                            Customers can provide feedback to the transport company on their performance. This helps
                            the player to understand why customers are unhappy. It is possible for the transport company
                            to answer the feedback of the customer but the transport company will not known if this
                            makes a difference to the customer's opinion of the company.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="3">
                        <Accordion.Header>Tickets & Subscriptions</Accordion.Header>
                        <Accordion.Body>
                            Customers can purchase tickets for their journey and also buy subscriptions for monthly
                            or yearly passes which provides much needed regular income for the company.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="4">
                        <Accordion.Header>Messages</Accordion.Header>
                        <Accordion.Body>
                            Each scenario has a local authority which will send occasional messages to your transport
                            company. This can be both positive messages such as requesting that you provide an additional
                            route to meet the needs of residents or negative messages demanding better performance
                            within a certain period of time. The player should take time to read these messages and
                            react accordingly!
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="5">
                        <Accordion.Header>GTFS</Accordion.Header>
                        <Accordion.Body>
                            The scenarios currently shipped with TraMS generate fictional routes, stops and timetables.
                            It is however also possible to import GTFS data into TraMS. GTFS is the General Transit
                            Feed Specification format from Google (<a href="https://developers.google.com/transit/gtfs">https://developers.google.com/transit/gtfs</a>).
                            This will eventually make it possible for players to generate their own scenarios (see roadmap).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="6">
                        <Accordion.Header>Vehicles</Accordion.Header>
                        <Accordion.Body>
                            Each vehicle has a fleet number, model name, vehicle type (e.g. bus, train, tram), livery,
                            seating and standing capacity. Vehicles may be allocated to routes but are only allowed to
                            work a certain number of hours a day. Vehicles need to be regularly inspected to ensure
                            they are in optimal condition. Vehicles which are no longer required by a company can be sold
                            but they will lose value over time.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="7">
                        <Accordion.Header>Drivers</Accordion.Header>
                        <Accordion.Body>
                            The management of drivers within TraMS is performed through the integration of PersonalMan.
                            Therefore all of the features that are available in PersonalMan are available within TraMS
                            for drivers. You can find out more about the features of PersonalMan <a href="https://www.davelee.de/personalman/#/features">here</a>.
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </Col>
        </Row>
    </Container>
);

export default Features;
