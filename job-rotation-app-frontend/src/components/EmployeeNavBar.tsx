import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Offcanvas from 'react-bootstrap/Offcanvas';
import logo from '../assets/logo1.png';
import { useState } from "react";
import RotationProcessModal from "./resource-modals/RotationProcessModal";
import ExpressInterestModal from './resource-modals/ExpressInterestModal';
import GtdSupportModal from './resource-modals/GtdSupportModal';
import PeopleSupportModal from './resource-modals/PeopleSupportModal';
import QuestionsModal from './resource-modals/QuestionsModal';
import ContactListModal from './resource-modals/ContactListModal';

function EmployeeNavBar() {
  const [showModal, setShowModal] = useState(false);
  const [showExpressInterestModal, setShowExpressInterestModal] = useState(false);
  const [showGtdSupportModal, setShowGtdSupportModal] = useState(false);
  const [showPeopleSupportModal, setShowPeopleSupportModal] = useState(false);
  const [showQuestionsModal, setShowQuestionsModal] = useState(false);
  const [showContactListModal, setShowContactListModal] = useState(false);

  const handleShowModal = () => setShowModal(true);
  const handleShowExpressInterestModal = () => setShowExpressInterestModal(true);
  const handleShowGtdSupportModal = () => setShowGtdSupportModal(true);
  const handleShowPeopleSupportModal = () => setShowPeopleSupportModal(true);
  const handleShowQuestionsModal = () => setShowQuestionsModal(true);
  const handleShowContactListModal = () => setShowContactListModal(true);

  const handleCloseModal = () => setShowModal(false);
  const handleCloseExpressInterestModal = () => setShowExpressInterestModal(false);
  const handleCloseGtdSupportModal = () => setShowGtdSupportModal(false);
  const handleClosePeopleSupportModal = () => setShowPeopleSupportModal(false);
  const handleCloseQuestionsModal = () => setShowQuestionsModal(false);
  const handleCloseContactListModal = () => setShowContactListModal(false);

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
            <Navbar.Brand style={{ fontSize: '3.0rem', color: 'white', textAlign: 'center', marginRight: '40px', fontFamily: 'Bebas Neue'}} href="/">
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
                  <Nav.Link onClick={handleShowQuestionsModal}>FAQs</Nav.Link>
                  <NavDropdown
                    title="Resources"
                    id={`offcanvasNavbarDropdown-expand-${expand}`}
                  >
                    <NavDropdown.Item onClick={handleShowModal}>Rotation Process </NavDropdown.Item>
                    <NavDropdown.Item onClick={handleShowExpressInterestModal}> Express Interest in a Role </NavDropdown.Item>
                    <NavDropdown.Item onClick={handleShowGtdSupportModal}> GTD Support </NavDropdown.Item>
                    <NavDropdown.Item onClick={handleShowPeopleSupportModal}> People Support </NavDropdown.Item>
                    <NavDropdown.Item onClick={handleShowQuestionsModal}> FAQs </NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item onClick={handleShowContactListModal}>
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

      <RotationProcessModal show={showModal} onClose={handleCloseModal} />
      <ExpressInterestModal show={showExpressInterestModal} onClose={handleCloseExpressInterestModal} />
      <GtdSupportModal show={showGtdSupportModal} onClose={handleCloseGtdSupportModal} />
      <PeopleSupportModal show={showPeopleSupportModal} onClose={handleClosePeopleSupportModal} />
      <QuestionsModal show={showQuestionsModal} onClose={handleCloseQuestionsModal} />
      <ContactListModal show={showContactListModal} onClose={handleCloseContactListModal} />
    </>
  );
}

export default EmployeeNavBar;