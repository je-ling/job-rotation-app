import React, { useEffect, useState } from 'react';
import { Modal } from 'react-bootstrap';
import Table from 'react-bootstrap/Table';
import DeleteRoleForm from './DeleteRoleForm';
import { JobRoleInformationForm } from './JobRoleInformationForm';

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


const ListRolesTable: React.FC = () => {
    const [showJobModal, setShowJobModal] = useState(false);
    const [roles, setRoles] = useState<Role[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const handleCloseJob = () => setShowJobModal(false);
    const handleShowJob = () => setShowJobModal(true);

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await fetch('/employee/list-available-roles');
                if (!response.ok) {
                    throw new Error('Failed to fetch roles');
                }
                const data: Role[] = await response.json();
                setRoles(data);
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

    return (
        <div>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Grade</th>
                        <th>Department</th>
                        <th>Location</th>
                        <th>Staffing Manager</th>
                        <th>Information</th>
                    </tr>
                </thead>
                <tbody>
                    {roles.map((role) => (
                        <tr key={role.roleId}>
                            <td>{role.roleId}</td>
                            <td>{role.roleName}</td>
                            <td>{role.gradeRequired}</td>
                            <td>{role.department}</td>
                            <td>{role.location}</td>
                            <td>{role.staffingManagerEmailAddress}</td>
                            <td>
                                <Modal show={showJobModal} onHide={handleCloseJob} size="lg" centered scrollable>
                                    <Modal.Header closeButton>
                                        <Modal.Title style={{ marginLeft: "auto" }}>{role.roleName}</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <JobRoleInformationForm role={role} />
                                    </Modal.Body>
                                </Modal>
                                <button className="btn btn-primary" onClick={handleShowJob}>View Details</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default ListRolesTable;