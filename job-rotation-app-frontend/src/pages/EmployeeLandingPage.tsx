import { Container } from "react-bootstrap";
import EmployeeNavBar from "../components/EmployeeNavBar";
import ListRolesTable from "../components/ListRolesTable";
import Footer from "../components/FooterBar";
import { useState, useEffect } from "react";

export const EmployeeLandingPage = () => {
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

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await fetch('/employee/list-available-roles');
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
            <EmployeeNavBar />
            <Container style={{ marginTop: "10px", maxWidth: "75%", padding: "35px", textAlign: "center" }}>
                <ListRolesTable roles={filteredRoles} onRolesUpdate={setFilteredRoles} onResetFilters={resetFilters} />
            </Container>
            <Footer />
        </>
    );
}

export default EmployeeLandingPage;