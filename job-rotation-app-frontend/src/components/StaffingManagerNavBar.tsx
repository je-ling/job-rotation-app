import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';

function StaffingManagerNavBar() {
  return (
    <Navbar className="bg-body-tertiary">
      <Container>
         <Navbar.Brand style={{ flex: 1, textAlign: 'center', fontSize: '2.2rem' }} href="#">Next Step</Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse className="justify-content-end">
          <Navbar.Text>
            Signed in as: <a href="#login"></a>
          </Navbar.Text>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default StaffingManagerNavBar;