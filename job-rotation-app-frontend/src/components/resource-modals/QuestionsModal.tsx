import Modal from "react-bootstrap/Modal";

type QuestionsModalProps = {
    show: boolean;
    onClose: () => void;
};

const QuestionsModal = ({ show, onClose }: QuestionsModalProps) => {
    return (
        <Modal show={show} onHide={onClose} centered size="lg" aria-labelledby="example-modal-sizes-title-lg">
            <Modal.Header closeButton>
                <Modal.Title>Frequently Asked Questions</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p><strong>How do I find out about rotation opportunities?</strong></p>
                <p>Opportunites can be found through this platform 'Next Step'. Your practice's staffing manager can also provide guidance and support on available assignments. Speak to your informal network and colleagues to be aware of potential opportunities.</p>
                <p><strong>What if I have questions about the rotation process?</strong></p>
                <p>Speak to your People Manager, Staffing Manager, Tactical Workforce Planner or Assignment Manager who can provide guidance and support on the rotation process.</p>
                <p><strong>How do I update my GTD profile?</strong></p>
                <p>GTD profiles can be updated through the internal GTD platform. Speak to your TWP or GTD champion for support and guidance on updating your profile.</p>
                <p><strong>Which staffing manager should I contact?</strong></p>
                <p>You should contact your practice's staffing manager for guidance and support on available assignments and the rotation process.</p>
                <p><strong>How do I know if a rotation opportunity is right for me?</strong></p>
                <p>Consider your career goals, skills, and interests when evaluating rotation opportunities. Discuss potential opportunities with your People Manager, Staffing Manager, or Tactical Workforce Planner to ensure alignment with your development plan.</p>
                <p><strong>Can I rotate to a different practice or business area?</strong></p>
                <p>Yes, you can rotate to a different practice or business area. Speak to your Staffing Manager about potential opportunities across the organisation and ensure your GTD profile is up to date to reflect your skills and interests.</p>
                <p><strong>What is the bench and what happens whilst I am on bench after an assignment ends?</strong></p>
                <p>The bench refers to a period when you are not assigned to a specific project or role. During this time, you will recieve support from bench managers and staffing managers to support you in your professional development. Time on bench can be used for training, networking, and getting involved in community initiatives.</p>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    );
};

export default QuestionsModal;