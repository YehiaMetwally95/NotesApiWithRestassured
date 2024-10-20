## Overveiw
- Implement E2E Scenarios on API Layer
- Using Pojo Classes for storing all Request and Response Parameters for every Endpoint with the help of Jackson Library for deserializing Json Payloads into Pojo Classes
- Using Lombok Library for generating Setters, Getters & Constructors & ToString of All Pojo Classes, thus reduce Boiler plate Code
- Using Object Model Design by setting two Object Model Classes for Request and Response of every Endpoint such that:
  - Request Object Model Class that contains all methods performed on Request Parameters through Request Pojo Class, includes Preparing Request body with static or dynamic data and Executing Request
  - Response Object Model Class that contains all Validations and Getters performed on the Response Parameters through Response Pojo Class
- Prepare Request Body as follows:
  - Statically from Json Files createad for each test, containing static test data --> (usually requires update before test cases that needs unique data or test cases that write on test data)
  - Dynamically using TimeStamp "for Unique Data" and DataFaker "for Descriptive Data", for Generating Dynamic and Unique Test Data --> (doesn't require any updates for any tests)
- Used Design Pattern
  -	Builder Pattern for Constructing Request Pojo Class with input parameters step by step in fluent manner to build the Request Body
  - Fluent Object Model Design Pattern in writing Test Script, thus chaining all Steps and different requests sent for the E2E scenario and all validations on recieved responses in One Line of Code
  - Fluent Facade Design Pattern for abstracting/hiding unnecessary requests and encapsulate them into high level steps, thus making the script more short and readable for anyone
- Execute the same Request several times (like Create Multiple Notes) in one line of code with help of Fluent Facade Design	
- Using APIManager Util that provide abstracted methods for sending all kind of requests and for different manipulations on API response
- Allure Report for the Following:
  - Reporting Test Result, and Logging all Test Steps and Validations
  - Screenshots for all sent requests and responses
  - In Case of Facade Design, Logging the Main Encapsulated Steps suitable for Stakeholders, then expand each step to check the detailed requests inside if needed
- Execute Tests from CI/CD Pipeline with GitHub Actions

## Application Under Test
- Notes API https://practice.expandtesting.com/notes/api/api-docs/#/