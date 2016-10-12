# Mississippi Vendor Challenge

## Main Links
Prototype URL: [MSAP Parent Portal](http://www.msap.engagepoint.com)</br></br>
[Continuous Delivery](http://jenkins.msap.engagepoint.com:8888/view/All/)</br>
[Monitoring](http://zabbix.msap.engagepoint.com:10080/zabbix/charts.php?sid=b97ada3ab8730469&form_refresh=1&fullscreen=0&groupid=10&hostid=10142&graphid=883)</br>
[Code Quality](http://jenkins.msap.engagepoint.com:8080/overview?id=com.engagepoint.msap%3Amsap-prototype)</br>
[U.S. Digital Service Playbook compliance]()</br>

For questions and comments regarding the installation, the documentation and the prototype, please contact: msap@engagepoint.com

## User guide
Application provide ability for anonymous users to search for Child Care Providers in state of Mississipi. </br>
Application as well provides several roles with different capabilities: PARENT, FOSTER_PARENT and CASE_WORKER.</br>
After registration and activation of account using email, user can receive role PARENT or FOSTER_PARENT depends on registration data.</br></br>

To login without registration please use demo users below</br>
Demo user with role PARENT: </br>
    - name: parent, password: parent</br>
Demo user with role FOSTER_PARENT: </br>
    - name: fosterparent, password: parent</br>
Demo uses with role CASE_WORKER: </br>
    - name: worker, password: worker</br>
    


## Project Description

EngagePoint implemented a modern, mobile-friendly, cloud-ready web application in only three weeks:

1.    Performed 1:1 interviews and documentted results
2.    Developed an interactive wireframe and performed usability testing with users
3.    Developed user interface mock-ups using  [U.S. Web Design Standards](https://standards.usa.gov/) in compliance with [ADA508](https://www.section508.gov/)
4.    Generated a generic web application using JHipster with an appropriate technology stack
5.    Configured continuous integration and automated testing using [Jenkins](https://jenkins.io/)
6.    Designed a data model and generated code artifacts using JHipster's entity generator
7.    Developed custom user interfaces according to design mockups and integrated them into a generic application
8.    Modified the front-end and back-end code to support prototype functionality

Each development stage included automated tests (and performance and acceptance tests) for Java and JavaScript code. [SonarQube](http://www.sonarqube.org/) controlled code quality, incorporated in an automated continuous delivery workflow implemented in [Jenkins](https://jenkins.io/). We also manually reviewed code to ensure quality.

## Installation Guide
To install and run the prototype on another machine, we provide several available options such as a Docker-based deployment and a compilation of the prototype from source code.
### Start application using Docker (applicable for production environments)
We have published a Docker image with the application prototype to the Docker Hub. To run the Docker image on another machine, complete the following steps: </br>1. Install [Docker Toolbox](https://www.docker.com/products/docker-toolbox) on your machine</br>2. Start Docker Quickstart Terminal or use graphical tool Kitematic(Alpha)</br>3. Download docker compose file for standlone (  [msap-prototype-full.yml](https://github.com/engagepoint/msap-prototype/blob/master/src/main/docker/examples/msap-prototype-full.yml) [)](https://github.com/engagepoint/chhs-adpq/blob/master/src/main/docker/chhs-apqd-full.yml) or high availability ( [msap-prototype-ha.yml](https://github.com/engagepoint/msap-prototype/blob/master/src/main/docker/examples/msap-prototype-ha.yml)) solution.</br>4. Change spring\_mail\_host parameter in yml file to your e-mail server. Without email server, the application will not be able to send account activation emails so user registration will be not functional. In order to test the application capabilities without email server shall use system users.</br>Role Parent: username: parent, password: parent</br>Role Case Worker: username: worker, password: worker</br>Role Administrator: username: admin, password: admin</br>5. In Docker Terminal run the following command line: ```docker-compose f <full-path-to-file>/msap-prototype-<ha or full>.yml up -d```</br>Please wait 1 to 5 minutes for application start depends on network connection.</br>6. If you have Windows environment, you can open Kitematic (Alpha) and click on Web Preview to open application in the browser. In the Linux, application will be available by default URL [http://127.0.0.1:8080/#/](http://127.0.0.1:8080/#/) (for Full) or [http://127.0.0.1:24080](http://127.0.0.1:24080) (for HA)</br>7. For high availability (HA) configuration (msap-prototype-ha.yml) you can scale application. For this in Docker terminal run command line: ```docker-compose scale msap-prototype-ha=n```</br>, where n is desired quantity of Application's containers.
### Compile application from source code and start (any operation system, development environment)
To compile the application from source code, you will need to setup and configure a development environment using the steps below:</br>1. 1.Install Java Development Kit (JDK) version 8 from  [the Oracle website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).</br>2. Install Java dependency management tool Maven from official  [Maven website](http://maven.apache.org/)</br>3. Install Git from  [git-scm.com](https://git-scm.com/downloads)</br>4. Use the project git repository with source code submitted by EngagePoint as a part of Vendor Challenge response</br>5. Install Node.js from  [the Node.js website](http://nodejs.org/). This will also install npm, which is the node package manager we are using in the next commands.</br>6. Navigate to the Git repository folder msap-prototype</br>7. Install Yeoman using the command line: ```npm install -g yo```</br>8. Install Bower using the command line: ```npm install -g bower```</br>9. Install  [Grunt](http://gruntjs.com/) using the command line: ```npm install -g grunt-cli```</br>10. Install JHipster using the command line: ```npm install -g generator-jhipster```</br></br>When the development environment is configured, you can compile and run the prototype application by following the steps below:</br>1. From Git repository folder msap-prototype, run Maven command: mvn spring-boot:run</br>2. The application will be automatically started on  [http://127.0.0.1:8080/#/](http://127.0.0.1:8080/#/) in 2-10 minutes dependse on performance of your machine

## List of artifacts used to create the prototype

### 1. Research findings
During our application prototype development, we used the following "human-centered design" techniques and tools:<br/>
* [User Interviews]()
* [Expert Interview]()
* [User Needs]()

#### Initial Analysis
The current functionality available on the URL identified provides search criteria including:
* Provider Name
* Provider Type
* City
* County
* Quality Star Rating

The results of searching produces a text based list, including phone numbers, which can be sorted by the same factors.
The current data set that the State provided for this activity was limited and is included in the solution.

#### Target Scope
During the formal interviews, additional items were recommended. Our recommended scope included the following search criteria:
* Address (or address fragment)
* Provider Name
* Provider Types
* Quality Star Rating

In addition, our search includes:
* Children’s ages
* Price
* Licensed/Unlicensed
* Languages supported
* Special Needs
* Historical information (on Complaints or Allegations)

Based on the input regarding the display of the results, our results are displayed both on a map, and as a text based listing.

The dataset used in our solution includes the State’s identified data. However, based on our meeting with State representatives on September 9, we have augmented the data with additional elements. See the [08. Decisions made] (https://docs.engagepoint.us/display/MSAP/08.+Decisions+made) document for more details on these decisions. 

### 2. User stories

To implement the prototype, we modified our Scrum methodology to align with the user-centric design. Our approach is detailed in US Digital Services Playbook Play 4.

After the initial gathering of information regarding current functionality and user needs, the EngagePoint team used a variety of techniques to clarify the requirements within the human-centric design. These include the following: 
* [User Personas]()
* [User Stories & Scenarios]()

User Personas and User Stories and Scenarios provide points of clarification in interviews with targeted users and support the design and development effort.

### 3. UX/UI Design

* [Wireframing]()
* [Usability Testing]()
* [Design mockups]()
 
For our team, interactive wireframes play an important part in the human-centric design approach. Wireframes provide the ability to quickly prototype and validate design ideas with users. The interactive wireframe is also a useful input for the application development team. Properly organized design stage activities minimize the need to rework the actual application in later stages, where the cost of changes could be much more expensive. 
 
The UX Design Process establishes a visual metaphor to support the steps in the process and orient users to where they are in the actions to be performed. The process also establishes important language consistencies and design consistencies that support the service. Our style guide integrates with the current Mississippi style guide. Our usability testing process validates if we have accomplished the intended goals (or provides a correction, if there is a missed alignment).
 
The EngagePoint product design incorporates support for tablet and smartphone usage. Providing these options in working with the application supports the various devices parents and caseworkers use in their daily lives. Additional information regarding the EngagePoint Design process can be found within the [UX Design Process]().
 
EngagePoint used the [U.S. Web Design Standards](https://standards.usa.gov/) to track with industry-standard web-accessibility guidelines alongside best practices of existing style libraries and modern web design. U.S. Web Design Standards provided a guide for creating visually appealing and easy-to-use online experiences for the American people.


### 4. Frameworks and libraries used to create prototype

EngagePoint selected the Java Virtual Machine (JVM) platform and Java 1.8 as the prototype's programming language. The JVM platform is commonly used in high-load web applications, such as Google. However, small prototype web applications can be created quickly and then transitioned to production usage on the same technology platform without having to rewrite the application.

Using the JVM platform for prototype development is the correct choice due to scalability and maturity of Java as an industry standard.

We adhere to these factors to maximize performance:

- Leverage a generic application platform for prototype construction
- Use code generators rather than boilerplate code
- Minimize time for the code-compile-test-deploy cycle

To minimize risk, we considered these factors for each system architecture component:

- Maturity level
- Success stories
- Documentation quality
- Cloud readiness
- Performance metrics
- Maintenance costs

#### 4.1. Application Platform

EngagePoint used the open source code generator  [JHipster](https://jhipster.github.io/), which let us generate a production-ready application using  [Spring Boot](http://projects.spring.io/spring-boot/) and  [AnglarJS](https://angularjs.org/), which contain monitoring, logging, configuration, and user management functionality.

JHipster lets us choose technologies based on project requirements. For the prototype, EngagePoint chose this technology stack:

![Architectural diagram](/documents/Images/Architecture.png?raw=true)

EngagePoint has used only open source technologies and platforms for prototype creation. Key technologies with links to source code and license type are described below:
1. Client side:
- [Bootstrap](http://getbootstrap.com/) 3.3.5: [source code](https://github.com/twbs/bootstrap) [,  ](http://getbootstrap.com/) [MIT License](https://github.com/twbs/bootstrap/blob/master/LICENSE)
- [UI Bootstrap](http://angular-ui.github.io/bootstrap/) [:](http://getbootstrap.com/) [source code](https://github.com/angular-ui/bootstrap) [,  ](http://getbootstrap.com/) [MIT License](https://github.com/angular-ui/bootstrap/blob/master/LICENSE)
- [AngularJS](https://angularjs.org/): [source code](https://github.com/angular/angular.js), [MIT License](https://github.com/angular/angular.js/blob/master/LICENSE)
- [Leaflet](http://leafletjs.com/): [source code](https://github.com/Leaflet/Leaflet), [BSD 2-Clause License](https://github.com/Leaflet/Leaflet/blob/master/LICENSE)
- [Mapzen](https://mapzen.com/): [source code](https://github.com/mapzen/leaflet-geocoder), [MIT License](https://github.com/mapzen/leaflet-geocoder/blob/master/LICENSE)
2. Server Side:
- [Spring Boot](http://projects.spring.io/spring-boot/): [source code](https://github.com/spring-projects/spring-boot), [Apache License Version 2](https://github.com/spring-projects/spring-boot/blob/master/LICENSE.txt)
- [Spring Framework](https://projects.spring.io/spring-framework/): [source code](https://github.com/spring-projects/spring-framework), [Apache License Version 2](https://github.com/spring-projects/spring-framework/blob/183594207fbb447e1b59262b4469f2aefbb8a3ec/src/dist/license.txt)
- [Spring Security](http://projects.spring.io/spring-security/): [source code](https://github.com/spring-projects/spring-security), [Apache License Version 2](https://github.com/spring-projects/spring-security/blob/master/license.txt)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/): [source code](https://github.com/spring-projects/spring-data-jpa), [Apache License Version 2](http://docs.spring.io/spring-data/jpa/snapshot-site/license.html)
- [Spring Data Elasticsearch](http://projects.spring.io/spring-data-elasticsearch/): [source code](https://github.com/spring-projects/spring-data-elasticsearch), [Apache License Version 2](https://github.com/spring-projects/spring-data-elasticsearch/blob/master/src/main/resources/license.txt)
- [Hibernate ORM](http://hibernate.org/orm/) [:](http://hibernate.org/orm/) [source code](https://github.com/hibernate/hibernate-orm) [,](http://hibernate.org/orm/) [LGPL V2.1](http://hibernate.org/license/)
- [Elasticsearch](https://www.elastic.co/products/elasticsearch): [source code](https://github.com/elastic/elasticsearch), [Apache License Version 2](https://github.com/elastic/elasticsearch/blob/master/LICENSE.txt)
- [PostgreSQL](https://www.postgresql.org/): [source code](https://github.com/postgres/postgres), [PostgreSQL Licence](https://opensource.org/licenses/postgresql) open license similar to BSD or MIT<br/>
- [Hazelcast](http://hazelcast.org/): [source code](https://github.com/hazelcast/hazelcast), [Apache License Version 2](https://github.com/elastic/elasticsearch/blob/master/LICENSE.txt) [     ](https://github.com/spring-projects/spring-framework)

#### 4.2. Rationale

The table below lists EngagePoint's architectural design decisions and their alignment with the functional and non-functional prototype requirements.

| Application Requirements | Technologies | Motivation and rationale |
| --- | --- | --- |
| Modern Web Application | HTML5, CSS3, [AngularJS](https://angularjs.org/) | AngularJS has decent documentation and an active GitHub community with lots of open components |
| Responsive UI | [Bootstrap](http://getbootstrap.com/) | Simplifies responsive UI implementation complexity providing CSS and JS. |
| Allow parents to search for child care providers | [Leaflet](http://leafletjs.com/), [Mapzen Search](https://mapzen.com/projects/search/?lng=-76.67925&lat=39.01412&zoom=12), [Elasticsearch](https://www.elastic.co/products/elasticsearch), [PostgreSQL](https://www.postgresql.org/) | - Leaflet is the leading open-source JavaScript library for mobile-friendly interactive maps. <br/>- Mapzen Search is an open source geocoding tool used for address  lookup capabilities. <br/> - Elasticsearch implements search capabilities needed for inbox functionality like full-text search, relevancy, ranking, and fuzzy search. <br/> - We are using the relational database for the persistence of messages in the private inbox. Prototype is database agnostic. PostgreSQL is our open source choice. |
| Allow foster parents to communicate with the case worker via a private inbox | [Elasticsearch](https://www.elastic.co/products/elasticsearch), [PostgreSQL](https://www.postgresql.org/), [Websockets](https://en.wikipedia.org/wiki/WebSocket), [HibernateORM](http://hibernate.org/orm/) | - Websockets technology is used for real-time notifications about new messages. |
| Allow parents to establish and manage their profile | [JHipster](https://jhipster.github.io/) [Hazelcast](http://hazelcast.org/) | - Generic JHipster application has built-in login, registration, and user profile functionality. EngagePoint has customized the generic implementation.<br/>- Hazelcast is a distributed data grid used for distributed cache capabilities and performance improvement of the application. For the prototype, we are storing user sessions as well as L2 Hibernate cache.|
| Automated deployment to IaaS, PaaS | [Spring Boot](http://projects.spring.io/spring-boot/), [Docker](https://www.docker.com/), [Jenkins](https://jenkins.io/) | - Spring Boot provides DevOps tools like externalized configuration, monitoring, and logging. Spring Boot eliminates the need to use external application containers, simplifying cloud deployment. <br/>- Docker containers are used for all components of application infrastructure (application, Elasticsearch server, PostgreSQL server). Containerization helps with automated deployment and makes application environments agnostic. <br/>- Jenkins is an open source tool used to implement continuous integration and delivery. |

### 4.3. Development Tools

JHipster provides tools that accelerate development and minimize custom coding. Entity Generator supports application prototyping, allowing the Technical Architect to describe the Entity Relational Diagram using JDL (domain specific language). Based on JDL, JHipster generates boilerplate code for simple CRUD operations with these entities:  [Liquibase](http://www.liquibase.org/) scripts for database objects, Hibernate entities, repository classes, Java REST resources, AngularJS controllers, REST client services, routers, unit tests for Java and JavaScript, and sample administrative UI.

The prototype has two [Maven](https://maven.apache.org/) profiles: DEV and PROD. The DEV profile is used on the local development environment and incorporates in-memory H2 and Elasticsearch engines. Spring Boot provides an embedded lightweight application container, Tomcat, which runs the prototype. We used  [Browsersync](https://www.browsersync.io/) and  [Spring Boot Devtools](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html) for automated reload of front-end and back-end code. These techniques reduce development time as developers view real-time updates. The PROD profile builds the prototype's production version, which is optimized for production use.

### 4.4. Automated testing

To eliminate functional regression and ensure that the application remains in compliance with requirements, we used different levels of automated testing:

* Java unit tests using [JUnit](http://junit.org/junit4/)
* Java integration tests using [Spring Boot integration testing tools](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
* JavaScript unit tests using [Karma JS](https://karma-runner.github.io/0.13/index.html) - [Jenkins job]()
* Load testing using [Gatling](http://gatling.io/#/) - [Jenkins job]()
* Acceptance testing using BDD framework [Cucumber](https://cucumber.io/) - [Jenkins job]()


### 4.5. Continuous Integration and Deployment Approach

We used Jenkins as a continuous integration and delivery tool. Configured instance of Jenkins is available for review. Instance of Sonar with history and current project status available for review.

Continuous deployment flow is triggered on any push to develop branch in GitHub repository. Jenkins jobs workflow includes following steps:

* [Build source code](http://jenkins.msap.engagepoint.com:8888/view/All/job/MSAP_build_no_test/)
* [Build Docker conteiner](http://jenkins.msap.engagepoint.com:8888/job/MSAP_build_docker/)
* Run series of tests:
  * [Java unit tests](http://jenkins.msap.engagepoint.com:8888/job/MSAP_test_UT/)
  * [JavaScript unit tests](http://jenkins.msap.engagepoint.com:8888/job/MSAP_test_UI/)
  * [Sonar code analyze](http://jenkins.msap.engagepoint.com:8888/view/All/job/MSAP_test_sonar/)
* When all the previous tests pass, [deploy to Development environment](http://jenkins.msap.engagepoint.com:8888/job/MSAP_deploy/) in Amazon Web Service
* Run [acceptance tests](http://jenkins.msap.engagepoint.com:8888/view/All/job/MSAP_test_Cucumber/) based on Cucumber
* Run [load tests](http://jenkins.msap.engagepoint.com:8888/view/All/job/MSAP_test_perf/) based on Gatling
* When all tests pass, [deploy into high availability production environment](http://jenkins.msap.engagepoint.com:8888/view/All/job/MSAP_deploy_prod/) in Amazon Web Services

Containerization with Docker best suits application delivery to target environments. The Prototype is delivered as Docker container image. It requires PostgreSQL DB and Elasticsearch as external dependencies. The Prototype can be configured to use dependencies as external services or as linked Docker containers. Amazon cloud is the deployment target for the prototype application.

Two types of environment were implemented:

1. Traditional EC2 based environment with Linux based instance. Is used for development/test environments tests.msap.engagepoint.com

	In this case we created a Virtual Machine (VM) based on any Linux image which supports Docker engine. After creating the VM we install Docker Engine and Docker compose. The Prototype is deployed based on the Docker Compose file, which creates, configures, and runs the application itself; all necessary requirements; and the Load Balancer. We can scale the Prototype's container count, which will be handled by Load Balancer automatically.
    

2. Clustered environment based on EC2 and Auto Scaling Group (ASG). Is used for production environment www.msap.engagepoint.com

	In this case for the Prototype, we create cluster based on Auto Scaling Group (ASG) of EC2 instances with Load Balancer (LB). We can configure various rules in ASG to add and remove EC2 instances based on the current workload or defined schedule.
    
    
Continuous monitoring was implemented by the synergy of built-in AWS containers tools for hardware items and Zabbix for application specific parameters.

In addition to built-in AWS monitored items like CPU, memory, etc. following Application-specific parameters monitored by Zabbix for every container:
* JVM memory details (Heap, Non-Heap)
* Datasource connection parameters (Active, Idle connections)
* REST services statistic
* Threads statistic (Deadlock, Waiting)

## Team Structure
<div>

<div>

<table>

<tbody>

<tr>

<th>#</th>

<th>Role</th>

<th>Agile Labor Category</th>

<th><span>Name</span></th>

<th>Responsibilities</th>

</tr>

<tr>

<td style="text-align: center;" colspan="1">1.</td>

<td>Delivery Manager/Technical Architect  

Team Leader responsible for delivering the project

</td>

<td><span>Category 3 / <span>Category 5</span></span></td>

<td> [Leonid Marushevskyi](https://www.linkedin.com/in/marushevskiy-leonid-57a733a)</td>

<td>

*   Delivering projects and products using the appropriate agile project management methodology, learning & iterating frequently
*   Leading the collaborative, dynamic planning process – prioritizing the work that needs to be done against the capacity and capability of the team
*   Ensuring that product is built to an appropriate level of quality for the stage (alpha/beta/production)
*   Actively and openly sharing knowledge of best practices
*   Architecting the overall system, by using prototyping and proof of concepts, defining development technologies and solution components
*   Ensuring strategic alignment of technical design and architecture to meet business and user needs, and stay on top of emerging technologies
*   Working with the Product Manager to define the roadmap for product and translating this into user stories
*   Developing product roadmaps, backlogs, and measurable success criteria
*   Clearly communicates and works with stakeholders at every level

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">2.</td>

<td>Product Manager</td>

<td>Category 2</td>

<td>[Margreta Silverstone](https://www.linkedin.com/in/margreta-silverstone-a571709)</td>

<td>

*   Leads one or more multi-disciplinary agile delivery teams to deliver excellent new products and/or iterations to existing products to meet user needs
*   Gathers user requirements based on a communicated understanding of diverse audience groups
*   Defines and gets stakeholder buy-in for product definition and delivery approach
*   Creates effective, prioritized product descriptions, and delivery plans to meet user needs in a cost-effective way
*   Interprets user research in order to make the correct product decisions
*   Continually keep abreast of changes to user habits, preferences, and behaviors across various digital platforms and their implications for successful delivery of software product
*   Underpins the delivery and iteration of digital services through effective analysis of qualitative and quantitative user data
*   Communicates credibly with a wide range of digital delivery disciplines and talent

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">3.</td>

<td>Business Analyst/User Researcher / Usability Tester</td>

<td>Category 4 / Category 12</td>

<td>[Pavel Khozhainov](https://www.linkedin.com/in/pavel-khozhainov-4562509)</td>

<td>

*   Works closely with the Product Manager to define a product approach to meet the specified user need
*   Analyzes and maps the risks of this product approach and propose mitigation solutions
*   Defines how the predicted user and financial benefit can be realized, and how channel shifts will be measured
*   Makes recommendations for action against the analysis done.
*   Conduct stakeholder interviews, user requirements analysis, task analysis, conceptual modeling, information architecture, interaction design, and usability testing
*   Produces user requirements specifications & experience goals, personas, storyboards, scenarios, flowcharts, design prototypes, and design specifications
*   Researches user needs as well as potential system enhancements
*   Planning, recruiting, and facilitating the usability testing of a system
*   Lead participatory and iterative design activities, including observational studies, customer interviews, usability testing, and other forms of requirements discovery
*   May create such artifacts as Usability Testing Plan, Testing Scripts, and Usability Testing Report

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">4.</td>

<td>DevOps Engineer</td>

<td>Category 7</td>

<td>[Oleksander Kuznetsov](https://www.linkedin.com/in/oleksandr-kuznetsov-69866171/en)</td>

<td>

*   Deploying and configuring services using infrastructure as a service providers (e.g., Amazon Web Services, Microsoft Azure, Google Compute Engine, RackSpace/OpenStack)
*   Configuring and managing Linux-based servers to serve a dynamic website
*   Debugging cluster-based computing architectures
*   Using scripting or basic programming skills to solve problems
*   Installing and managing the use of open source monitoring tools
*   Installing and monitoring configuration management tools (e.g., Puppet, Chef, Ansible, Salt)
*   Recommending and implementing Architecture for continuous integration and deployment, and continuous monitoring
*   Supports Containerization technologies (e.g., LXC, Docker, Rocket)

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">5.</td>

<td>Visual Designer/Interaction Designer</td>

<td>Category 11/Category 12</td>

<td>

[Dariia Iarmuratii](https://www.linkedin.com/in/dariia-iarmuratii-b3640b39/en)

</td>

<td>

*   Oversees all visual design efforts
*   Defines, creates, communicates, and manages required project documentation such as style guides and provides updates as necessary
*   Designs and specifies user interfaces and information architecture
*   Effectively communicates research findings, conceptual ideas, detailed design, and design rationale and goals both verbally and visually
*   Plans and facilitates collaborative critiques and analysis & synthesis working sessions
*   Works closely with visual designers and development teams to ensure that customer goals are met and design specifications are delivered upon
*   Designs and develops primarily internet/web pages and applications
*   Develops proof-of-concepts and prototypes of easy-to-navigate user interfaces (UIs) that consists of web pages with graphics, icons, and color schemes that are visually appealing
*   Has familiarity to, or may actually: code, test, debug documents, and implement web applications using a variety of platforms
*   Analyzing and synthesizing the results of usability testing in order to provide recommendations for change to a system

</td>

</tr>

<tr>

<td colspan="1">6.</td>

<td colspan="1">Writer / Content Designer / Content Strategist</td>

<td colspan="1">Category 13</td>

<td colspan="1">[Jonathan Parker](https://www.linkedin.com/in/jonathanp8888)</td>

<td colspan="1">

*   Assign, edit, and produce content for products, services, and various projects
*   Collaborate closely with developers and designers to create, test, and deploy effective content marketing experiences using the Agile method of software development
*   Offer educated recommendations on how to deliver a consistent, sustainable and standards-driven execution of content strategy across products, services, and projects
*   Participate, as needed, on an Agile software development scrum teams

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">7.</td>

<td rowspan="5" ><span>Frontend Web Developer/</span><span>Backend Web Developer</span></td>

<td rowspan="5"><span>Category 9 /</span> Category 10  

</td>

<td>[Alexander Sankin](https://ua.linkedin.com/in/alexander-sankin-99023458)</td>

<td rowspan="5">

*   Frontend web development using modern techniques and frameworks (HTML5, CSS3, CSS frameworks like LESS and SASS, Responsive Design, Bourbon, Twitter Bootstrap)
*   JavaScript development using modern standards, including strict mode compliance, modularization techniques and tools, and frameworks and libraries (e.g., jQuery, MV* frameworks such as Backbone.js and Ember.js, D3, <span>AngularJS</span>)
*   Creates web development using open-source web programming languages Java, JavaScript and frameworks AngularJS
*   Develops and consumes web-based, RESTful APIs
*   Uses Scalable search technology (ElasticSearch)
*   Handling large data sets and scaling their handling and storage
*   Uses and works with open source solutions and community
*   Uses and works in team environments that use agile methodologies (Scrum)
*   Uses Test-driven development
*   Uses version control systems, specifically Git and GitHub
*   Creates and deploys relational and non-relational database systems
*   Communicates technical concepts to a non-technical audience
*   Ensures Section 508 Compliance
*   Creates web layouts from static images

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">8.</td>

<td colspan="1">[Serge Redchuk](https://www.linkedin.com/in/serge-redchuk-22b13518)</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">9.</td>

<td colspan="1">[Oleg Korniichuk](https://ua.linkedin.com/in/oleg-korniichuk-3257302)</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">10.</td>

<td colspan="1">[Alexander Serbin](https://www.linkedin.com/in/alexander-serbin-7112622)</td>
<td colspan="1">[Oleksandr Nikitin](https://www.linkedin.com/in/aleksandr-nikitin-2234a22a)</td>

</tr>

</tbody>

</table>

</div>

<div>

<table style="border-collapse: collapse;width: 62.0px;" border="0" cellpadding="0" cellspacing="0">

<tbody>

<tr style="height: 15.0pt;" height="20">

</tr>

</tbody>

</table>

</div>

</div>
