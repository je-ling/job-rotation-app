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
    client: string;
    jobDescription: string;
    startDate: string;
    securityClearanceRequired: string;
};

type EnumValues = {
    grades: string[];
    departments: string[];
    durations: string[];
};

const UpdateRoleForm = ({ role, onRoleUpdate }: { role: Role | null; onRoleUpdate: (updatedRole: Role) => void }) => {
    const [successMessage, setSuccessMessage] = useState("");

    const [formRole, setFormRole] = useState<Role>(
        role || {
            roleId: 0,
            roleName: "",
            gradeRequired: "",
            department: "",
            location: "",
            staffingManagerEmailAddress: localStorage.getItem("staffingManagerEmailAddress") || "",
            duration: "",
            client: "",
            jobDescription: "",
            startDate: "",
            securityClearanceRequired: "",
        }
    );

    useEffect(() => {
        if (role) {
            setFormRole(role);
        }
    }, [role]);

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
        setFormRole((prevRole) => ({
            ...prevRole,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        console.log("Submitting role:", formRole);
        console.log("Role state before submission:", formRole);
        try {
            const response = await fetch(`/staffing-manager/update-role/${formRole.roleId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formRole),
            });

            if (response.ok) {
                const updatedRole = await response.json();
                console.log("Role updated successfully", updatedRole);
                setSuccessMessage("Role updated successfully!");
                setTimeout(() => {
                    setSuccessMessage("");
                }, 2000);

                // clear form fields after successful submission
                setFormRole({
                    roleId: 0,
                    roleName: "",
                    gradeRequired: "",
                    department: "",
                    location: "",
                    staffingManagerEmailAddress: localStorage.getItem("staffingManagerEmailAddress") || "",
                    duration: "",
                    client: "",
                    jobDescription: "",
                    startDate: "",
                    securityClearanceRequired: "",
                });
                setRoleIdInput("");
                onRoleUpdate(updatedRole);
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
        setErrorMessage("");
        try {
            const response = await fetch(`/staffing-manager/available-roles/${roleIdInput}`);
            if (!response.ok) {
                setErrorMessage("Please provide a valid Role ID.");
                setTimeout(() => {
                    setErrorMessage("");
                }, 2000);
            }
            const data = await response.json();
            setFormRole(data);
        } catch (err) {
            setErrorMessage(err instanceof Error ? err.message : "An error occurred");
        } finally {
            setFetchingRole(false);
        }
    };

    const [errorMessage, setErrorMessage] = useState("");

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
                                variant="outline-info"
                                onClick={fetchRoleDetails}
                            >
                                {fetchingRole ? "Fetching..." : "Get Role Details"}
                            </Button>
                        </Col>
                    </Row>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="roleName" className="form-label">
                                Role Name <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                className="form-control"
                                id="roleName"
                                name="roleName"
                                placeholder="Role Name"
                                value={formRole.roleName}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                        <Col md={6}>
                            <label htmlFor="location" className="form-label">
                                Location <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                className="form-control"
                                id="location"
                                name="location"
                                placeholder="Location"
                                value={formRole.location}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={6}>
                            <label htmlFor="gradeRequired" className="form-label">
                                Grade Required <span style={{ color: 'red' }}>*</span>
                            </label>
                            <select
                                className="form-control"
                                id="gradeRequired"
                                name="gradeRequired"
                                value={formRole.gradeRequired}
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
                                Department <span style={{ color: 'red' }}>*</span>
                            </label>
                            <select
                                className="form-control"
                                id="department"
                                name="department"
                                value={formRole.department}
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
                                Staffing Manager Email Address <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                className="form-control"
                                id="staffingManagerEmailAddress"
                                name="staffingManagerEmailAddress"
                                type="email"
                                placeholder="Staffing Manager Email Address"
                                value={formRole.staffingManagerEmailAddress}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                        <Col md={6}>
                            <label htmlFor="startDate" className="form-label">
                                Start Date <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                className="form-control"
                                id="startDate"
                                name="startDate"
                                type="date"
                                placeholder="Start Date"
                                value={formRole.startDate}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={4}>
                            <label htmlFor="duration" className="form-label">
                                Duration <span style={{ color: 'red' }}>*</span>
                            </label>
                            <select
                                className="form-control"
                                id="duration"
                                name="duration"
                                value={formRole.duration}
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
                        <Col md={4}>
                            <label htmlFor="securityClearanceRequired" className="form-label">
                                SC Eligible / Required <span style={{ color: 'red' }}>*</span>
                            </label>
                            <select
                                className="form-control"
                                id="securityClearanceRequired"
                                name="securityClearanceRequired"
                                value={formRole.securityClearanceRequired}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Option</option>
                                <option value="YES">Yes</option>
                                <option value="NO">No</option>
                            </select>
                        </Col>
                        <Col md={4}>
                            <label htmlFor="client" className="form-label">
                                Client <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                className="form-control"
                                id="client"
                                name="client"
                                placeholder="Client"
                                value={formRole.client}
                                onChange={handleChange}
                                required
                            />
                        </Col>
                    </Row>

                    <Row className="mb-3">
                        <Col md={12}>
                            <label htmlFor="jobDescription" className="form-label">
                                Job Description <span style={{ color: 'red' }}>*</span>
                            </label>
                            <textarea
                                className="form-control"
                                id="jobDescription"
                                name="jobDescription"
                                placeholder="Job Description"
                                value={formRole.jobDescription}
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
