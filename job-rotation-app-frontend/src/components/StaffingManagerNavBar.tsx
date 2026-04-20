import { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import { Button } from "react-bootstrap";
import logo from '../assets/logo1.png';

export function StaffingManagerNavBar() {
  const [managerName, setManagerName] = useState("");

  const logout = () => {
    localStorage.removeItem("staffingManagerName");
    localStorage.removeItem("staffingManagerEmailAddress");

    window.location.href = "/login";
  };

  useEffect(() => {
    const name = localStorage.getItem("staffingManagerName");
    if (name) {
      setManagerName(name);
    }
  }, []);

  return (
    <>
      {[false].map((expand) => (
        <Navbar
          key={String(expand)}
          expand={expand}
          className="mb-3"
          style={{ height: '90px', backgroundColor: '#0058AB', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
        >
          <Container>
            <img
              src={logo}
              width="180"
              height="45"
              alt="Company Logo"
            />
            <Navbar.Brand style={{ fontSize: '2.5rem', color: 'white', textAlign: 'center', marginLeft: '100px' }} href="#">
              NEXT STEP
            </Navbar.Brand>
            <div style={{ display: "flex", alignItems: "center" }}>
              <Navbar.Text style={{ marginRight: "10px", color: "white" }}>
                Signed in as: {managerName || "USER"}
              </Navbar.Text>
              <Button variant="outline-light" onClick={logout}>
                Sign Out
              </Button>
            </div>
          </Container>
        </Navbar>
      ))}
    </>
  );
}

export default StaffingManagerNavBar;