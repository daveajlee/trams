import React from "react";
import {Accordion, Col, Container, Row} from "react-bootstrap";
import architectureFigure from "../architecture.png";
import architectureSmall from "../architecture-mobile.png";
import MediaQuery from 'react-responsive';

const Architecture = () => (
    <Container fluid>
        <Row>
            <Col>
                <h2 className="mt-5 text-center">Architecture</h2>
                <MediaQuery query="(max-device-width: 700px)">
                    <Col className="text-center mt-5"><img src={architectureSmall} alt="TraMS"
                                                           className="img-responsive"/></Col>
                </MediaQuery>
                <MediaQuery query="(min-device-width: 701px)">
                    <Col className="text-center mt-5"><img src={architectureFigure} alt="TraMS"
                                                           className="img-responsive"/></Col>
                </MediaQuery>
            </Col>
        </Row>
        <Row>
            <Col>
                <p className="mt-5">The current architecture of TraMS is based on the classical client server architecture
                pattern. This pattern ensures a separation of concern between the client and the server. The communication between
                    the client and server is managed through an API. A description of each part of the architecture diagram
                    follows in the next section.</p>
            </Col>
        </Row>
        <Row>
            <Col>
                <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Clients (TraMS Desktop Client)</Accordion.Header>
                        <Accordion.Body>
                            The current architecture of TraMS allows multiple clients to be built - each client
                            simply has to implement the API provided by the server. Currently there is only one client
                            which is available for download: the TraMS Frontend Client which runs in Electron and
                            supports both an online mode (with server) and offline mode (without server). This client is
                            typically restricted to Desktop or Laptop systems. A mobile app is possible in the current
                            architecture and is part of the roadmap of TraMS development.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Load Balancing (Eureka)</Accordion.Header>
                        <Accordion.Body>
                            Whilst not an absolute requirement for a client server architecture, load balancing and
                            service registries is an important topic for this architecture pattern. TraMS
                            integrates Eureka per default but other load balancer systems can also be used if an
                            organisation needs to run multiple instances using an alternative technology.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>TraMS Server</Accordion.Header>
                        <Accordion.Body>
                            The server is the brain behind TraMS. It manages the complete backend processes 
                            to run a transport company including customers, revenue, routes, stops, timetables and vehicles.
                            It responds to all of the requests for data that are sent by any client. It also 
                            uses <a href="https://www.davelee.de/personalman/">PersonalMan Server</a> to support employee management.
                            The server is stateless and implements a REST API.
                            This ensures a simple and easy-to-use API which clients can then implement. It is currently
                            built in Java using Spring Boot but can optionally also be deployed via Docker container(s).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="3">
                        <Accordion.Header>Database (MongoDB)</Accordion.Header>
                        <Accordion.Body>
                            TraMS uses a database to save all of the data. This data can theoretically be stored in any
                            type of database with minimal additional effort. In the
                            current implementation, MongoDB as a NoSQL database is used to ensure a quick answer to all
                            of the requests from the server. In order to safeguard consistency, only the server is allowed
                            to communicate with the database.
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </Col>
        </Row>
    </Container>
);

export default Architecture;
