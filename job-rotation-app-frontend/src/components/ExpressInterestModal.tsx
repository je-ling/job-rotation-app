import Modal from "react-bootstrap/Modal";
import opportunity from '../assets/opportunity.jpeg';

type ExpressInterestModalProps = {
    show: boolean;
    onClose: () => void;
};

const ExpressInterestModal = ({ show, onClose }: ExpressInterestModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Express Interest in a Role</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p><strong>Contact your practice's staffing manager</strong></p>
                <p>They will provide guidance and support on what assignments are currently available. They will be able to provide you with more information about roles and guide you through the rotation process.</p>
                <p>Once you have secured a new assignement, you will be assigned an end date for your current assignment to then be able to rotate to the new role or opportunity.</p>
                <img src={opportunity} alt="Opportunity" style={{ width: "80%", marginTop: "10px", marginLeft: "10%", marginRight: "10%" }} />
                 <p>Ensure your GTD profile is up to date to reflect your current skills, interests, certifications and details of your previous rotation assignments.</p>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default ExpressInterestModal;