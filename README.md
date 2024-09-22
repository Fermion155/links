# URL SHORTENER APPLICATION

Url shortener REST application which provides a shorter version of the url, which can be used to access the same website of the long url.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)

## Installation

1. Clone the repository:
   ``` sh
   https://github.com/Fermion155/links.git
   ```
2. Navigate to the project directory:
    ```sh
    cd links
    ```
3. Run application:    
    a. On docker:
    ``` sh
    docker-compose up
    ```
    b. In code editor:  
    Create database docker service:
    ``` sh  
    docker-compose up db
    ```  
   In code editor use VM options to activate a profile:
     ```sh
    -Dspring.profiles.active=local
     ```
## Usage

 The application will be accessible at ``localhost:8080/``   
 To get a shortened url use `` POST `` request ``localhost:8080/`` with plain text request body:  
 ```sh
 wwww.example.com
 ```
Result will be a shortened link:
```sh
http://localhost:8080/5symmlQ
```
Use ``GET`` request on the received link and you will be redirected to the requested url.
