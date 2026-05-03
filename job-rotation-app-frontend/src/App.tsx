import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route } from "react-router-dom";
import Footer from "./components/FooterBar";
import EmployeeNavBar from "./components/EmployeeNavBar";
import { StaffingManagerLandingPage } from "./pages/StaffingManagerLandingPage";
import { StaffingManagerLoginPage } from "./pages/StaffingManagerLoginPage";
import { EmployeeLandingPage } from './pages/EmployeeLandingPage';


function App() {
  return (
    <>
    
      <Routes>
        <Route path="/staffing-manager-dashboard" element={<StaffingManagerLandingPage />} />
        <Route path="/login" element={<StaffingManagerLoginPage />} />
        <Route path="/" element={<EmployeeLandingPage />} />
         {/* <Route path="/" element={<div>Page Not Found</div>} />  */}
         {/* main landing page, need to add in employee landing page for this staffing-manager-dashboard */}
      </Routes>
    </>
  );
}

export default App;