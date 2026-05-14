clientes-api
GitHub starsGitHub forksGitHub issuesJava (Maven)

📑 Table of Contents

Description
Features
Tech Stack
Quick Start
Key Dependencies
Screenshots
Project Structure
Development Setup
Contributing


📝 Description
A robust backend API developed with Java and Maven, specifically designed to streamline client management, payment processing, and subscription lifecycles. This project serves as a practical implementation of modern enterprise application principles, featuring a comprehensive testing suite to ensure reliability, security, and seamless integration for financial operations.

✨ Features
🌐 Api
🧪 Testing
🛠️ Tech Stack
☕ Java (Maven)
⚡ Quick Start

# Clone the repository
git clone https://github.com/Jefferson3121/clientes-api.git

# Build with Maven
mvn install
📦 Key Dependencies
spring-boot-starter-web: ${lombok.version}
mapstruct: ${mapstruct.version}
spring-boot-starter-test: 3.25.3
springdoc-openapi-starter-webmvc-ui: 2.6.0
📸 Screenshots
Tip: You can auto-generate a beautiful project mockup image using the Screenshot button above!

Main Application View

Feature Showcase

📁 Project Structure
.
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── CreatePayRequestDTO
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── ClientHub
    │   │           └── api
    │   │               ├── ClientHubApplication.java
    │   │               ├── component
    │   │               │   ├── ClientMapper.java
    │   │               │   ├── PayMapper.java
    │   │               │   ├── PlanMapper.java
    │   │               │   └── SubscriptionMapper.java
    │   │               ├── config
    │   │               │   └── OpenApiConfig.java
    │   │               ├── controller
    │   │               │   ├── ClientController.java
    │   │               │   ├── PayController.java
    │   │               │   ├── PlanController.java
    │   │               │   └── SubscriptionController.java
    │   │               ├── domain
    │   │               │   ├── entity
    │   │               │   │   ├── Customer.java
    │   │               │   │   ├── Pay.java
    │   │               │   │   ├── Plan.java
    │   │               │   │   └── Subscription.java
    │   │               │   └── enums
    │   │               │       ├── PlanDuration.java
    │   │               │       ├── State.java
    │   │               │       └── StateSubscription.java
    │   │               ├── dto
    │   │               │   ├── request
    │   │               │   │   ├── ClientRequestChangeEmailDTO.java
    │   │               │   │   ├── ClientRequestChangeNameDTO.java
    │   │               │   │   ├── ClientRequestDTO.java
    │   │               │   │   ├── CreatePayRequestDTO.java
    │   │               │   │   ├── PLanRequestDTO.java
    │   │               │   │   └── SubscriptionRequestDTO.java
    │   │               │   └── response
    │   │               │       ├── CustomerResponseDTO.java
    │   │               │       ├── PayResponseDTO.java
    │   │               │       ├── PlanResponseDTO.java
    │   │               │       ├── ResponseError.java
    │   │               │       └── SubscriptionResponseDTO.java
    │   │               ├── exception
    │   │               │   ├── ClientAlreadyExistsException.java
    │   │               │   ├── ClientHandlerException.java
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   ├── HttpHandlerException.java
    │   │               │   ├── PlanHandlerException.java
    │   │               │   ├── PlanNoFoundException.java
    │   │               │   └── UnchangedValueException.java
    │   │               ├── repository
    │   │               │   ├── CustomerRepository.java
    │   │               │   ├── PayRepository.java
    │   │               │   ├── PlanRepository.java
    │   │               │   └── SubscriptionRepository.java
    │   │               └── service
    │   │                   ├── contrat
    │   │                   │   ├── CustomerService.java
    │   │                   │   ├── PayService.java
    │   │                   │   ├── PlanService.java
    │   │                   │   └── SubscriptionService.java
    │   │                   └── impl
    │   │                       ├── customer
    │   │                       │   └── CustomerServiceImpl.java
    │   │                       ├── pay
    │   │                       │   └── PayServiceImpl.java
    │   │                       ├── plan
    │   │                       │   └── PlanServiceImpl.java
    │   │                       └── subscription
    │   │                           └── SubscriptionServiceImpl.java
    │   └── resources
    │       ├── aplication-dev.properties
    │       └── application.properties
    └── test
        └── java
            └── com
                └── ClientHub
                    └── api
                        ├── CustomerHubApplicationTests.java
                        ├── controller
                        │   ├── ClientControllerTest.java
                        │   ├── PayControllerTest.java
                        │   ├── PlanControllerTest.java
                        │   └── SubscriptionControllerTest.java
                        ├── domain
                        │   └── PayTest.java
                        └── service
                            └── impl
                                ├── CustomerServiceImplTest.java
                                ├── PayServiceImplTest.java
                                ├── PlanServiceImplTest.java
                                └── SubscriptionServiceImplTest.java


                                
🛠️ Development Setup
Java (Maven) Setup
Install Java (JDK 11+ recommended)
Install Maven
Install dependencies: mvn install
Run the project: mvn exec:java or check pom.xml for specific run commands
👥 Contributing
Contributions are welcome! Here's how you can help:

Fork the repository
Clone your fork: git clone https://github.com/Jefferson3121/clientes-api.git
Create a new branch: git checkout -b feature/your-feature
Commit your changes: git commit -am 'Add some feature'
Push to your branch: git push origin feature/your-feature
Open a pull request
Please ensure your code follows the project's style guidelines and includes tests where applicable.
