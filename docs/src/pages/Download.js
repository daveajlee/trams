import React from "react";
import {Button, Card, Col, Container, Row} from "react-bootstrap";

const Download = () => (
    <Container fluid>
        <Row>
            <Col>
                <h2 className="mt-5 text-center">Download</h2>
            </Col>
        </Row>
        <Row className="mt-3">
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center download-card">
                    <Card.Body>
                        <Card.Title>TraMS Business Server</Card.Title>
                        <Card.Text>
                            A running business server instance is required before any client of TraMS can be used since
                            the company functionality is essential! This
                            server can be run on any computer which supports Java 11 or above. It can be
                             manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams-business/packages/1512410">JAR</Button>
                        <Button variant="primary" className="ms-3" href="https://hub.docker.com/r/daveajlee/trams-business">Docker</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center download-card">
                    <Card.Body>
                        <Card.Title>TraMS CRM Server</Card.Title>
                        <Card.Text>
                            A running CRM server instance is required before any client which wishes to use
                            customer or revenue functionality for TraMS can be used. This
                            server can be run on any computer which supports Java 11 or above. It can be
                            manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams-crm/packages/1512449">JAR</Button>
                        <Button variant="primary" className="ms-3" href="https://hub.docker.com/r/daveajlee/trams-crm">Docker</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center download-card">
                    <Card.Body>
                        <Card.Title>TraMS Operations Server</Card.Title>
                        <Card.Text>
                            A running Operations server instance is required before any client which wishes to run
                            a simulation can be used. This
                            server can be run on any computer which supports Java 11 or above. It can be
                            manually started via the supplied JAR file or the supplied Docker image.
                        </Card.Text>
                        <Button variant="primary" href="https://github.com/daveajlee/trams-operations/packages/1512385">JAR</Button>
                        <Button variant="primary" className="ms-3" href="https://hub.docker.com/r/daveajlee/trams-operations">Docker</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center download-card">
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
