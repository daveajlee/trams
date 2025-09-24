import React from "react";
import {Card, Col, Container, Row} from "react-bootstrap";
import apiImage from "../douglas-lopes-ehyV_XOZ4iA-unsplash.jpg";

const Api = () => (
    <Container fluid>
        <Row>
            <Col>
                <Card>
                    <Card.Img variant="top" className="img-responsive w-25 rounded mx-auto d-block mt-3" src={apiImage} />
                    <Card.Title className="mt-5 text-center">API</Card.Title>
                    <Card.Body className="text-center mb-5">The current API of TraMS can be accessed
                        by running the TraMS server and then the following URL: <br/><br/><span style={{fontWeight: "bold"}}>http://server:port/swagger-ui/index.html</span> <br/><br/> If you would like to test the API or test a
                    client you have built for the API then you need to download TraMS Server from the Download
                    page.</Card.Body>
                </Card>
            </Col>
        </Row>
    </Container>
);

export default Api;
