import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route } from "react-router-dom";
import { StaffingManagerLandingPage } from "./pages/StaffingManagerLandingPage";
import { StaffingManagerLoginPage } from "./pages/StaffingManagerLoginPage";


function App() {
  return (
    <Routes>
      <Route path="/staffing-manager-dashboard" element={<StaffingManagerLandingPage />} />
      <Route path="/login" element={<StaffingManagerLoginPage />} />
      <Route path="*" element={<div>Page Not Found</div>} />
    </Routes>
  );
}

export default App;