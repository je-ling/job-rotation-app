import { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import logo from "../assets/logo.svg";

export function StaffingManagerNavBar() {
  const [managerName, setManagerName] = useState("");

  useEffect(() => {
    const name = localStorage.getItem("staffingManagerName");
    if (name) {
      setManagerName(name);
    }
  }, []);

  return (
    <>
      {[false].map((expand) => (
        <Navbar key={String(expand)} expand={expand} className="bg-body-tertiary mb-3">
          <Container>
            <img src={logo} width="150" height="80" alt="Company Logo" />
            <Navbar.Brand style={{ fontSize: "2.5rem", marginRight: "70px" }} href="#">
              NEXT STEP
            </Navbar.Brand>
            <Navbar.Text>
              Signed in as: {managerName || "USER"}
            </Navbar.Text>
          </Container>
        </Navbar>
      ))}
    </>
  );
}

export default StaffingManagerNavBar;