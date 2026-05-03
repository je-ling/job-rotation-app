import Modal from "react-bootstrap/Modal";
import cv from '../../assets/cv.jpeg';

type GtdSupportModalProps = {
    show: boolean;
    onClose: () => void;
};

const GtdSupportModal = ({ show, onClose }: GtdSupportModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>GTD Support</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p><strong>What is GTD (Global Talent Deployment)?</strong></p>
                <p>A GTD profile is an internal one-pager CV that highlights your skills, interests, certifications, and details of your previous rotation assignments.</p>
                <p>This is essential to showcase your qualifications and interests to potential opportunities and assignment areas within the organisation.</p>
                <img src={cv} alt="CV" style={{ width: "80%", marginTop: "10px", marginLeft: "10%", marginRight: "10%" }} />
                <p>Ensure your GTD profile is up to date to reflect your current skills, interests, certifications and details of your previous rotation assignments.</p>
                <p>For each practice, GTD champions and tactical workforce planners are available to support you in maintaining and updating your profile, ensuring it best advertises you!</p>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default GtdSupportModal;