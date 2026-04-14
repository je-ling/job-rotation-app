import { Container } from "react-bootstrap";
import EmployeeNavBar from "../components/EmployeeNavBar";
import ListRolesTable from "../components/ListRolesTable";
import Footer from "../components/FooterBar";


export const EmployeeLandingPage = () => {
    return (
        <>
            <EmployeeNavBar />
            {/* need search bar and filters here as comps*/}
            <Container style={{ marginTop: "10px", maxWidth: "700px", padding: "35px", textAlign: "center" }}>
                <ListRolesTable />
            </Container>
            <Footer />
        </>
    );
}

export default EmployeeLandingPage;