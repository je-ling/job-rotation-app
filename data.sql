ALTER TABLE roles
MODIFY grade_required VARCHAR(50),
MODIFY department VARCHAR(50),
MODIFY duration VARCHAR(50),
MODIFY job_description VARCHAR(2000);

INSERT INTO job_rotation_db.staffing_managers
(user_id, email_address, first_name, last_name, password)
VALUES
(1, 'john.doe@example.com', 'john', 'doe', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(2, 'jane.doe@example.com', 'jane', 'doe', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(3, 'test.doe@example.com', 'test', 'doe', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(4, 'will.smith@example.com', 'will', 'smith', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(5, 'charlie.brown@example.com', 'charlie', 'brown', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(6, 'debbie.white@example.com', 'debbie', 'white', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(7, 'bob.smith@example.com', 'bob', 'smith', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(8, 'emily.clark@example.com', 'emily', 'clark', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(9, 'fred.jones@example.com', 'fred', 'jones', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6'),
(10, 'sarah.jane@example.com', 'sarah', 'jane', '$2y$12$xxTpctqZF2brm2H7mJZmT.ALw7CDDfMQxvlWWJ5pYRLWQFp8efjT6');

INSERT INTO roles (
  role_id, client, department, duration, grade_required, job_description,
  location, role_name, security_clearance_required,
  staffing_manager_email_address, start_date, version
) VALUES 
(152, 'John Lewis', 'ENGAGEMENT_MANAGEMENT', 'THREE_MONTHS', 'GRADE_4',
'As the engagement manager for the Red Team, you will be responsible for maintaining strong relationships with stakeholders and clients, ensuring successful delivery of projects. The role involves coordinating activities, communicating with stakeholders, and ensuring that client needs are met on time and to a high standard. Key responsibilities include monitoring project progress, identifying and resolving issues, and ensuring deadlines and quality standards are met. 

On site requirements - client site once a month and hybrid. 

Strong communication, organisation, and problem-solving skills are essential!',
'London', 'Engagement Manager', 'No', 'will.smith@example.com', '2026-04-30', 10),

(202, 'McDonalds', 'DIGITAL_EXPERIENCE', 'NINE_MONTHS', 'GRADE_3',
'You will be responsible for creating user-friendly and intuitive digital experiences for the client. The role involves understanding user needs, designing interfaces, and improving the usability and accessibility of a system. A typical day involves research, creating wireframes and prototypes, and working closely with developers to ensure the final product meets user expectations and accessibility needs. 

On site requirements - hybrid, monthly workshops. 

Preferable experience in frontend development or user-centric experience.',
'Birmingham', 'UX Designer', 'No', 'jane.doe@example.com', '2026-05-11', 3),

(203, 'McDonalds', 'DIGITAL_EXPERIENCE', 'NINE_MONTHS', 'GRADE_7',
'You will be responsible for creating user-friendly and intuitive digital experiences for the client. The role involves understanding user needs, designing interfaces, and improving the usability and accessibility of a system. A typical day involves research, creating wireframes and prototypes, and working closely with client developers to ensure the final product meets user expectations and accessibility needs. 

On site requirements - hybrid, monthly workshops.',
'Birmingham', 'UX Designer', 'No', 'jane.doe@example.com', '2026-05-11', 2),

(205, 'HMRC', 'AGILE_DELIVERY', 'TWELVE_MONTHS', 'GRADE_5',
'You will be responsible for identifying business needs and translating them into technical requirements. The role involves gathering and analysing requirements, working closely with stakeholders, and ensuring that solutions align with business objectives. You will help to bridge the gap between users and developers to ensure systems meet user needs and deliver value. 

On site requirements - hybrid, two days in client office per week.',
'Newcastle', 'Business Analyst', 'Yes', 'john.doe@example.com', '2026-06-17', 2),

(252, 'Anglian Water', 'DIGITAL_EXPERIENCE', 'TWELVE_MONTHS', 'GRADE_8',
'As a UX Researcher Lead you will oversee user research activities and ensuring insights are effectively used to improve product design. The role involves planning and guiding research strategies, managing research activities, usability testing, and mentoring team members. You will work closely with designers, developers, and stakeholders to ensure products are user-centred and aligned with user needs and requirements. 

On site requirements - once a month in client office, hybrid.',
'Manchester', 'UX Researcher Lead', 'No', 'jane.doe@example.com', '2026-06-01', 3),

(255, 'Scottish Water', 'ARCHITECTURE', 'NINE_MONTHS', 'GRADE_9',
'As a Senior Solutions Architect you are responsible for designing and overseeing the implementation of complex technical solutions that meet business requirements. The role involves defining system architecture, ensuring scalability, security, and performance, and guiding development teams throughout the project lifecycle. Collaboration with stakeholders is essential to align technical solutions with business goals and provide strategic technical leadership. 

On site requirements - client site, hybrid.',
'Birmingham', 'Solutions Architect', 'No', 'charlie.brown@example.com', '2026-05-25', 2),

(302, 'Met Police', 'BUSINESS_OPERATIONS', 'NINE_MONTHS', 'GRADE_6',
'As a Business Operations Partner you will be responsible for supporting and improving business processes to ensure efficient and effective operations. The role involves working with different teams to identify areas for improvement, analysing performance, and implementing solutions that align with business goals. Business Operations Partners help optimise workflows, support decision-making, and contribute to overall organisational success.',
'Telford', 'Business Operations Partner', 'No', 'debbie.white@example.com', '2026-05-25', 3),

(352, 'HMRC', 'PLATFORM_ENGINEERING', 'TWELVE_MONTHS', 'GRADE_5',
'As a DevOps Engineer you will be responsible for automating and improving the development and deployment processes of software systems. The role involves managing infrastructure, implementing CI/CD pipelines, and ensuring system reliability, scalability, and performance. As a DevOps Engineer you will work closely with development and operations teams to streamline workflows and enable faster, more efficient delivery of applications for the client. 

On site requirements - hybrid, two days in client office.',
'Bristol', 'DevOps Engineer', 'Yes', 'bob.smith@example.com', '2026-07-13', 1),

(364, 'John Lewis', 'ENGAGEMENT_MANAGEMENT', 'THREE_MONTHS', 'GRADE_4',
'As the engagement manager for the Red Team, you will be responsible for maintaining strong relationships with stakeholders and clients, ensuring successful delivery of projects. The role involves coordinating activities, communicating with stakeholders, and ensuring that client needs are met on time and to a high standard. Key responsibilities include monitoring project progress, identifying and resolving issues, and ensuring deadlines and quality standards are met. 

On site requirements - client site once a month and hybrid. 

Strong communication, organisation, and problem-solving skills are essential!',
'London', 'Engagement Manager', 'No', 'will.smith@example.com', '2026-04-30', 1);