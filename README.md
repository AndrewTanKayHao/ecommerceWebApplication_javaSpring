<p align="center">
  <img src="src/main/resources/static/images/team5.png" alt="Team5 Logo" width="300">
</p>

<h2 align="center">JavaEE CA Project</h2>

<h3 align="center">GDipSA-61</h3>

<h4 align="center">Team 5</h4>

**Group Members:**
- Angeline Yap Zhi Han 		- A0315792X
- Dang Dinh Hoang Lam 		- A0315355H
- Honey Win Naing 			- A0332346J
- Nyunt Sin Htet			- A0331424R
- Sara Johari 				- A0332469X
- Tan Kay Hao Andrew 		- A0335094A
- Teo Chee Wee 				- A0332432R
- Theingi Myint 			- A0332945Y

**AI Tool Declaration:**

We used GPT-5.0 to generate a list of luxurious stationery products, product descriptions, and product images featured in our application.
We are responsible for the content and quality of the submitted work.

⚠️ **Important:** 

Please run the following SQL scripts before testing the application.
These tables (users and authorities) are required by Spring Security for authentication to work properly.

SQL schema: **eCommerce**

-- Security Tables for Admin Authentication


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

