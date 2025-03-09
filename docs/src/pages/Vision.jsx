import React from "react";
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import visionImage from "../joshua-earle-Dwheufds6kQ-unsplash.jpg";

const Vision = () => (
    <Container fluid>
        <Row>
            <Col>
                <Card className="border-0">
                    <Card.Img variant="top" className="img-responsive w-25 rounded mx-auto d-block mt-3" src={visionImage} />
                    <Card.Title as="h3" className="mt-5 text-center">TraMS Vision</Card.Title>
                    <Card.Body className="text-center mb-5">TraMS provides a realistic
                        simulation and gamification of public transport management from a public transport company perspective.</Card.Body>
                </Card>
            </Col>
        </Row>
        <Row>
            <Col>
                <h3 className="mt-5 text-center">Goals</h3>
                <Accordion className="mt-3">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Realistic daily operational process management</Accordion.Header>
                        <Accordion.Body>
                            TraMS should simulate all of the normal daily operational processes within public
                            transport management from a public transport company perspective. This includes processes
                            such as route, vehicle, driver and timetable management.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Gamification of management decisions</Accordion.Header>
                        <Accordion.Body>
                            TraMS should help and challenge the player to make the correct management decisions in order
                            for their business to survive the challenges of every day life within a public transport
                            company.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>Scenario-based Gamification</Accordion.Header>
                        <Accordion.Body>
                            TraMS should ship with a number of scenarios which the user can choose to play and attempt
                            to build a successful public transport company for the scenario. It should also be possible
                            for other non-developers to be able to create new scenarios which can be shared with other
                            players.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="3">
                        <Accordion.Header>High performance and reliability</Accordion.Header>
                        <Accordion.Body>
                            TraMS should provide all users and players with good performance and reliability.
                            They should get quick feedback whenever they make a request and no data
                            should be intentionally lost.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="4">
                        <Accordion.Header>Easy to use</Accordion.Header>
                        <Accordion.Body>
                            In order to make TraMS even easier to use, it would be better to have a web-based
                            interface for all users. Currently additional software must be installed on the user's
                            computer. A browser-based system could then be used in Google Chrome, Firefox or Safari without
                            the installation of additional software.
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </Col>
        </Row>
    </Container>
);

export default Vision;
