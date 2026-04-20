import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap';
import Table from 'react-bootstrap/Table';
import { JobRoleInformationForm } from './JobRoleInformationForm';
import FiltersForm from './FiltersForm';

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

//  TO DO: need to update role fields

const searchFilter = (roles: Role[], query: string): Role[] => {
    return roles.filter((role) => {
        const roleIdMatch = role.roleId.toString().includes(query);
        const roleNameMatch = role.roleName.toLowerCase().includes(query.toLowerCase());
        const departmentMatch = role.department.toLowerCase().includes(query.toLowerCase());
        const locationMatch = role.location.toLowerCase().includes(query.toLowerCase());
        return roleIdMatch || roleNameMatch || departmentMatch || locationMatch;
    });
}

const ListRolesTable: React.FC = () => {
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

    return (
        <div>
            <div style={{ marginLeft: '55px' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px', paddingLeft: '10px' }}>
                    <Form.Control
                        type="text"
                        placeholder="Search"
                        value={searchQuery}
                        className="me-2"
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                    <Button variant="outline-primary" onClick={handleShowFiltersJob}>Filters</Button>
                </div>
                <Modal show={showFiltersModal} onHide={handleCloseFiltersJob} size="lg" centered scrollable>
                    <Modal.Header closeButton>
                        <Modal.Title style={{ marginLeft: "auto" }}>Filter Job Roles</Modal.Title>
                    </Modal.Header>
                    {/* TO DO: have client as a filter based of stakeholder feedback */}
                    <Modal.Body>
                        <FiltersForm onClose={handleCloseFiltersJob} onApply={(filters) => {
                            const filteredRoles = allRoles.filter((role) => {
                                return (
                                    (filters.grade === "" || role.gradeRequired === filters.grade) &&
                                    (filters.department === "" || role.department === filters.department) &&
                                    (filters.duration === "" || role.duration === filters.duration)
                                );
                            });
                            setRoles(filteredRoles);
                            handleCloseFiltersJob();
                        }} />
                    </Modal.Body>
                </Modal>
            </div>
            <Table striped bordered hover style={{ fontSize: '15px', textAlign: 'center' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Grade</th>
                        <th>Department</th>
                        <th>Location</th>
                        <th>Staffing Manager</th>
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
                                <button className="btn btn-primary" onClick={handleShowJob}>View</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
};

export default ListRolesTable;