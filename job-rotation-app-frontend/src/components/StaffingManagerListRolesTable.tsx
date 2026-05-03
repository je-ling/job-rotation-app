import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
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

type StaffingManagerListRolesTableProps = {
    roles: Role[];
    onRolesUpdate: (updatedRoles: Role[]) => void;
    onResetFilters: () => void;
};

const StaffingManagerListRolesTable: React.FC<StaffingManagerListRolesTableProps> = ({ roles, onRolesUpdate, onResetFilters }) => {
    const [searchQuery, setSearchQuery] = useState('');
    const [showJobModal, setShowJobModal] = useState(false);
    const handleCloseJob = () => setShowJobModal(false);
    const handleShowJob = (role: Role) => {
        setSelectedJobRole(role);
        setShowJobModal(true);
    };

    const [showFiltersModal, setShowFiltersModal] = useState(false);
    const handleCloseFiltersJob = () => setShowFiltersModal(false);
    const handleShowFiltersJob = () => setShowFiltersModal(true);

    const [selectedJobRole, setSelectedJobRole] = useState<Role | null>(null);

    const filteredRoles = searchFilter(roles, searchQuery);

    return (
        <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '20px', paddingLeft: '20px' }}>
                <Form.Control
                    type="text"
                    placeholder="Search"
                    value={searchQuery}
                    className="me-2"
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
                <Button variant="outline-primary" onClick={handleShowFiltersJob}>Filters</Button>
                <Button variant="outline-secondary" onClick={onResetFilters}>Reset</Button>

                <Modal
                    size="lg"
                    show={showFiltersModal}
                    onHide={handleCloseFiltersJob}
                    backdropClassName="custom-backdrop"
                    centered
                    scrollable
                >
                    <Modal.Header closeButton>
                        <Modal.Title style={{ marginLeft: "auto" }}>Filter Job Roles</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FiltersForm
                            onClose={handleCloseFiltersJob}
                            onApply={(filters) => {
                                const filteredRoles = roles.filter((role) => {
                                    return (
                                        (filters.grade === "" || role.gradeRequired === filters.grade) &&
                                        (filters.department === "" || role.department === filters.department) &&
                                        (filters.duration === "" || role.duration === filters.duration) &&
                                        (filters.client === "" || role.client.toLowerCase().includes(filters.client.toLowerCase()))
                                    );
                                });
                                onRolesUpdate(filteredRoles);
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
                                <button className="btn btn-primary" onClick={() => handleShowJob(role)}>View</button>
                                <Modal
                                    size="lg"
                                    show={showJobModal}
                                    onHide={handleCloseJob}
                                    backdropClassName="custom-backdrop"
                                    centered
                                    scrollable
                                >
                                    <Modal.Header closeButton>
                                        <Modal.Title style={{ marginLeft: "auto" }}>{selectedJobRole?.roleName}</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        {selectedJobRole && <JobRoleInformationForm role={selectedJobRole} />}
                                    </Modal.Body>
                                </Modal>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div >
    );
};

export default StaffingManagerListRolesTable;