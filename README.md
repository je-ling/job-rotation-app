# Next Step - Job Rotation Application

Work based report module project encompassing career development and rotation resources, hosting internal role opportunities available within the Next Step application.

To run the application execute the following commands:

1. /job-rotation-app/job-rotation-app-backend

```bash
./gradlew bootRun
# to run the application
```

2. /job-rotation-app/job-rotation-app-frontend

```bash
npm run dev
# open and launch the development environment in a browser https://localhost:5173
```

## Frontend Development Tools

1. TypesScript
2. Boostrap
3. React

## Backend Development Tools

1. Java
2. SpringBoot
3. MySQL Database

## Frontend

The frontend consists of multiple screens aimed for employee and staffing manager end users.

To run the frontend application by itself:

```bash
npm run dev
# open and launch the development environment in a browser https://localhost:5173
```

```bash
npm install
# install all project dependencies
```

```bash
npm audit fix
# address vunerabilites of dependencies issues
```

### Pages Implemented

- Employee Landing Page
- Resource Pages: staffing manager contact list, express interest, GTD support, people support, FAQs, rotation process
- Staffing Manager Landing Page
- Create Role
- Update Role
- Delete Role
- Data Charts

## Backend

The backend consists of several APIs implemented to carry out the data exchange functionalties required. Refer to the openApiSpec.yml file within the job-rotation-app-backend folder to view all APIs implemented with its responses, parameters and data expected. Unit tests have been created to validate functionalitis of all APIs developed.

To run the backend application itself:

```bash
./gradlew bootRun
# to run the application
```

```bash
./gradlew build
# to build the application and its dependencies
```

```bash
./gradlew test -i
# to run tests
```

## Database
1. Install MySQL Workbench
2. Create a database connection with connection name: 'job_rotation_db', port: 3306, username: root, password: new_password, and hostname: 127.0.0.1
3. Create a schema named job_rotation_db
3. Run .gradlew bootRun within the job-rotation-app-backend directory to spin up the backend - this will create the necessary tables required for the application
4. Update the data type for grade_required, department, duration to VARCHAR(50) and job_description to VARCHAR(1000)
5. Execute the SQL code within the 'data.sql' file within MySQL workbench for data to use while running the application. 

## Troubleshoot
- Data not rendering? - ensure the backend is running before running the frontend
