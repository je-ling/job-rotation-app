import Container from "react-bootstrap/esm/Container";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Footer from "../components/FooterBar";
import CreateRoleForm from "../components/CreateRoleForm";
import { useState } from "react";
import StaffingManagerNavBar from "../components/StaffingManagerNavBar";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import UpdateRoleForm from "../components/UpdateRoleForm";
import DeleteRoleForm from "../components/DeleteRoleForm";


export const StaffingManagerLandingPage = () => {
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);


    const handleCloseCreate = () => setShowCreateModal(false);
    const handleShowCreate = () => setShowCreateModal(true);

    const handleCloseUpdate = () => setShowUpdateModal(false);
    const handleShowUpdate = () => setShowUpdateModal(true);

    const handleCloseDelete = () => setShowDeleteModal(false);
    const handleShowDelete = () => setShowDeleteModal(true);

    return (
        <>
            <StaffingManagerNavBar />
            <Container style={{ marginTop: "10px", maxWidth: "700px", padding: "35px", textAlign: "center" }}>
                <Row>
                    <Col>
                        <Button variant="primary" size="lg" onClick={handleShowCreate} className="mt-4">
                            Create New Role
                        </Button>
                    </Col>
                    <Col>
                        <Button variant="success" size="lg" onClick={handleShowUpdate} className="mt-4">
                            Update Roles
                        </Button>
                    </Col>
                    <Col>
                        <Button variant="danger" size="lg" onClick={handleShowDelete} className="mt-4">
                            Delete Roles
                        </Button>
                    </Col>
                </Row>
            </Container>

            <Modal show={showCreateModal} onHide={handleCloseCreate} size="lg" centered scrollable>
                <Modal.Header closeButton>
                    <Modal.Title style={{ marginLeft: "auto" }}>Create Job Role</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <CreateRoleForm onClose={handleCloseCreate} />
                </Modal.Body>
            </Modal>

            <Modal show={showUpdateModal} onHide={handleCloseUpdate} size="lg" centered scrollable>
                <Modal.Header closeButton>
                    <Modal.Title style={{ marginLeft: "auto" }}>Update Job Role</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <UpdateRoleForm onClose={handleCloseUpdate} />
                </Modal.Body>
            </Modal>

            <Modal show={showDeleteModal} onHide={handleCloseDelete} size="lg" centered scrollable>
                <Modal.Header closeButton>
                    <Modal.Title style={{ marginLeft: "auto" }}>Delete Job Role</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <DeleteRoleForm onClose={handleCloseDelete} />
                </Modal.Body>
            </Modal>
            <Footer />
        </>
    );
};

export default StaffingManagerLandingPage;