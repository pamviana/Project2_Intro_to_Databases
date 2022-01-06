# Project 2 Intro to Databases

**Step 1 – Database implementation** 
Run the SQL Text File on MySQL.

File can be found at:  
Project2_DATABASES > Database Implementation > Project2  

*OBS: Line 329 and 330 are calling 2 procedures that add data to the database.*  
![image](https://user-images.githubusercontent.com/54336594/148451691-7d15a266-bd33-4a93-9c44-8549c54d02cd.png)

The procedures’ code can be found at:  
Database Implementation > addUser  
Database Implementation > addSubject  

We are using a VIEW in our application, so that needs to be added too.  
It can be found at:  
Database Implementation > typeOfAccess  

**Step 2 – Java Swing**
The step-by-step to install Java Swing will depend on the IDE. We used Eclipse, and followed  
this tutorial:  
https://www.youtube.com/watch?v=ndhFmgzi6io&t=190s  

**Step 3 – Install JDBC**
**Step 4 – Add classes to the Java Project**
We have 5 classes total. Where four of them are a JFrame class, and one (Store.java) is a normal
java class. 

![image](https://user-images.githubusercontent.com/54336594/148451882-f50f28fc-65e1-44b4-81f6-e51154b68d6a.png)

Files can be found at:  
Java

**Step 5 – Set up the connection between JDBC and MySQL**  
You will only need to change the connection information on the class Store:  

![image](https://user-images.githubusercontent.com/54336594/148451961-6ba907f6-f3da-4f5d-8f30-e15d62ccf5e0.png)

Change password, project, and user accordingly.  

**Step 6 – Populate the database with some data before generating reports** 
When everything is set up, run the project.  

1) Go to “Assign/Delete Privileges”

![image](https://user-images.githubusercontent.com/54336594/148452053-6dca5ad8-347d-4c48-ad89-1b66348c7fb5.png)


2) Assign privileges to some users:  
- Select a user  
- Select an object  
- Check some privileges  
- Click on “Assign”  

![image](https://user-images.githubusercontent.com/54336594/148452091-7353161d-107b-4b19-8e1a-81e69c76dfc8.png)

3) Now you have data to the database and will be able to generate some reports.  
For example:  

![image](https://user-images.githubusercontent.com/54336594/148452136-396b724e-f4f0-440b-b23b-77c45af600b9.png)  

![image](https://user-images.githubusercontent.com/54336594/148452165-609e1a04-67c2-438e-98f2-84db54b35404.png)  
 



