import React from "react";
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import roadmapImage from "../jess-bailey-mexeVPlTB6k-unsplash.jpg";

const Roadmap = () => (
    <Container fluid>
        <Row>
            <Col>
                <Card className="border-0">
                    <Card.Img variant="top" className="img-responsive w-25 rounded mx-auto d-block mt-3" src={roadmapImage} />
                    <Card.Title as="h3" className="mt-5 text-center">Roadmap</Card.Title>
                    <Card.Body className="text-center mb-5">This page contains a list of features that I plan to add
                        to TraMS in the future. It is sorted like a product backlog with the features at the top
                        having more chance of being implemented before the features near the bottom. Since I work on
                        TraMS in my free time, I cannot guarantee any deadlines when features will be available.
                        You can also get in touch with me if you would like to help with the development of TraMS
                        so that features can be implemented more quickly!</Card.Body>
                </Card>
            </Col>
        </Row>
        <Row>
            <Col>
                <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Browser-based Game</Accordion.Header>
                        <Accordion.Body>
                            In order to make TraMS even easier to use, it would be better to have a web-based
                            interface for the game. Currently additional software must be installed on the user's
                            computer. A browser-based system could then be used in Google Chrome, Firefox or Safari without
                            the installation of additional software.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Backup Games</Accordion.Header>
                        <Accordion.Body>
                            In order to make TraMS more reliable, it should be possible to export and import
                            all company and game data. This would also make it possible for players to
                            backup the data to prevent data loss.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>Create your own Scenario</Accordion.Header>
                        <Accordion.Body>
                            Currently TraMS can import GTFS data but the user cannot create their own scenario. It should
                            however be possible based on the GTFS data for the player to create their own scenario
                            and share this scenario with other players in an easy exchangeable format.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="3">
                        <Accordion.Header>More Gamification Elements</Accordion.Header>
                        <Accordion.Body>
                            TraMS should include more challenges such as roadworks and diversions to routes and the
                            bidding for contracts to run certain routes on behalf of the local authority which
                            the company could win or lose depending on specific conditions.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="4">
                        <Accordion.Header>Budget Planning and Monthly/Annual Accounting</Accordion.Header>
                        <Accordion.Body>
                            Vehicles, drivers and other costs should be reflected an accounting review of this month
                            and the current financial year. There should also be forecasts of these costs
                            which provide the basis of a financial plan for the coming financial year.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="5">
                        <Accordion.Header>Journey Planner</Accordion.Header>
                        <Accordion.Body>
                            It should be possible for customers to be able to plan their own journeys through the company's
                            transport network. The company should also have an overview which journeys customers are making
                            so that routes can be improved to be more relevant for the needs of the customer. This should
                            then increase the customer satisfaction rate.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="6">
                        <Accordion.Header>Security</Accordion.Header>
                        <Accordion.Body>
                            TraMS should be secured so that only the player can access their own saved games and play
                            them even if other users have access to the TraMS server.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="7">
                        <Accordion.Header>Mobile App</Accordion.Header>
                        <Accordion.Body>
                            In order to make TraMS even easier to use, it would be better to have a mobile
                            app which users could download onto their smartphone. This app should offer similar
                            functionality to the browser-based game.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="8">
                        <Accordion.Header>API for Public Holidays</Accordion.Header>
                        <Accordion.Body>
                            In order to make TraMS more realistic, it would be good to integrate an API which reads
                            public holidays. This would allow the public holiday timetables to automatically take effect
                            based on the country where the user's transport company is based.
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </Col>
        </Row>
    </Container>
);

export default Roadmap;
