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
}

function addRole(role: Role) {
    fetch("/staffing-manager/roles", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(role),
    })
    .then(response => response.json())
    .then(data => {
        console.log("Role created successfully:", data);
        // show success message (check how to do seconds then change) and navigate back to main staffing manager page
    })
    .catch(error => {
        console.error("Error creating role:", error);
    });
}

export const CreateRolePage = () => {
    return (
        <div>
            <h1>Create Role Page</h1>
            <form>
                {/* Form fields for role creation */}
                <div>
                    <label htmlFor="roleName">Role Name:</label>
                    <input type="text" id="roleName" name="roleName" required />
                </div>
                <div>
                    <label htmlFor="description">Description:</label>
                    <textarea id="description" name="description" required></textarea>
                </div>
                <div>
                    <label htmlFor="department">Department:</label>
                    <input type="text" id="department" name="department" required />
                </div>
                <div>
                    <label htmlFor="location">Location:</label>
                    <input type="text" id="location" name="location" required />
                </div>
                 <button type="submit">Create Role</button>
            </form>
        </div>
    );
}
