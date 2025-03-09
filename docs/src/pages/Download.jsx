import React from "react";
import {Button, Card, CardGroup, Col, Container, Row} from "react-bootstrap";

const Download = () => (
    <Container fluid>
        <Row>
            <Col>
                <h2 className="mt-5 text-center">Download</h2>
            </Col>
        </Row>
        <Row className="mt-3">
            <CardGroup>
                <Card className="text-center download-card">
                    <Card.Body>
                        <Card.Title>Server</Card.Title>
                        <Card.Text>
                            A running server instance is required before any client of TraMS can be used. It ensures company,
                            customer, revenue and simulation functionality is available for any client. This
                            server can be run on any computer which supports Java 11 or above. It can be
                            manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams/packages/1836444">JAR</Button>
                        <Button variant="primary" className="ms-3" href="https://hub.docker.com/r/daveajlee/trams-server">Docker</Button>
                    </Card.Body>
                </Card>
                <Card className="text-center download-card">
                    <Card.Body>
                        <Card.Title>Desktop Client</Card.Title>
                        <Card.Text>
                            The desktop client for TraMS is implemented via Angular and Electron. It supports
                            an offline mode which does not require the Server APIs. An online mode using the Server APIs
                            is planned.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams/releases">Download Client</Button>
                    </Card.Body>
                </Card>
            </CardGroup>
        </Row>
    </Container>
);

export default Download;
