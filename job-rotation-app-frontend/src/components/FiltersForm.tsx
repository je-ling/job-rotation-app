import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Form from "react-bootstrap/Form";

type EnumValues = {
    grades: string[];
    departments: string[];
    durations: string[];
    client: string;
};

type FiltersFormProps = {
    onApply: (filters: { grade: string; department: string; duration: string; client: string }) => void;
    onClose: () => void;
};

const FiltersForm = ({ onApply, onClose }: FiltersFormProps) => {
    const [filters, setFilters] = useState({
        grade: "",
        department: "",
        duration: "",
        client: "",
    });

    const [enums, setEnums] = useState<EnumValues>({
        grades: [],
        departments: [],
        durations: [],
        client: "",
    });

    useEffect(() => {
        const fetchEnums = async () => {
            try {
                const response = await fetch("/employee/enums");
                if (!response.ok) {
                    throw new Error("Failed to fetch filter values. Please try again.");
                }
                const data = await response.json();
                setEnums(data);
            } catch (err) {
                console.error(err instanceof Error ? err.message : "An error occurred");
            }
        };

        fetchEnums();
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFilters((prevFilters) => ({
            ...prevFilters,
            [name]: value,
        }));
    };

    const handleApply = () => {
        onApply(filters);
        setFilters({ grade: "", department: "", duration: "", client: "" }); // Reset filters after applying
    };

    return (
        <form>
            <Row className="mb-3">
                <Col md={3}>
                    <Form.Group controlId="filterGrade">
                        <Form.Label>Grade</Form.Label>
                        <Form.Select
                            name="grade"
                            value={filters.grade}
                            onChange={handleChange}
                        >
                            <option value="">Select Grade</option>
                            {enums.grades.map((grade) => (
                                <option key={grade} value={grade}>
                                    {grade}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                </Col>
                <Col md={3}>
                    <Form.Group controlId="filterDepartment">
                        <Form.Label>Department</Form.Label>
                        <Form.Select
                            name="department"
                            value={filters.department}
                            onChange={handleChange}
                        >
                            <option value="">Select Department</option>
                            {enums.departments.map((dept) => (
                                <option key={dept} value={dept}>
                                    {dept}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                </Col>
                <Col md={3}>
                    <Form.Group controlId="filterDuration">
                        <Form.Label>Duration</Form.Label>
                        <Form.Select
                            name="duration"
                            value={filters.duration}
                            onChange={handleChange}
                        >
                            <option value="">Select Duration</option>
                            {enums.durations.map((dur) => (
                                <option key={dur} value={dur}>
                                    {dur}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                </Col>
                <Col md={3}>
                    <Form.Group controlId="filterClient">
                        <Form.Label>Client</Form.Label>
                        <Form.Control
                            type="text"
                            name="client"
                            value={filters.client}
                            onChange={handleChange}
                            placeholder="Client Name"
                        />
                    </Form.Group>
                </Col>
            </Row>
            <div className="d-flex gap-2">
                <Button variant="secondary" onClick={onClose}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={handleApply}>
                    Apply Filters
                </Button>
            </div>
        </form>
    );
};

export default FiltersForm;