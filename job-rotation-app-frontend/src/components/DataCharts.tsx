import { useEffect, useState } from "react";
import { PieChart, Pie, Tooltip, Cell, BarChart, Bar, Legend, YAxis, XAxis, CartesianGrid, AreaChart, Area } from "recharts";
import { Row, Col, Container, Button } from "react-bootstrap";

export const DataCharts = () => {

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
  const [, setError] = useState<string | null>(null);

  const fetchRoles = async () => {
    try {
      const response = await fetch('/staffing-manager/get-all-roles');
      if (!response.ok) {
        throw new Error('Failed to fetch roles');
      }
      const data: Role[] = await response.json();
      setRoles(data);
    } catch (err: any) {
      setError(err.message);
    }
  };

  useEffect(() => {
    fetchRoles();
  }, []);

  const getGradeData = (roles: Role[]) => {
    const gradeCounts: { [key: string]: number } = {};
    roles.forEach((role) => {
      gradeCounts[role.gradeRequired] = (gradeCounts[role.gradeRequired] || 0) + 1;
    });
    return Object.entries(gradeCounts).map(([grade, count]) => ({ grade, count }));
  };

  const gradeData = getGradeData(roles);

  const getPracticeData = (roles: Role[]) => {
    const practiceCounts: { [key: string]: number } = {};
    roles.forEach((role) => {
      practiceCounts[role.department] = (practiceCounts[role.department] || 0) + 1;
    });
    return Object.entries(practiceCounts).map(([practice, count]) => ({ practice, count }));
  };

  const practiceData = getPracticeData(roles);

  const getClientData = (roles: Role[]) => {
    const clientCounts: { [key: string]: number } = {};
    roles.forEach((role) => {
      clientCounts[role.client] = (clientCounts[role.client] || 0) + 1;
    });
    return Object.entries(clientCounts).map(([client, count]) => ({ client, count }));
  };

  const clientData = getClientData(roles);

  const getMonthStartData = (roles: Role[]) => {
    const monthCounts: { [key: string]: number } = {};
    roles.forEach((role) => {
      const month = new Date(role.startDate).toLocaleString('default', { month: 'short', year: 'numeric' });
      monthCounts[month] = (monthCounts[month] || 0) + 1;
    });
    return Object.entries(monthCounts).map(([name, count]) => ({ name, count }));
  };

  const roleMonthStartData = getMonthStartData(roles);

  return (
    <Container style={{ width: "90%", height: "100%", padding: "1px" }}>
      <h2 style={{ textAlign: "left" }}> Data Visualization </h2>
      <Row>
        <Col md={12} style={{ display: 'flex', justifyContent: 'center' }}>
          <div style={{ border: "1px solid #ccc", borderRadius: "6px", padding: "15px", marginTop: "18px", textAlign: "center", width: "2340px" }}>
            <h3>Total Open Roles</h3>
            <p style={{ fontSize: "24px", fontWeight: "bold" }}>{roles.length}</p>
            <Button variant="outline-primary" onClick={fetchRoles}>
              Refresh Data
            </Button>
          </div>
        </Col>
      </Row>
      <Row >
        <Col md={6} style={{ display: 'flex', justifyContent: 'center' }}>
          <div style={{ border: "1px solid #ccc", borderRadius: "8px", padding: "16px", marginTop: "16px", width: "100%" }}>
            <h3 style={{ textAlign: "center" }}>Distribution of Roles by Client</h3>
            <PieChart width={600} height={400}>
              <Pie
                data={clientData}
                dataKey="count"
                nameKey="client"
                cx="50%"
                cy="50%"
                outerRadius={140}
                fill="#82ca9d"
                label={({ name, value }) => `${name}: ${value}`}
              >
                {clientData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={"#" + ((1 << 24) * Math.random() | 0).toString(16)} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </div>
        </Col>
        <Col md={6} style={{ display: 'flex', justifyContent: 'center' }}>
          <div style={{ border: "1px solid #ccc", borderRadius: "8px", padding: "16px", marginTop: "16px", width: "100%" }}>
            <h3 style={{ textAlign: "center" }}>Distribution of Roles by Month Start</h3>
            <AreaChart
              style={{ aspectRatio: 1.618 }}
              responsive
              data={roleMonthStartData}
              margin={{
                top: 20,
                right: 0,
                left: 0,
                bottom: 0,
              }}
              onContextMenu={(_, e) => e.preventDefault()}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" niceTicks="snap125" />
              <YAxis width="auto" niceTicks="snap125" />
              <Tooltip />
              <Area type="monotone" dataKey="count" stroke="#1DB8F2" fill="#1DB8F2" />
            </AreaChart>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md={6}>
          <div style={{ border: "1px solid #ccc", borderRadius: "8px", padding: "16px", marginTop: "16px" }}>
            <h3 style={{ textAlign: "center" }}>Distribution of Roles by Grade</h3>
            <BarChart
              width={500}
              height={300}
              data={gradeData}
              margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="grade" interval={0} angle={-45} textAnchor="end" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="count" fill="#00828E" />
            </BarChart>
          </div>
        </Col>
        <Col md={6}>
          <div style={{ border: "1px solid #ccc", borderRadius: "8px", padding: "16px", marginTop: "16px" }}>
            <h3 style={{ textAlign: "center" }}>Distribution of Roles by Practice</h3>
            <BarChart
              width={500}
              height={300}
              data={practiceData}
              margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="practice" interval={0} angle={-45} textAnchor="end" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="count" fill="#725F9F" />
            </BarChart>
          </div>
        </Col>
      </Row>

    </Container>
  );
};

export default DataCharts;
