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
          style={{ height: '90px', backgroundColor: '#0058AB', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
        >
          <Container>
            <img
              src={logo}
              width="180"
              height="45"
              alt="Company Logo"
            />
            <Navbar.Brand style={{ fontSize: '2.5rem', color: 'white', textAlign: 'center', marginRight: '40px'}} href="/">
              NEXT STEP
            </Navbar.Brand>
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
                    {/* TO DO: use modal pages for pop up information pages here! */}
                    <NavDropdown.Item href="#rotation-process">Rotation Process</NavDropdown.Item>
                    <NavDropdown.Item href="#express-interest"> Express Interest in a Role </NavDropdown.Item>
                    <NavDropdown.Item href="#gtd-support"> GTD Support </NavDropdown.Item>
                    <NavDropdown.Item href="#people-support"> People Support </NavDropdown.Item>
                    <NavDropdown.Divider />
                    {/* contact list - also have a copy button for contact email address */}
                    <NavDropdown.Item href="#staffing-managers-contacts">
                      Staffing Managers Contact List
                    </NavDropdown.Item>
                  </NavDropdown>
                  <Nav.Link href="/login">Staffing Manager Login</Nav.Link>
                </Nav>
              </Offcanvas.Body>
            </Navbar.Offcanvas>
          </Container>
        </Navbar>
      ))}
    </>
  );
}

export default EmployeeNavBar;