import { Container } from "react-bootstrap";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import EmployeeNavBar from "../components/EmployeeNavBar";
import Footer from "../components/FooterBar";

export const StaffingManagerLoginPage = () => {

    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const response = await fetch("/staffing-manager/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include",
                body: JSON.stringify({
                    emailAddress,
                    password,
                }),
            });

            if (response.ok) {
                // redirect user to the staffing manager dashboard page
                localStorage.setItem("staffingManagerLoggedIn", "true");
                localStorage.setItem("staffingManagerEmailAddress", emailAddress);
                localStorage.setItem("staffingManagerName", emailAddress.split("@")[0]);
                navigate("/staffing-manager-dashboard");

            } else if (response.status === 400) {
                setError("Both email and password inputs are required");
            } else if (response.status === 401) {
                setError("Invalid email or password provided");
            } else {
                setError("Invalid credentials provided, please try again.");
            }
            //  error messages displayed to user on the UI
        } catch (err) {
            setError("An error occurred. Please try again.");
            console.error("Login error:", err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <EmployeeNavBar />
            <Container style={{ marginTop: "50px", maxWidth: "600px", padding: "35px", borderRadius: "10px" }}>
                <h1 className="mb-4" style={{ marginTop: "80px", textAlign: "center", fontSize: "2.0rem" }}>Staffing Manager Login</h1>
                <hr className="hr" />
                <Container style={{ marginTop: "50px", marginBottom: "200px", maxWidth: "420px", border: "1px solid #ccc", padding: "35px", borderRadius: "10px" }}>
                    {error && <div className="alert alert-danger">{error}</div>}
                    <form onSubmit={handleSubmit}>
                        <div className="form-group mb-3">
                            <label htmlFor="emailInput">Email address</label>
                            <input
                                type="email"
                                className="form-control"
                                id="emailInput"
                                placeholder="Enter email"
                                value={emailAddress}
                                onChange={(e) => setEmailAddress(e.target.value)}
                                disabled={loading}
                                required
                            />
                        </div>
                        <div className="form-group mb-3">
                            <label htmlFor="passwordInput">Password</label>
                            <input
                                type="password"
                                className="form-control"
                                id="passwordInput"
                                placeholder="Enter password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                disabled={loading}
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="btn btn-primary w-75"
                            disabled={loading}
                            style={{ textAlign: "center", display: "block", marginLeft: "auto", marginRight: "auto" }}
                        >
                            {loading ? "Logging in..." : "Login"}
                        </button>
                    </form>
                </Container>
            </Container>
            <Footer />
        </>
    );
}

export default StaffingManagerLoginPage;