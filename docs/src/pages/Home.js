import React from "react";
import logo from "../trams-logo.png";
import logoSmall from "../trams-logo-mobile.png"
import visionImage from "../joshua-earle-Dwheufds6kQ-unsplash.jpg";
import roadmapImage from "../jess-bailey-mexeVPlTB6k-unsplash.jpg";
import architectureImage from "../arpad-czapp-wS250FJl5Uw-unsplash.jpg";
import apiImage from "../douglas-lopes-ehyV_XOZ4iA-unsplash.jpg";
import {Button, Card, Col, Container, Row} from "react-bootstrap";
import {Link} from "react-router-dom";
import MediaQuery from "react-responsive";

const Home = () => (
    <Container fluid>
        <Row>
            <MediaQuery query="(max-device-width: 900px)">
                <Col className="text-center"><img src={logoSmall} alt="TraMS"
                                                  className="img-responsive"/></Col>
            </MediaQuery>
            <MediaQuery query="(min-device-width: 901px)">
                <Col className="text-center"><img src={logo} alt="TraMS"
                                                  className="img-responsive"/></Col>
            </MediaQuery>
        </Row>
        <Row>
            <Col className="text-center mt-5"><h1>TraMS - Transport Management Simulator</h1></Col>
        </Row>
        <Row>
            <Col className="text-left mt-3"><p>Bored of waiting in queues for public transport that always runs late?
                Do you think you could make public transport run on time? TraMS is your chance to prove it!
                It is a fun computer game where you run your own fictional transport company and have to ensure passengers
                arrive at their destination on-time! If your vehicles are delayed in traffic then you need to take action
                to get them back on schedule before your passengers get frustrated and leave for another transport operator.
                Can your transport operator stand the test of time or will your passengers take your company off the road/rails?
                This website provides information about the vision and roadmap of TraMS as well as the flexible API architecture
                which TraMS uses.</p></Col>
        </Row>
        <Row>
            <Col className="text-left mt-3"><p>If you simply want to try out TraMS without reading the complete documentation first
                (not recommended) then you can simply download the latest version: </p><p className="text-center mt-2"><Button as={Link} to="/download">Download</Button></p></Col>
        </Row>
        <hr/>
        <Row className="mt-3">
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center nav-card">
                    <Card.Img variant="top" src={visionImage} alt={"Person sitting on mountain and viewing clouds"}/>
                    <Card.Body>
                        <Card.Title>Vision</Card.Title>
                        <Card.Text>
                            Find out more about the vision and goals behind TraMS.
                        </Card.Text>
                        <Button variant="primary" as={Link} to="/vision">Vision</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center nav-card">
                    <Card.Img variant="top" src={roadmapImage} alt={"Photo of a diary and pen"}/>
                    <Card.Body>
                        <Card.Title>Features & Roadmap</Card.Title>
                        <Card.Text>
                            Current features and the next features that are planned.
                        </Card.Text>
                        <Button variant="primary" as={Link} to="/features">Features</Button>
                        <Button variant="primary" className="ms-3" as={Link} to="/roadmap">Roadmap</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center nav-card">
                    <Card.Img variant="top" src={architectureImage} alt={"Photo of a mobile phone and a big screen"}/>
                    <Card.Body>
                        <Card.Title>Architecture</Card.Title>
                        <Card.Text>
                            Documentation of the architecture design of TraMS.
                        </Card.Text>
                        <Button variant="primary" as={Link} to="/architecture">Architecture</Button>
                    </Card.Body>
                </Card>
            </Col>
            <Col xs={12} sm={12} md={6} lg={3}>
                <Card className="text-center nav-card">
                    <Card.Img variant="top" src={apiImage} alt={"Photo of a laptop with an API client visible on the main screen"}/>
                    <Card.Body>
                        <Card.Title>API</Card.Title>
                        <Card.Text>
                            Technical documentation for developers.
                        </Card.Text>
                        <Button variant="primary" as={Link} to="/api">API</Button>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    </Container>
);

export default Home;
