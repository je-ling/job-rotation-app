import { useState, useEffect } from "react";
import type { FormEvent } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";

type Role = {
    roleId: number;
    roleName: string;
    gradeRequired: string;
    department: string;
    location: string;
    staffingManagerEmailAddress: string;
    duration: string;
    jobDescription: string;
    startDate: string;
    securityClearanceRequired: string;
};

type EnumValues = {
    grades: string[];
    departments: string[];
    durations: string[];
};

type UpdateRoleFormProps = {
    roleId: number; 
    onClose: () => void;
};

const UpdateRoleForm = ({ roleId, onClose }: UpdateRoleFormProps) => {
    const [successMessage, setSuccessMessage] = useState("");

    const [role, setRole] = useState<Role>({
        roleId: roleId,
        roleName: "",
        gradeRequired: "",
        department: "",
        location: "",
        staffingManagerEmailAddress: "",
        duration: "",
        jobDescription: "",
        startDate: "",
        securityClearanceRequired: "",
    });

    useEffect(() => {
        const fetchRoleDetails = async () => {
            try {
                const response = await fetch(`/staffing-manager/available-roles/${roleId}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch role details. Please try again.");
                }
                const data = await response.json();
                setRole(data);
            } catch (err) {
                console.error(err instanceof Error ? err.message : "An error occurred");
            }
        };

        fetchRoleDetails();
    }, [roleId]); 

    const [enums, setEnums] = useState<EnumValues>({
        grades: [],
        departments: [],
        durations: [],
    });

    const [loading, setLoading] = useState(true);
    const [roleIdInput, setRoleIdInput] = useState("");
    const [fetchingRole, setFetchingRole] = useState(false);

    useEffect(() => {
        const fetchEnums = async () => {
            try {
                const response = await fetch("/staffing-manager/enums");
                if (!response.ok) {
                    throw new Error("Failed to fetch values required to create a role. Please try again.");
                }
                const data = await response.json();
                setEnums(data);
            } catch (err) {
                console.error(err instanceof Error ? err.message : "An error occurred");
            } finally {
                setLoading(false);
            }
        };

        fetchEnums();
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setRole((prevRole) => ({
            ...prevRole,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        console.log("Submitting role:", role);
        console.log("Role state before submission:", role);
        try {
            const response = await fetch(`/staffing-manager/update-role/${role.roleId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(role),
            });

            if (response.ok) {
                console.log("Role updated successfully");
                setSuccessMessage("Role updated successfully.");
                setTimeout(() => {
                    onClose();
                }, 2000);
            } else {
                console.error("Failed to update role");
            }
        } catch (error) {
            console.error("An error occurred while updating the role:", error);
        }
    };

    const fetchRoleDetails = async () => {
        if (!roleIdInput) return;
        setFetchingRole(true);
        try {
            const response = await fetch(`/staffing-manager/available-roles/${roleIdInput}`);
            if (!response.ok) {
                throw new Error("Failed to fetch role details. Please provide a valid Role ID.");
            }
            const data = await response.json();
            setRole(data);
        } catch (err) {
            console.error(err instanceof Error ? err.message : "An error occurred");
        } finally {
            setFetchingRole(false);
        }
    };

    return (
        <>
            {loading ? (
                <p>Loading form...</p>
            ) : (
                <form onSubmit={handleSubmit}>
                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="roleIdInput" className="form-label">
                                Role ID
                            </label>
                            <input
                                className="form-control"
                                id="roleIdInput"
                                name="roleIdInput"
                                placeholder="Enter Role ID"
                                value={roleIdInput}
                                onChange={(e) => setRoleIdInput(e.target.value)}
                            />
                        </Col>
                        <Col md={6} className="d-flex align-items-end">
                            <Button
                                variant="info"
                                onClick={fetchRoleDetails}
                            >
                                {fetchingRole ? "Fetching..." : "Get Role Details"}
                            </Button>
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="roleName" className="form-label">
                                Role Name
                            </label>
                            <input
                                className="form-control"
                                id="roleName"
                                name="roleName"
                                placeholder="Role Name"
                                value={role.roleName}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                        <Col md={6}>
                            <label htmlFor="location" className="form-label">
                                Location
                            </label>
                            <input
                                className="form-control"
                                id="location"
                                name="location"
                                placeholder="Location"
                                value={role.location}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="gradeRequired" className="form-label">
                                Grade Required
                            </label>
                            <select
                                className="form-control"
                                id="gradeRequired"
                                name="gradeRequired"
                                value={role.gradeRequired}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Grade</option>
                                {enums.grades.map((grade) => (
                                    <option key={grade} value={grade}>
                                        {grade}
                                    </option>
                                ))}
                            </select>
                        </Col>
                        <Col md={6}>
                            <label htmlFor="department" className="form-label">
                                Department
                            </label>
                            <select
                                className="form-control"
                                id="department"
                                name="department"
                                value={role.department}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Department</option>
                                {enums.departments.map((dept) => (
                                    <option key={dept} value={dept}>
                                        {dept}
                                    </option>
                                ))}
                            </select>
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="staffingManagerEmailAddress" className="form-label">
                                Staffing Manager Email Address
                            </label>
                            <input
                                className="form-control"
                                id="staffingManagerEmailAddress"
                                name="staffingManagerEmailAddress"
                                type="email"
                                placeholder="Staffing Manager Email Address"
                                value={role.staffingManagerEmailAddress}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                        <Col md={6}>
                            <label htmlFor="startDate" className="form-label">
                                Start Date
                            </label>
                            <input
                                className="form-control"
                                id="startDate"
                                name="startDate"
                                type="date"
                                placeholder="Start Date"
                                value={role.startDate}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="duration" className="form-label">
                                Duration
                            </label>
                            <select
                                className="form-control"
                                id="duration"
                                name="duration"
                                value={role.duration}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Duration</option>
                                {enums.durations.map((dur) => (
                                    <option key={dur} value={dur}>
                                        {dur}
                                    </option>
                                ))}
                            </select>
                        </Col>
                        <Col md={6}>
                            <label htmlFor="securityClearanceRequired" className="form-label">
                                Security Clearance Eligible / Required
                            </label>
                            <select
                                className="form-control"
                                id="securityClearanceRequired"
                                name="securityClearanceRequired"
                                value={role.securityClearanceRequired}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Option</option>
                                <option value="YES">YES</option>
                                <option value="NO">NO</option>
                            </select>
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={12}>
                            <label htmlFor="jobDescription" className="form-label">
                                Job Description
                            </label>
                            <textarea
                                className="form-control"
                                id="jobDescription"
                                name="jobDescription"
                                placeholder="Job Description"
                                value={role.jobDescription}
                                onChange={handleChange}
                                required
                                style={{ minHeight: "250px" }}
                            />
                        </Col>
                    </Row>

                    {successMessage && (
                        <div className="alert alert-success" role="alert">
                            {successMessage}
                        </div>
                    )}

                    <div className="d-flex gap-2">
                        <Button variant="secondary" onClick={onClose}>
                            Cancel
                        </Button>
                        <Button variant="primary" type="submit" className="w-100">
                            Update Role
                        </Button>
                    </div>
                </form>
            )}
        </>
    );
};

export default UpdateRoleForm;
