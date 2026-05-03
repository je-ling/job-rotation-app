import Modal from "react-bootstrap/Modal";
import support from '../../assets/support.jpeg';

type PeopleSupportModalProps = {
    show: boolean;
    onClose: () => void;
};

const PeopleSupportModal = ({ show, onClose }: PeopleSupportModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>People Support</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p><strong>Who can support you during the rotation process?</strong></p>
                <ul>
                    <li>
                        <strong>People Manager:</strong> Your PM will help to identify development opportunities, discuss your career aspirations, and how they align to business priorities.
                    </li>
                    <li>
                        <strong>Staffing Manager:</strong> Your SM will work with you to understand your career aspirations and support you in finding suitable rotation opportunities within your practice.
                    </li>
                    <li>
                        <strong>Tactical Workforce Planner:</strong> Your TWP can guide you in updating your Talent profile and give guidance on training and certifications.
                    </li>
                    <li>
                        <strong>Assignment Manager:</strong> Your AM may be able to support your rotation/career aspirations within your current assignment or business area.
                    </li>
                    <li>
                        <strong>Head of Profession:</strong> Can advise on roles, technologies and certifications that support your career goals and development.
                    </li>
                    <li>
                        <strong>L&D:</strong> Can provide learning and development guidance and support.
                    </li>
                </ul>
                <img src={support} alt="People Support" style={{ width: "85%", marginTop: "20px", marginLeft: "10%", marginRight: "10%" }} />
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default PeopleSupportModal;