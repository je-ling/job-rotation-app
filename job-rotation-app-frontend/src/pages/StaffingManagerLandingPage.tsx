import Container from "react-bootstrap/esm/Container";
import Footer from "../components/FooterBar";
import CreateRoleForm from "../components/CreateRoleForm";
import { useState, useEffect } from "react";
import StaffingManagerNavBar from "../components/StaffingManagerNavBar";
import UpdateRoleForm from "../components/UpdateRoleForm";
import DeleteRoleForm from "../components/DeleteRoleForm";
import StaffingManagerListRolesTable from "../components/StaffingManagerListRolesTable";
import { Tab, Tabs } from "react-bootstrap";
import DataCharts from "../components/DataCharts";


export const StaffingManagerLandingPage = () => {
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

    const [roles, setRoles] = useState<Role[]>([]);
    const [filteredRoles, setFilteredRoles] = useState<Role[]>([]);
    const [, setError] = useState<string | null>(null);
    const [selectedRoleId,] = useState<number | null>(null);

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await fetch('/staffing-manager/get-all-roles');
                if (!response.ok) {
                    throw new Error('Failed to fetch roles');
                }
                const data: Role[] = await response.json();
                setRoles(data);
                setFilteredRoles(data);
            } catch (err: any) {
                setError(err.message);
            }
        };
        fetchRoles();
    }, []);

    const resetFilters = () => {
        setFilteredRoles(roles);
    };

    return (
        <>
            <StaffingManagerNavBar />
            <Container style={{ marginTop: "10px", maxWidth: "1400px", padding: "30px", paddingLeft: '60px' }}>
                <Tabs
                    defaultActiveKey="view-roles"
                    id="fill-tab-example"
                    className="mb-3"
                    fill
                >
                    <Tab eventKey="view-roles" title="View Roles">
                        <StaffingManagerListRolesTable roles={filteredRoles} onRolesUpdate={setFilteredRoles} onResetFilters={resetFilters} />
                    </Tab>
                    <Tab eventKey="create" title="Create Role">
                        <CreateRoleForm onRoleCreated={(newRole) => {
                            setRoles((prevRoles) => [...prevRoles, newRole]);
                            setFilteredRoles((prevFilteredRoles) => [...prevFilteredRoles, newRole]);
                        }} />
                    </Tab>
                    <Tab eventKey="update-role" title="Update Role">
                        <UpdateRoleForm
                            role={roles.find((r) => r.roleId === selectedRoleId) || null}
                            onRoleUpdate={(updatedRole) => {
                                setRoles((prevRoles) => prevRoles.map((role) => role.roleId === updatedRole.roleId ? updatedRole : role));
                                setFilteredRoles((prevFilteredRoles) => prevFilteredRoles.map((role) => role.roleId === updatedRole.roleId ? updatedRole : role));
                            }} />
                    </Tab>
                    <Tab eventKey="delete-role" title="Delete Role">
                        <DeleteRoleForm role={roles.find((r) => r.roleId === selectedRoleId) || null}
                            onRoleDelete={(deletedRole) => {
                                setRoles((prevRoles) => prevRoles.filter((role) => role.roleId !== deletedRole.roleId));
                                setFilteredRoles((prevFilteredRoles) => prevFilteredRoles.filter((role) => role.roleId !== deletedRole.roleId));
                            }} />
                    </Tab>
                    <Tab eventKey="charts" title="Charts">
                        <DataCharts/>
                    </Tab>
                </Tabs>
            </Container>
            <Footer />
        </>
    );
};

export default StaffingManagerLandingPage;