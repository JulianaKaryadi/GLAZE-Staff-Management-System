# GLAZE-Staff-Management-System
GLAZE Staff Management System streamlines retail operations by centralizing task and shift management, allowing managers to track performance while giving staff clear visibility of responsibilities and schedules.

## Overview
GLAZE is a comprehensive staff management system designed to simplify task management and shift scheduling in retail environments. The system provides an intuitive interface for managers to assign tasks, schedule shifts, set deadlines, and track progress in real-time, while giving staff members clear visibility of their responsibilities and work hours.

## Problem Statement
The system addresses several key challenges in retail operations:
- **Task Management Issues**: Manual task management is time-consuming and error-prone, leading to missed deadlines and unclear responsibilities
- **Shift Scheduling Difficulties**: Creating and managing staff schedules manually is inefficient and often results in scheduling conflicts
- **Communication Gaps**: Lack of efficient communication channels for task updates and shift changes leads to misunderstandings and delays

## Features

### Manager Features
- User management (Add, Edit, Delete staff accounts)
- Task management (Create, Edit, View, Delete tasks)
- Shift scheduling
- Performance monitoring
- Report generation

### Staff Features
- View assigned tasks
- Update task status
- Add notes to tasks
- View shift schedule

## Folder Structure

The repository contains:
- **GlazeSystem**: Main application project
- **GlazeSystemLibrary**: Contains custom exception classes used by the main application

## Technology Stack
- **Frontend**: JSP, CSS, JavaScript
- **Backend**: Java EE, EJB
- **Database**: Java DB
- **Architecture**: Component-based development with reusable modules

## System Design

### User Interface
The system features a clean, intuitive interface with:
- Login page
- Role-based dashboards (Manager/Staff)
- Task management interfaces
- Schedule calendar view
- Reporting interface

### Data Model
The system uses a relational database with four main entities:
- Users (user_id, username, password, full_name, email, role, is_active, created_at)
- Tasks (task_id, creator_id, assigned_to_id, title, description, status, priority, created_at, updated_at, due_date, completed_at)
- Shifts (shift_id, user_id, creator_id, shift_date, start_time, end_time, created_at, updated_at)
- Task_Notes (note_id, task_id, user_id, note_content, created_at)

## Installation and Setup

1. **Prerequisites**
   - Java Development Kit (JDK) 8 or higher
   - Java EE compatible application server (GlassFish, WildFly, etc.)
   - Java DB or compatible database system

2. **Database Setup**
   - Create a new database named `glazedb`
   - Run the SQL scripts in the `database` folder to create the required tables

3. **Deployment**
   - Build the application using Maven or your preferred build tool
   - Deploy the resulting WAR file to your application server
   - Access the application at `http://localhost:8080/glaze` (or your configured URL)

## Usage

1. **Login**
   - Use your credentials to log in to the system
   - System will redirect to the appropriate dashboard based on user role

2. **Manager Dashboard**
   - Access staff management, task management, shift management, and reporting features
   - View overall system metrics and performance indicators

3. **Staff Dashboard**
   - View assigned tasks and their details
   - Update task status and add notes
   - View personal shift schedule

### GlazeSystemLibrary

The `GlazeSystemLibrary` module contains custom exception classes used throughout the application. This is packaged as a JAR file and included in the main GlazeSystem project. This approach follows component-based development practices by creating reusable components that can be maintained separately and integrated into the main application.

Key exceptions include:
- `AuthenticationException`: Thrown when authentication fails
- `DataAccessException`: Thrown when database operations fail
- `ValidationException`: Thrown when data validation fails
