Please run the following SQL scripts before testing the application.
These tables (users and authorities) are required by Spring Security for authentication to work properly.

SQL schema: **eCommerce**

Security Tables for Admin Authentication:


```sql
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_auth_users FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    UNIQUE(username, authority)
);
