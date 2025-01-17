# Research Report: Intro to Spring Boot

## Overview

### Summary of Work

I researched Spring Boot basics, as it seems that is the most straight-forward well documented implementation of our backend that can interact with our frontend and database system. I read through some SpringBoot tutorials to create some starter files, which can be found in the `backend/` folder of our repository.

I wanted to create a starting file structure to build upon as we start implementing more code throughout the project, including some start files that can house some basic functionalities, such as a `User` class. The `User` class and functionality was the primary target of implementing this basic Spring Boot framework, as we know from our architecture plan that we will be storing users in databases, as well as using user information on the frontend (login, leaderboard, stats), so it will use all main parts.

### Motivation

Our project will use Spring Boot for the backend implementation, as it provides easy ways to interact with the frontend via REST API, as well as database integration. To create the starter files and general folder structure for the backend, I needed to do some research on Spring and how it can be efficiently implemented in our project.

### Time Spent

2.5 hours

## Information Learned and Results

### Starter Files

For getting the starter files, I used an initializer (please see Code section for more information on this). The main components of this code initializer for creating a Spring Boot project are:
    - Group: denotes the package name; typically a reverse domain name. For the starter files, I used com.wordle
    - Artifact: denotes the application name; as this project is the sole backend, I have named this backend
    - Package: Java code is organized into packages (which map into folders in your file structure). Packages are the namespace of Java. By default, I left his as com.wordle.backend.

This created a nice folder structure in `src/`. 

### Project and Folder Structure

I found a [great article](https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819) that discussed the best practices for utilizing the Spring Boot framework; this led me to add **model**, **controller**, **repository**, and **service** folders.

1. Controller
        - Contains RESTful controller classes to handling incoming HTTP requests; as per the README, we are utilizing REST API, and this is the folder that houses the files used for frontend integration via HTTP request handling.
2. Model
        - Stores data structures and overall behavior of the application domain
        - These classes are typically mapped to database tables
3. Repository
        - Repository classes that deal with data access, typically with a relational mapping framework (either ORM or JPA). For now, I have left the implementation up to us to decide
4. Service
        - Implement business logic; used by the controllers to perform operations on and change ata.

### So, why use Spring Boot?

We now have an idea of some of the basics for the structure of a Spring Boot application, and we know that it makes integration easy for building a web page...but what else should we know about Spring Boot for our Wordle game?

Overview:
    - Takes care of dependencies and initialization for an easy way to run our application 

Some of the main features are:
    - No XML configuration needed
    - Easy maintenance and REST API creation
        - simple annotations @RestController and @RequestMapping
    - Simple deployment

### Code

To get the Spring Boot initial path, I used [this initializer](https://start.spring.io/), which allowed me to choose the Java version and also set up a gradle build environment.

**NOTE:** With the Spring Boot initializer, I had to select a Java version. I selected version 17, what I am currently running. This is apparent in `build.gradle`, and will need to be changed in that file if running a Java version beyond 17.


### Sources
https://spring.io/projects/spring-boot

https://www.geeksforgeeks.org/introduction-to-spring-boot/

https://spring.io/guides/tutorials/rest

https://tom-collings.medium.com/controller-service-repository-16e29a4684e5

https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819
