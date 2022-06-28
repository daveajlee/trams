import React from "react";
import {Button, Card, Col, Container, Row} from "react-bootstrap";

const Download = () => (
    <Container fluid>
        <Row>
            <Col>
                <h2 className="mt-5 text-center">Download</h2>
            </Col>
        </Row>
        <Row className="mt-5">
            <Col>
                <Card style={{ width: '18rem' }}>
                    <Card.Body>
                        <Card.Title>TraMS Business Server</Card.Title>
                        <Card.Text>
                            A running business server instance is required before any client of TraMS can be used since
                            the company functionality is essential! This
                            server can be run on any computer which supports Java 11 or above. It can be
                             manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="secondary">Coming Soon!</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col>
                <Card style={{ width: '18rem' }}>
                    <Card.Body>
                        <Card.Title>TraMS CRM Server</Card.Title>
                        <Card.Text>
                            A running CRM server instance is required before any client which wishes to use
                            customer or revenue functionality for TraMS can be used. This
                            server can be run on any computer which supports Java 11 or above. It can be
                            manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="secondary">Coming Soon!</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col>
                <Card style={{ width: '18rem' }}>
                    <Card.Body>
                        <Card.Title>TraMS Operations Server</Card.Title>
                        <Card.Text>
                            A running Operations server instance is required before any client which wishes to run
                            a simulation can be used. This
                            server can be run on any computer which supports Java 11 or above. It can be
                            manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="secondary">Coming Soon!</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col>
                <Card style={{ width: '18rem' }}>
                    <Card.Body>
                        <Card.Title>Desktop Client</Card.Title>
                        <Card.Text>
                            There is currently only a desktop client for TraMS which requires Java 8 or above. It has an integrated server so none of the
                            server services are required. The feature set of this client is therefore limited! A new
                            client which uses the Server APIs is planned.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams-game/releases/download/v0.4.0/trams-0.4.0.zip">ZIP file</Button>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    </Container>
);

export default Download;
