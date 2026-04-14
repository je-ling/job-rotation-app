import React, { useState } from 'react';

type Role = {
    roleId: number;
    roleName: string;
    gradeRequired: string;
    department: string;
    location: string;
    staffingManagerEmailAddress: string;
    duration: string;
    jobDescription: string;
    startDate: string;
    securityClearanceRequired: string;
};

export const JobRoleInformationForm: React.FC<{ role: Role }> = ({ role }) => {
    const [showPopup, setShowPopup] = useState(false);

    const copyToClipboard = (text: string) => {
        navigator.clipboard.writeText(text);
        setShowPopup(true);
        setTimeout(() => setShowPopup(false), 2000);
    };

    return (
        <div className="job-role-details" style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
            <section className="job-information" style={{ border: '1px solid #ccc', borderRadius: '8px', padding: '15px' }}>
                <h4>Job Information</h4>
                <div style={{ display: 'flex', gap: '25px' }}>
                    <dl style={{ flex: 1 }}>
                        <dt>Grade Required:</dt>
                        <dd>{role.gradeRequired}</dd>
                        <dt>Department:</dt>
                        <dd>{role.department}</dd>
                        <dt>Location:</dt>
                        <dd>{role.location}</dd>
                        <dt>Duration:</dt>
                        <dd>{role.duration}</dd>
                    </dl>
                    <dl style={{ flex: 1 }}>
                        <dt>Start Date:</dt>
                        <dd>{role.startDate}</dd>
                        <dt>Security Clearance Required:</dt>
                        <dd>{role.securityClearanceRequired}</dd>
                        <dt>Contact Staffing Manager:</dt>
                        <dd>
                            {role.staffingManagerEmailAddress}
                            <button
                                onClick={() => copyToClipboard(role.staffingManagerEmailAddress)}
                                style={{ marginLeft: '8px', cursor: 'pointer', border: 'none', background: 'none' }}
                                aria-label="Copy staffing manageremail address"
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-copy" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M4 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 5a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1h1v1a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h1v1z" />
                                </svg>
                                {/* from bootstrap icons site */}
                            </button>
                        </dd>
                    </dl>
                </div>
            </section>

            <section className="job-description" style={{ border: '1px solid #ccc', borderRadius: '8px', padding: '16px'}}>
                <h4>Job Description</h4>
                <div className="card-body">
                    <p style={{ whiteSpace: 'pre-wrap' }}>{role.jobDescription}</p>
                </div>
            </section>

            {showPopup && (
                <div style={{
                    position: 'fixed',
                    top: '20%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    backgroundColor: 'green',
                    color: '#fff',
                    padding: '10px 20px',
                    borderRadius: '8px',
                }}>
                    Copied to clipboard!
                </div>
            )}
        </div>
    );
};

export default JobRoleInformationForm;