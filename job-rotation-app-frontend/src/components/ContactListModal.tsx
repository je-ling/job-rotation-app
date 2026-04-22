import Modal from "react-bootstrap/Modal";

type ContactListModalProps = {
    show: boolean;
    onClose: () => void;
};

const ContactListModal = ({ show, onClose }: ContactListModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Staffing Manager Contact List</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p><strong>Platform Engineering:</strong> bob.smith@example.com</p>
                <p><strong>Agile Delivery:</strong> sarah.jane@example.com</p>
                <p><strong>Engagement Management:</strong> jane.doe@example.com</p>
                <p><strong>Insights & Data:</strong> emily.clark@example.com and john.doe@example.com</p>
                <p><strong>Architecture:</strong> charlie.brown@example.com</p>
                <p><strong>Development:</strong> fred.jones@example.com</p>
                <p><strong>Digital Experience:</strong> will.smith@example.com</p>
                <p><strong>Business Operations:</strong> debbie.white@example.com</p>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default ContactListModal;