import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import careerGrow from '../assets/career-grow.png';

type RotationProcessModalProps = {
    show: boolean;
    onClose: () => void;
};

const RotationProcessModal = ({ show, onClose }: RotationProcessModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Rotation Process</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>A rotation process involves an employee moving through different roles or departments within the organisation for a set period of time. </p>
                <p>A new job rotation allows the individual to gain new skills, understand different areas of the business, and to build up their network. This process aids in supporting career development, business needs and organisational growth.</p>
                <img src={careerGrow} alt="Career Growth" style={{ width: "100%", marginTop: "20px" }} />
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default RotationProcessModal;