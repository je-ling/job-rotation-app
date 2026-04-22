import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import Table from 'react-bootstrap/Table';
import { JobRoleInformationForm } from './JobRoleInformationForm';
import FiltersForm from './FiltersForm';
import UpdateRoleForm from './UpdateRoleForm';

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

const searchFilter = (roles: Role[], query: string): Role[] => {
    return roles.filter((role) => {
        const roleIdMatch = role.roleId.toString().includes(query);
        const roleNameMatch = role.roleName.toLowerCase().includes(query.toLowerCase());
        const departmentMatch = role.department.toLowerCase().includes(query.toLowerCase());
        const locationMatch = role.location.toLowerCase().includes(query.toLowerCase());
        const clientMatch = role.client.toLowerCase().includes(query.toLowerCase());
        return roleIdMatch || roleNameMatch || departmentMatch || locationMatch || clientMatch;
    });
}

const StaffingManagerListRolesTable: React.FC = () => {
    const [successMessage, setSuccessMessage] = useState<string>("");

    const [roles, setRoles] = useState<Role[]>([]);
    const [allRoles, setAllRoles] = useState<Role[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [searchQuery, setSearchQuery] = useState('');

    const [showJobModal, setShowJobModal] = useState(false);
    const handleCloseJob = () => setShowJobModal(false);
    const handleShowJob = () => setShowJobModal(true);

    const [showFiltersModal, setShowFiltersModal] = useState(false);
    const handleCloseFiltersJob = () => setShowFiltersModal(false);
    const handleShowFiltersJob = () => setShowFiltersModal(true);

    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const handleCloseUpdate = () => setShowUpdateModal(false);
    const handleShowUpdate = (roleId: number) => {
        setSelectedRoleId(roleId);
        setShowUpdateModal(true);
    };

    const [selectedRoleId, setSelectedRoleId] = useState<number | null>(null);

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await fetch('/employee/list-available-roles');
                if (!response.ok) {
                    throw new Error('Failed to fetch roles');
                }
                const data: Role[] = await response.json();
                setRoles(data);
                setAllRoles(data);
            } catch (err: any) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        fetchRoles();
    }, []);

    if (loading) {
        return <p>Loading roles...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const filteredRoles = searchFilter(roles, searchQuery);

    const handleDelete = async (roleId: number) => {
        console.log("Submitting role:", roleId);
        try {
            const response = await fetch(`/staffing-manager/delete-role/${roleId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                console.log("Role deleted successfully");
                setSuccessMessage("Role deleted successfully!");
                setTimeout(() => {
                    setSuccessMessage("");
                }, 2000);

                setRoles((prevRoles) => prevRoles.filter((role) => role.roleId !== roleId));
            } else {
                console.error("Failed to delete role");
            }
        } catch (error) {
            console.error("An error occurred while deleting the role:", error);
        }
    };

    return (
        <div>
            {successMessage && (
                <div className="alert alert-success" role="alert">
                    {successMessage}
                </div>
            )}
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px', paddingLeft: '20px' }}>
                <Form.Control
                    type="text"
                    placeholder="Search"
                    value={searchQuery}
                    className="me-2"
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
                <Button variant="outline-primary" onClick={handleShowFiltersJob}>Filters</Button>

                <Modal show={showFiltersModal} onHide={handleCloseFiltersJob} size="lg" centered scrollable>
                    <Modal.Header closeButton>
                        <Modal.Title style={{ marginLeft: "auto" }}>Filter Job Roles</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FiltersForm
                            onClose={handleCloseFiltersJob}
                            onApply={(filters) => {
                                const filteredRoles = allRoles.filter((role) => {
                                    return (
                                        (filters.grade === "" || role.gradeRequired === filters.grade) &&
                                        (filters.department === "" || role.department === filters.department) &&
                                        (filters.duration === "" || role.duration === filters.duration) &&
                                        (filters.client === "" || role.client.toLowerCase().includes(filters.client.toLowerCase()))
                                    );
                                });
                                setRoles(filteredRoles);
                                handleCloseFiltersJob();
                            }}
                        />
                    </Modal.Body>
                </Modal>
            </div>
            <Table striped bordered hover style={{ fontSize: '15px', paddingLeft: '10px' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Grade</th>
                        <th>Department</th>
                        <th>Staffing Manager</th>
                        <th>Client</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredRoles.map((role) => (
                        <tr key={role.roleId}>
                            <td>{role.roleId}</td>
                            <td>{role.roleName}</td>
                            <td>{role.gradeRequired}</td>
                            <td>{role.department}</td>
                            <td>{role.staffingManagerEmailAddress}</td>
                            <td>{role.client}</td>
                            <td>
                                <button className="btn btn-primary" onClick={handleShowJob}>View</button>
                                <Modal show={showJobModal} onHide={handleCloseJob} size="lg" centered scrollable>
                                    <Modal.Header closeButton>
                                        <Modal.Title style={{ marginLeft: "auto" }}>{role.roleName}</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <JobRoleInformationForm role={role} />
                                    </Modal.Body>

                                    <Modal show={showUpdateModal} onHide={handleCloseUpdate} size="lg" centered scrollable>
                                        <Modal.Header closeButton>
                                            <Modal.Title style={{ marginLeft: "auto" }}>Update Job Role</Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body>
                                            {selectedRoleId && <UpdateRoleForm roleId={selectedRoleId} onClose={handleCloseUpdate} />}
                                        </Modal.Body>
                                    </Modal>
                                    <Container style={{ marginTop: "5px", marginBottom: "10px", maxWidth: "400px", textAlign: "center" }}>
                                        <Row>
                                            <Col xs={6}>
                                                <Button
                                                    variant="success"
                                                    size="lg"
                                                    onClick={() => handleShowUpdate(role.roleId)}
                                                    className="pull-right"
                                                >
                                                    Update Role
                                                </Button>
                                            </Col>
                                            <Col xs={6}>
                                                <Button
                                                    variant="danger"
                                                    size="lg"
                                                    onClick={() => handleDelete(role.roleId)}
                                                    className="pull-left"
                                                >
                                                    Delete Role
                                                </Button>
                                            </Col>
                                        </Row>
                                    </Container>
                                </Modal>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default StaffingManagerListRolesTable;

