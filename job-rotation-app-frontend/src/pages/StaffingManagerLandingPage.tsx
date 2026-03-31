import Container from "react-bootstrap/esm/Container";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Footer from "../components/FooterBar";
import CreateRoleForm from "../components/CreateRoleForm";
import { useState } from "react";
import StaffingManagerNavBar from "../components/StaffingManagerNavBar";


export const StaffingManagerLandingPage = () => {
    const [showModal, setShowModal] = useState(false);

    const handleClose = () => setShowModal(false);  
    const handleShow = () => setShowModal(true);

    const logout = () => {
    localStorage.removeItem("staffingManagerName");
    localStorage.removeItem("staffingManagerEmailAddress");

    window.location.href = "/login";
  };

    return (
        <>
            <StaffingManagerNavBar />
            <Button variant="secondary" onClick={logout}>
              Sign Out
            </Button>
            <Container style={{ marginTop: "10px", maxWidth: "600px", padding: "35px", textAlign: "center" }}>
                {/* <h2> Staffing Management</h2> */}
                <Button variant="primary" size="lg" onClick={handleShow} className="mt-4">
                    Create New Role
                </Button>
            </Container>

            <Modal show={showModal} onHide={handleClose} size="lg" centered scrollable>
                <Modal.Header closeButton>
                    <Modal.Title style={{ marginLeft: "auto" }}>Create Job Role</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreateRoleForm onClose={handleClose} />
                </Modal.Body>
            </Modal>

            <Footer />
        </>
    );
};

export default StaffingManagerLandingPage;