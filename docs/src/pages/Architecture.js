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
                <MediaQuery query="(max-device-width: 900px)">
                    <Col className="text-center mt-5"><img src={architectureSmall} alt="TraMS"
                                                           className="img-responsive"/></Col>
                </MediaQuery>
                <MediaQuery query="(min-device-width: 901px)">
                    <Col className="text-center mt-5"><img src={architectureFigure} alt="TraMS"
                                                           className="img-responsive"/></Col>
                </MediaQuery>
            </Col>
        </Row>
        <Row>
            <Col>
                <p className="mt-5">The current architecture of TraMS is based a variation of the classical client server architecture
                pattern. This pattern ensures a separation of concern between the client and the server. In the case of
                TraMS, the server is split into three smaller services which are all independent of one another and have
                    a separate database. This ensures a separation of data and that each service only has the data it
                    needs without being concerned what other data exists in the other services. The communication between
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
                            simply has to implement the APIs provided by the three server services. Currently there is only one client
                            which is available for download: the TraMS Desktop Java Client which contains an integrated
                            version of the TraMS servers. This client is typically restricted to Desktop or Laptop systems. A responsive
                            browser-based client or a mobile app is possible in the current architecture and is part of
                            the roadmap of TraMS development.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Proxy Server (NGINX)</Accordion.Header>
                        <Accordion.Body>
                            The proxy server allows clients to use more user friendly addresses and prevents the need
                            for individual ports to be used by the client. It also allows the server services to move without
                            having to change the addresses in the client.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="2">
                        <Accordion.Header>Load Balancing (Eureka)</Accordion.Header>
                        <Accordion.Body>
                            Whilst not an absolute requirement for a client server architecture, load balancing and
                            service registries is an important topic for this architecture pattern. TraMS
                            integrates Eureka per default but other load balancer systems can also be used if an
                            organisation needs to run multiple instances using an alternative technology.
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="3">
                        <Accordion.Header>TraMS Business Server</Accordion.Header>
                        <Accordion.Body>
                            The business server is the brain behind TraMS when it comes to companies. It manages the
                            whole dataset around companies and implements the complete feature set for this domain.
                            It responds to all of the requests for company data that are sent by any client.
                            The server is stateless and implements a REST API.
                            This ensures a simple and easy-to-use API which clients can then implement. It is currently
                            built in Java using Spring Boot but can optionally also be deployed via Docker container(s).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="4">
                        <Accordion.Header>TraMS CRM Server</Accordion.Header>
                        <Accordion.Body>
                            The CRM server is the brain behind TraMS when it comes to customer and revenue
                            management. It manages the whole dataset around customers and revenue and implements the
                            complete feature set for this domain. It responds to all of the requests for customer
                            and revenue data that are sent by any client. The server is stateless and implements a REST API.
                            This ensures a simple and easy-to-use API which clients can then implement. It is currently
                            built in Java using Spring Boot but can optionally also be deployed via Docker container(s).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="5">
                        <Accordion.Header>TraMS Operations Server</Accordion.Header>
                        <Accordion.Body>
                            The operations server is the brain behind TraMS when it comes to day-to-day
                            management of the transport company. It manages the whole dataset around routes,
                            vehicles, stops and timetables and implements the complete feature set for this domain.
                            It also uses <a href="https://www.davelee.de/personalman/">PersonalMan Server</a> to support employee management.
                            It responds to all of the requests for data that are sent by any client.
                            The server is stateless and implements a REST API.
                            This ensures a simple and easy-to-use API which clients can then implement. It is currently
                            built in Java using Spring Boot but can optionally also be deployed via Docker container(s).
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="6">
                        <Accordion.Header>Database (MongoDB)</Accordion.Header>
                        <Accordion.Body>
                            TraMS uses a database to save all of the data. This data can theoretically be stored in any
                            type of database with minimal additional effort. The only criteria is that each of the
                            three server services (Business, CRM and Operations) have their own schema so that they
                            do not know about the other data in the system). In the
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
