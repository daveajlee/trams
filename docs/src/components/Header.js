import logo from './../trams-header-logo.png';
import {Navbar} from "react-bootstrap";
import {Container} from "react-bootstrap";
import {Nav} from "react-bootstrap";
import { Link } from 'react-router-dom';

const Header = () => (

    <div className="Header">
    <header className="App-header">
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand as={Link} to="/"><img src={logo} alt="TraMS"
                                                className="img-responsive img-max-height"/></Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/">Home</Nav.Link>
                        <Nav.Link as={Link} to="/vision">Vision</Nav.Link>
                        <Nav.Link as={Link} to="/features">Features</Nav.Link>
                        <Nav.Link as={Link} to="/roadmap">Roadmap</Nav.Link>
                        <Nav.Link as={Link} to="/architecture">Architecture</Nav.Link>
                        <Nav.Link as={Link} to="/api">API</Nav.Link>
                        <Nav.Link as={Link} to="/download">Download</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    </header>
  </div>

);

export default Header;