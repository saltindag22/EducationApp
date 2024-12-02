# Technical Overview: Teacher and Admin Management System with AI-Generated Questions

## 1. **System Architecture**

This system follows a **microservices**-like architecture with two core components:

- **Spring Boot Backend (Java)** – Responsible for handling the user roles, courses, questions, and interactions with the AI module.
- **Python-based AI Module** – Uses a **Large Language Model (LLM)** to generate university-level open-ended questions based on given topics.

### Key Components:
- **Spring Boot Application**: Provides APIs for course management, question management, and integrates with the AI module. This project is also structured by using the Model-View-Controller (MVC) design pattern, which separates the application's concerns into three distinct components:
    **Model**
        In this project, the Course, Topic, and Question entities are part of the model layer. These entities are managed using JPA and interact directly with the database.
    **View**
        The responses are structured in a way that makes it easy for frontend developers or API consumers to display data.
    **Controller**
        The controller layer handles user requests, processes them, and returns appropriate responses.
        In this project, Spring Controllers are used to expose API endpoints for managing courses, topics, and questions. They receive HTTP requests, delegate business logic to the service layer, and return responses.Examples of controller classes include CourseController and QuestionController.

- **AI Module (Python)**: A RESTful API that generates open-ended questions based on topics.
- **Database**: Stores courses, questions, and user information.
- **API Gateway**: Although a simple API design is used, you can imagine adding an API gateway if scaling is necessary in the future.
  
## 2. **Spring Boot Backend: Key Components**

### 2.1 **Role-Based Access Control (RBAC)**
- **Admin Role**: Full access to all courses, topics, and questions across the system.
- **Teacher Role**: Restricted to courses assigned to them. They can add, update, list, and delete (soft-delete) questions for their courses only.
- **Security**: Spring Security is used to handle authentication and authorization. JWT tokens are issued for login, and each user request is validated using these tokens.

### 2.2 **Course and Question Management**
The backend will expose RESTful APIs for managing courses, topics, and questions.

#### Database Models:
- **User Entity**:
  - `id` (Primary Key)
  - `email` (String)
  - `password` (String)
  - `role` (Enum)
  - `questions`(List<Question>) (One-to-Many relationship with User/Question)


- **Admin Entity**:
    -Extends User Entity class and inherits all properties.

- **Teacher Entity**:
    -Extends User Entity class and inherits all properties.
    - `courses`(List<Course>) (One-to-Many relationship with Teacher/Course)

- **Roles Entity**:
  - `TEACHER` (Enum)
  - `ADMIN` (Enum)

- **Course Entity**:
  - `id` (Primary Key)
  - `name` (String)
  - `topics` (Set<Topic>) (One-to-Many relationship with Course/Topic)
  - `teacher` (Teacher) (Many-to-One relationship with Course/Teacher)

- **Topic Entity**:
  - `id` (Primary Key)
  - `name` (String)
  - `course` (Course)(Many-to-One relationship with Topic/Course)
  - `questions`(Set<Question>) (One-to-Many relationship with Topic/Question)
  - `createdBy` (User) (Many-to-One relationship with Topic/User)

- **Question Entity**:
  - `id` (Primary Key)
  - `content` (String)
  - `topic` (Topic) (Many-to-One relationship with Question/Topic)
  - `createdBy` (Many-to-One relationship with Question/User)
  - `isDeleted` (Boolean for soft-delete)
  - `aiGenerated` (Boolean for mark as Ai generated)

### 2.3 **Authentication & Authorization**
- **Authorization**: Role-based access control is enforced using Spring Security. Each user has a role (`TEACHER` or `ADMIN`), and these roles determine access to different API endpoints.
- For this, JwtAuthorizationFilter.java is created and, in SecurityConfiguration.java, this filter is used to authorize.

```
### 2.4 **API Endpoints**

Here are the core APIs:

#### **Authentication Controller APIs**
- **POST** `/api/v1/auth/signin`: Authenticate a user and return a JWT token.
  - Input: SigninRequest (`email`, `password`)
  - Output: JwtAuthenticationResponse (token)

- **POST** `/api/v1/auth/signup`: This will allow new users to register.
    - Input: SignUpRequest(`email`, `password`)
    - Output: new User

- **POST** `/api/v1/auth/refresh`: Refresh expired tokens using a valid refresh token.
     - Input: RefreshTokenRequest(`token`)
     - Output: JwtAuthenticationResponse (refreshToken)


#### **User Controller APIs**
- **GET** `/api/user/{userId}`: Return the user which is accessed by its id.
- **GET** `/api/user/{email}`: Return the user which is accessed by its email.
- **GET** `/api/user/allUsers`: List all users (accessible by Admins).

#### **Course Controller APIs**
- **GET** `/api/course/allCourses`: List all courses (accessible by Admins).
- **GET** `/api/course/{id}`: Return the course which is accessed by its id (accessible by Admins).
- **POST** `/api/course/create`: Create a new course (accessible by Admins).
- **POST** `/api/course/{courseId}/assignTeacher/{userId}`: Assign a teacher in a course (accessible by Admins).
- **POST** `/api/course/{courseId}/addTopic/{topicId}`: Add a topic in a course (accessible by Admins).
- **PUT** `/api/course/update/{courseId}/{courseName}": Update course details (accessible by Admins).
- **DELETE** `/api/course/delete/{courseId}`: Delete course (accessible by Admins).

#### **Topic Controller APIs**
- **POST** `/api/topic/create`: Create a new topic.
- **GET** `/api/topic/{topicId}`:  Return the question which is accessed by its id.
**GET** `/api/question/allTopics`: List all questions in the system.
- **PUT** `/api/topic/update/{topicId}`: Update an existing question.
- **DELETE** `/api/topic/delete/{topicId}`: Delete a topic.

#### **Question Controller APIs**
- **POST** `/api/question/create`: Add a new question to a course (Teachers can only add to assigned courses).
**POST** `/api/question/generate-question`: Request the AI to generate a new question based on a topic.
- **GET** `/api/question/questionById{questionId}`:  Return the question which is accessed by its id.
**GET** `/api/question/allQuestions`: List all questions in the system.
- **PUT** `/api/question/update/{questionId}`: Update an existing question.
- **DELETE** `/api/question/softDelete/{questionId}`: Soft-delete a question (mark as deleted).



### 2.5 **Database Interaction**
- Use **JPA** (Java Persistence API) to manage entities and interact with the database.
- Use **MySQL** for development and production respectively. MySQL is used to persist courses, topics, and questions. In MySql,tables are created according to entities in the system.

---

## 3. **AI Module (Python)**

### 3.1 **AI Question Generation**
- **Python Flask**: The AI module is implemented using **Flask**, which will expose an endpoint that Spring Boot can communicate with.
- **Integration with LLM**: The AI module integrates with a large language model (LLM),Gemini Pro 1.5, to generate questions based on a given topic.

-This Python module accepts a `GET` request with a topic, generates a question using Gemini's API, and returns the generated question as a response.

### 3.2 **AI Endpoint Communication**
-** AI Service Implementation in Java**: AI Service takes topic input from User and pass it to FlaskAPI url and returns the new AI- generated question.
- **API Communication**: The Spring Boot backend calls the AI Service in Question Controller to generate questions. The `GET` request sent to `/api/question/generate-question` from Spring Boot would pass a topic, and the AI module responds with a generated question.
```

## 4. **Testing**

### 4.1 **API Testing with Postman
- Use **Postman** to manually test the backend API endpoints, including authentication, CRUD operations for courses and questions, and the AI question generation functionality.