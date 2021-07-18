# Buddy-App

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=Buddy-App-Interactive_Buddy-App)

## Overview

A chat application where people can create chat requests or accept chat requests in order to chat with people. 

### How to use

Install app on android phone and you're ready to go. Webservice is hosted on Heroku so no webservice hosting is required.

## Features included in App
- Login / Register with e-mail address.
- Login / Register with random key for anonymity (Only Username -> Login with Unique Login Key)
- Creating chat requests
- Accepting chat requests from other users
- Setting current mood
- Karma system
- Updating profile
- Chat with users


## Technologies used
### App
- Android (Kotlin)
- Material Design - Guidelines for Better Usability
- Volley - For Rest API requests
- GSON - For JSON Parsing
- Socket.io - For Socket API requests
- android.emoji support - For emoji support in chat

### Webservice
- Node.js - Webservice framework
- Socket.io - Socket connection module
- Express - Rest API module
- MongoDB - DB
- Mongoose - DB Connector and Schemas for better Data structure
- JWT Auth - Tokens for Rest communication
- bcrypt - for password hashing and salting
- dotenv - for .env file support

### DevOp
- Sonarcloud - Scanning for codesmells
- Github Actions - CI for Build, Unit testing ...

### Usability
- Paper Prototype See Usability Page
- Digital Prototype
- Usability Tests

## Group Members:
- Raphael Burgstaller
- Alexander Kogler
- Lukas Kohlmaier
