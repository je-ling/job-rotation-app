import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Offcanvas from 'react-bootstrap/Offcanvas';
import logo from '../assets/logo1.png';


function EmployeeNavBar() {
  return (
    <>
      {[false].map((expand) => (
        <Navbar
          key={String(expand)}
          expand={expand}
          className="mb-3"
          style={{ height: '90px', backgroundColor: '#0058AB' }}
        >
          <Container>
            <img
              src={logo}
              width="180"
              height="45"
              // width="150"
              // height="80"
              // width="90"
              // height="82"
              alt="Company Logo"
            />
            <Navbar.Brand style={{ fontSize: '2.5rem', marginRight: '70px', color: 'white' }} href="#">NEXT STEP</Navbar.Brand>
            <Navbar.Toggle
              aria-controls={`offcanvasNavbar-expand-${expand}`}
              style={{ marginRight: '20px', color: 'white', borderColor: 'white' }}
            />
            <Navbar.Offcanvas
              id={`offcanvasNavbar-expand-${expand}`}
              aria-labelledby={`offcanvasNavbar-Title-${expand}`}
              placement="end"
            >
              <Offcanvas.Header closeButton>
                <Offcanvas.Title style={{ flex: 1, textAlign: 'center' }} id={`offcanvasNavbar-Title-${expand}`}>
                  NEXT STEP
                </Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                <Nav className="justify-content-end flex-grow-1 pe-3">
                  <Nav.Link href="/">Home</Nav.Link>
                  <Nav.Link href="#action2">FAQs</Nav.Link>
                  <NavDropdown
                    title="Resources"
                    id={`offcanvasNavbarDropdown-expand-${expand}`}
                  >
                    <NavDropdown.Item href="#rotation-process">Rotation Process</NavDropdown.Item>
                    <NavDropdown.Item href="#express-interest"> Express Interest in a Role </NavDropdown.Item>
                    <NavDropdown.Item href="#gtd-support"> GTD Support </NavDropdown.Item>
                    <NavDropdown.Item href="#people-support"> People Support </NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item href="#staffing-managers-contacts">
                      Staffing Managers Contact List
                    </NavDropdown.Item>
                  </NavDropdown>
                  <Nav.Link href="/login">Staffing Manager Login</Nav.Link>
                </Nav>
                {/* <Form className="d-flex">
                  <Form.Control
                    type="search"
                    placeholder="Search"
                    className="me-2"
                    aria-label="Search"
                  />
                  <Button variant="outline-success">Search</Button>
                </Form> */}
              </Offcanvas.Body>
            </Navbar.Offcanvas>
          </Container>
        </Navbar>
      ))}
    </>
  );
}

export default EmployeeNavBar;