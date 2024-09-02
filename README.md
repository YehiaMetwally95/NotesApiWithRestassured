1- Implement E2E Scenario on API Layer Using Rest Assured Notes API
https://practice.expandtesting.com/notes/api/api-docs/#/Users/post_users_login

2- Using Object Model Design by setting two Object Model Classes for Request and Response of every Endpoint such that Request Object Model Class that contains all methods performed on request including request body generation & Response Object Model Class that contains all validations and getters performed on the response

3- Using Pojo Classes for storing all Request and Response Parameters for every Endpoint with the help of Jackson Library for deserializing Json Responses into Pojo Class Objects

4- Prepare Request Body Data Statically from Mocking Server (Json Server) containing static test data & Dynamically using TimeStamp for generating dynamic test data 

5- Read Request Body as Object from Pojo Class of Request & Perform Dynamic Validations on Response data against The Input Test Data stored on Pojo Class of Request whether the data is Static or Dynamic

6- Using Lombok Library for generating Setters, Getters & Constructors & ToString of All Pojo Classes, thus reduce Boiler plate Code

7- Using Builder Pattern for setting and constructing Request Pojo Classes with input parameters step by step in fluent manner

8- Using Fluent Object Model Design Pattern in writing test script, thus chaining all requests for the E2E scenario and all validations on the responses in one line of code

9- Using Fluent Facade Design Pattern for abstracting unnecessary requests and encapsulate them into one request, thus making E2E scenario requests more short and readable

10- Using ApiManager Util that provide abstracted methods for sending all kind of requests and for different manipulations on API response

11- Allure Report for Reporting Test Result, and Logging Test Steps (all sent requests and all validations on responses)
