# BankingApplication Backend
The objective of this project was to build a simple banking application using RestControllers. The project is built using Java SpringBoot to create a WebApplication that allows users to Create an Account, Login, Create current and saving accounts, make trasactions using these accounts such as deposit and withdraw and also view transaction history for these accounts. 

Usage
-----
* Must have Maven
* Must have MySQL
* Tomcatserver
* Open terminal and git clone https://github.com/Alextrueman97/BankingApplication.git
* Open project into IDE
* Change the properties file root: and password:
* Run the application as a spring boot project

Updates
---
The backend of the project is complete so this will be able to work with any front end created for it. 
The CrossOrigin annotation on the controllers allows requests to be made from a front end project running on localHost:3000
Future improvements to this project will be to create the front end once I am able to learn ReactJs. 
Another future improvement is to change the transactions based on the Saving Account to allow the user to deposit from currentAccount into SavingAccount and also withdraw from SavingAccount into CurrentAccount instead of deposits coming from anywhere and withdraws going from SavingAccount and not into CurrentAccount. 
A final improvement would also be to add Spring Security for the Registraion and Login to make the account more secure and also allow encryption of passwords.  
