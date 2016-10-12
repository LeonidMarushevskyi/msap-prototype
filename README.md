# Mississippi Vendor Challenge

# Table of Contents
1. [Main Links](#main-links)
2. [User guide](#user-guide)
3. [Project Description](#project-description)
4. [Installation Guide](#installation-guide)
5. [List of artifacts used to create the prototype](#list-of-artifacts-used-to-create-the-prototype)
6. [Team Structure](#team-structure)
7. [U.S. Digital Services Playbook checklist](#us-digital-services-playbook-checklist)


## Main Links
**Prototype URL:** [MSAP Parent Portal - www.msap.engagepoint.com](http://www.msap.engagepoint.com)

**Vendor Challenge Documentation** is included in Git repository in HTML format in /documents folder. Please use /documents/index.html as entry point. This documentation is referenced from documentation below.  

[Continuous Delivery](http://jenkins.msap.engagepoint.com:8888/view/All/)</br>
[Monitoring](http://zabbix.msap.engagepoint.com:10080/zabbix/charts.php?sid=283d739fb5f810b5&form_refresh=1&fullscreen=0&groupid=10&hostid=0&graphid=955)</br>
[Code Quality](http://jenkins.msap.engagepoint.com:8080/overview?id=com.engagepoint.msap%3Amsap-prototype)</br>

For questions and comments regarding the installation, the documentation and the prototype, please contact: msap@engagepoint.com

## User guide
Application provide ability for anonymous users to search for Child Care Providers in state of Mississipi.

Application as well provides several roles with different capabilities: PARENT, FOSTER_PARENT and CASE_WORKER.

After registration and activation of account using email, user can receive role PARENT or FOSTER_PARENT depends on registration data.

To login without registration please use demo users below:

Demo user with role PARENT:

    name: parent, password: parent
Demo user with role FOSTER_PARENT:

    name: fosterparent, password: parent
Demo uses with role CASE_WORKER:

    name: worker, password: worker

## Project Description

EngagePoint implemented a modern, mobile-friendly, cloud-ready web application in only three weeks:

1. Performed 1:1 interviews and documentted results
2. Developed an interactive wireframe and performed usability testing with users
3. Developed user interface mock-ups using  [U.S. Web Design Standards](https://standards.usa.gov/) in compliance with [ADA508](https://www.section508.gov/)
4. Generated a generic web application using JHipster with an appropriate technology stack
5. Configured continuous integration and automated testing using [Jenkins](https://jenkins.io/)
6. Designed a data model and generated code artifacts using JHipster's entity generator
7. Developed custom user interfaces according to design mockups and integrated them into a generic application
8. Modified the front-end and back-end code to support prototype functionality

Each development stage included automated tests (and performance and acceptance tests) for Java and JavaScript code. [SonarQube](http://www.sonarqube.org/) controlled code quality, incorporated in an automated continuous delivery workflow implemented in [Jenkins](https://jenkins.io/). We also manually reviewed code to ensure quality.

## Installation Guide
To install and run the prototype on another machine, we provide several available options such as a Docker-based deployment and a compilation of the prototype from source code.

### Start application using Docker (applicable for production environments)

We have published a Docker image with the application prototype to the Docker Hub. To run the Docker image on another machine, complete the following steps: 

1. Install [Docker Toolbox](https://www.docker.com/products/docker-toolbox) on your machine
2. Start Docker Quickstart Terminal or use graphical tool Kitematic(Alpha)
3. Download docker compose file for standlone (  [msap-prototype-full.yml](https://github.com/engagepoint/msap-config/blob/master/msap-prototype-full.yml) ) or high availability ( [msap-prototype-ha.yml](https://github.com/engagepoint/msap-config/blob/master/msap-prototype-ha.yml)) solution.
4. Change spring_mail_host parameter in yml file to your e-mail server. Without email server, the application will not be able to send account activation emails so user registration will be not functional. In order to test the application capabilities without email server shall use system users.
Parent: username: parent, password: parent
Foster Parent: username: fosterparent, password: parent
Role Case Worker: username: worker, password: worker
5. In Docker Terminal run the following command line: ```docker-compose f <full-path-to-file>/msap-prototype-<ha or full>.yml up -d```
Please wait 1 to 5 minutes for application start depends on network connection.
6. If you have Windows environment, you can open Kitematic (Alpha) and click on Web Preview to open application in the browser. In the Linux, application will be available by default URL [http://127.0.0.1:8080/#/](http://127.0.0.1:8080/#/) (for Full) or [http://127.0.0.1:24080](http://127.0.0.1:24080) (for HA)
7. For high availability (HA) configuration (msap-prototype-ha.yml) you can scale application. For this in Docker terminal run command line: 
```docker-compose scale msap-prototype-ha=n```
, where n is desired quantity of Application's containers.

### Compile application from source code and start (any operation system, development environment)
To compile the application from source code, you will need to setup and configure a development environment using the steps below:

1. Install Java Development Kit (JDK) version 8 from  [the Oracle website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
2. Install Java dependency management tool Maven from official  [Maven website](http://maven.apache.org/)
3. Install Git from  [git-scm.com](https://git-scm.com/downloads)
4. Use the project git repository with source code submitted by EngagePoint as a part of Vendor Challenge response<
5. Install Node.js from  [the Node.js website](http://nodejs.org/). This will also install npm, which is the node package manager we are using in the next commands.
6. Navigate to the Git repository folder msap-prototype
7. Install Yeoman using the command line: ```npm install -g yo```
8. Install Bower using the command line: ```npm install -g bower```
9. Install  [Grunt](http://gruntjs.com/) using the command line: ```npm install -g grunt-cli```
10. Install JHipster using the command line: ```npm install -g generator-jhipster```

When the development environment is configured, you can compile and run the prototype application by following the steps below:

1. From Git repository folder msap-prototype, run Maven command: ```mvn spring-boot:run```
2. The application will be automatically started on  [http://127.0.0.1:8080/#/](http://127.0.0.1:8080/#/) in 2-10 minutes dependse on performance of your machine

## List of artifacts used to create the prototype

### 1. Research findings
During our application prototype development, we used the following "human-centered design" techniques and tools:<br/>
* **User Interviews** (please refer to UX Design Process/01. Interviews from Vendor Challenge Documentation)
* **Expert Interview** (please refer to UX Design Process/01. Interviews/02. Expert Interview - Shelly Kihnke 2016-09-28 from Vendor Challenge Documentation)
* **User Needs** (please refer to UX Design Process/02. User Needs from Vendor Challenge Documentation)

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

The dataset used in our solution includes the State’s identified data. However, based on our meeting with State representatives on September 9, we have augmented the data with additional elements. See the UX Design Process/08. Decisions made document for more details on these decisions. 

### 2. User stories

To implement the prototype, we modified our Scrum methodology to align with the user-centric design. Our approach is detailed in US Digital Services Playbook Play 4 as well as in Agile/Agile Approach Overview document from Vendor Challenge Documentation.

After the initial gathering of information regarding current functionality and user needs, the EngagePoint team used a variety of techniques to clarify the requirements within the human-centric design. These include the following: 
* **User Personas** (please refer to UX Design Process/03. User Personas from Vendor Challenge Documentation)
* **User Stories & Scenarios** (please refer to UX Design Process/04. User Stories & Scenarios from Vendor Challenge Documentation)

User Personas and User Stories and Scenarios provide points of clarification in interviews with targeted users and support the design and development effort.

### 3. UX/UI Design

* **Wireframing** (please refer to UX Design Process/05. List of Axure wireframe versions from Vendor Challenge Documentation)
* **Usability Testing** (please refer to UX Design Process/06. Usability testing from Vendor Challenge Documentation)
* **Design mockups** (please refer to UX Design Process/07. Design Concepts and Mockups from Vendor Challenge Documentation)
 
For our team, interactive wireframes play an important part in the human-centric design approach. Wireframes provide the ability to quickly prototype and validate design ideas with users. The interactive wireframe is also a useful input for the application development team. Properly organized design stage activities minimize the need to rework the actual application in later stages, where the cost of changes could be much more expensive. 
 
The UX Design Process establishes a visual metaphor to support the steps in the process and orient users to where they are in the actions to be performed. The process also establishes important language consistencies and design consistencies that support the service. Our style guide integrates with the current Mississippi style guide. Our usability testing process validates if we have accomplished the intended goals (or provides a correction, if there is a missed alignment).
 
The EngagePoint product design incorporates support for tablet and smartphone usage. Providing these options in working with the application supports the various devices parents and caseworkers use in their daily lives. Additional information regarding the EngagePoint Design process can be found within the **UX Design Process** (please refer to UX Design Process document from Vendor Challenge Documentation).
 
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

![Architectural diagram](https://github.com/engagepoint/msap-config/blob/master/4620408.png?raw=true)

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

<td><a href="https://www.linkedin.com/in/marushevskiy-leonid-57a733a">Leonid Marushevskyi</a></td>

<td>
<ul>
<li>Delivering projects and products using the appropriate agile project management methodology, learning & iterating frequently</li>
<li>Leading the collaborative, dynamic planning process – prioritizing the work that needs to be done against the capacity and capability of the team</li>
<li>Ensuring that product is built to an appropriate level of quality for the stage (alpha/beta/production)</li>
<li>Actively and openly sharing knowledge of best practices</li>
<li>Architecting the overall system, by using prototyping and proof of concepts, defining development technologies and solution components</li>
<li>Ensuring strategic alignment of technical design and architecture to meet business and user needs, and stay on top of emerging technologies</li>
<li>Working with the Product Manager to define the roadmap for product and translating this into user stories</li>
<li>Developing product roadmaps, backlogs, and measurable success criteria</li>
<li>Clearly communicates and works with stakeholders at every level</li>

</ul>

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">2.</td>

<td>Product Manager</td>

<td>Category 2</td>

<td><a href="https://www.linkedin.com/in/margreta-silverstone-a571709">Margreta Silverstone</a></td>

<td>
<ul>
<li>Leads one or more multi-disciplinary agile delivery teams to deliver excellent new products and/or iterations to existing products to meet user needs</li>
<li>Gathers user requirements based on a communicated understanding of diverse audience groups</li>
<li>Defines and gets stakeholder buy-in for product definition and delivery approach</li>
<li>Creates effective, prioritized product descriptions, and delivery plans to meet user needs in a cost-effective way</li>
<li>Interprets user research in order to make the correct product decisions</li>
<li>Continually keep abreast of changes to user habits, preferences, and behaviors across various digital platforms and their implications for successful delivery of software product</li>
<li>Underpins the delivery and iteration of digital services through effective analysis of qualitative and quantitative user data</li>
<li>Communicates credibly with a wide range of digital delivery disciplines and talent</li>
</ul>
</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">3.</td>

<td>Business Analyst/User Researcher / Usability Tester</td>

<td>Category 4 / Category 12</td>

<td><a href="https://www.linkedin.com/in/pavel-khozhainov-4562509">Pavel Khozhainov</a></td>

<td>
<ul>
<li>Works closely with the Product Manager to define a product approach to meet the specified user need</li>
<li>Analyzes and maps the risks of this product approach and propose mitigation solutions</li>
<li>Defines how the predicted user and financial benefit can be realized, and how channel shifts will be measured</li>
<li>Makes recommendations for action against the analysis done</li>
<li>Conduct stakeholder interviews, user requirements analysis, task analysis, conceptual modeling, information architecture, interaction design, and usability testing</li>
<li>Produces user requirements specifications & experience goals, personas, storyboards, scenarios, flowcharts, design prototypes, and design specifications</li>
<li>Researches user needs as well as potential system enhancements</li>
<li>Planning, recruiting, and facilitating the usability testing of a system</li>
<li>Lead participatory and iterative design activities, including observational studies, customer interviews, usability testing, and other forms of requirements discovery</li>
<li>May create such artifacts as Usability Testing Plan, Testing Scripts, and Usability Testing Report</li>
</ul>

</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">4.</td>

<td>DevOps Engineer</td>

<td>Category 7</td>

<td><a href="https://www.linkedin.com/in/oleksandr-kuznetsov-69866171/en">Oleksander Kuznetsov</a></td>

<td>
<ul>
<li>Deploying and configuring services using infrastructure as a service providers (e.g., Amazon Web Services, Microsoft Azure, Google Compute Engine, RackSpace/OpenStack)</li>
<li>Configuring and managing Linux-based servers to serve a dynamic website</li>
<li>Debugging cluster-based computing architectures</li>
<li>Using scripting or basic programming skills to solve problems</li>
<li>Installing and managing the use of open source monitoring tools</li>
<li>Installing and monitoring configuration management tools (e.g., Puppet, Chef, Ansible, Salt)</li>
<li>Recommending and implementing Architecture for continuous integration and deployment, and continuous monitoring</li>
<li>Supports Containerization technologies (e.g., LXC, Docker, Rocket)</li>
</ul>
</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">5.</td>

<td>Visual Designer/Interaction Designer</td>

<td>Category 11/Category 12</td>

<td><a href="https://www.linkedin.com/in/dariia-iarmuratii-b3640b39/en">Dariia Iarmuratii</a>

</td>

<td>
<ul>
<li>Oversees all visual design efforts</li>
<li>Defines, creates, communicates, and manages required project documentation such as style guides and provides updates as necessary</li>
<li>Designs and specifies user interfaces and information architecture</li>
<li>Effectively communicates research findings, conceptual ideas, detailed design, and design rationale and goals both verbally and visually</li>
<li>Plans and facilitates collaborative critiques and analysis & synthesis working sessions</li>
<li>Works closely with visual designers and development teams to ensure that customer goals are met and design specifications are delivered upon</li>
<li>Designs and develops primarily internet/web pages and applications</li>
<li>Develops proof-of-concepts and prototypes of easy-to-navigate user interfaces (UIs) that consists of web pages with graphics, icons, and color schemes that are visually appealing</li>
<li>Has familiarity to, or may actually: code, test, debug documents, and implement web applications using a variety of platforms</li>
<li>Analyzing and synthesizing the results of usability testing in order to provide recommendations for change to a system</li>
</ul>
</td>

</tr>

<tr>

<td colspan="1">6.</td>

<td colspan="1">Writer / Content Designer / Content Strategist</td>

<td colspan="1">Category 13</td>

<td colspan="1"><a href="https://www.linkedin.com/in/jonathanp8888">Jonathan Parker</a></td>

<td colspan="1">
<ul>
<li>Assign, edit, and produce content for products, services, and various projects</li>
<li>Collaborate closely with developers and designers to create, test, and deploy effective content marketing experiences using the Agile method of software development</li>
<li>Offer educated recommendations on how to deliver a consistent, sustainable and standards-driven execution of content strategy across products, services, and projects</li>
<li>Participate, as needed, on an Agile software development scrum teams</li>
</ul>
</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">7.</td>

<td rowspan="5" ><span>Frontend Web Developer/</span><span>Backend Web Developer</span></td>

<td rowspan="5"><span>Category 9 /</span> Category 10  

</td>

<td><a href="https://ua.linkedin.com/in/alexander-sankin-99023458">Alexander Sankin</a></td>

<td rowspan="5">
<ul>
<li>Frontend web development using modern techniques and frameworks (HTML5, CSS3, CSS frameworks like LESS and SASS, Responsive Design, Bourbon, Twitter Bootstrap)</li>
<li>JavaScript development using modern standards, including strict mode compliance, modularization techniques and tools, and frameworks and libraries (e.g., jQuery, MV* frameworks such as Backbone.js and Ember.js, D3, <span>AngularJS</span>)</li>
<li>Creates web development using open-source web programming languages Java, JavaScript and frameworks AngularJS</li>
<li>Develops and consumes web-based, RESTful APIs</li>
<li>Uses Scalable search technology (ElasticSearch)</li>
<li>Handling large data sets and scaling their handling and storage</li>
<li>Uses and works with open source solutions and community</li>
<li>Uses and works in team environments that use agile methodologies (Scrum)</li>
<li>Uses Test-driven development</li>
<li>Uses version control systems, specifically Git and GitHub</li>
<li>Creates and deploys relational and non-relational database systems</li>
<li>Communicates technical concepts to a non-technical audience</li>
<li>Ensures Section 508 Compliance</li>
<li>Creates web layouts from static images</li>
</ul>
</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">8.</td>

<td colspan="1"><a href="https://www.linkedin.com/in/serge-redchuk-22b13518">Serge Redchuk</a</td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">9.</td>

<td colspan="1"><a href="https://ua.linkedin.com/in/oleg-korniichuk-3257302">Oleg Korniichuk</a></td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">10.</td>

<td colspan="1"><a href="https://www.linkedin.com/in/alexander-serbin-7112622">Alexander Serbin</a></td>

</tr>

<tr>

<td style="text-align: center;" colspan="1">11.</td>

<td colspan="1"><a href="https://www.linkedin.com/in/aleksandr-nikitin-2234a22a">Oleksandr Nikitin</a></td>

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

## U.S. Digital Services Playbook checklist

<div>

<div>

<table >

<tbody>

<tr>

<td> 

Play Checklist and Key Questions

</td>

<td>

Response / Artifacts

</td>

</tr>

<tr>

<td> 

<div >

## PLAY 1 Understand what people need

### Checklist

1.  Early in the project, spend time with current and prospective users of the service
2.  Use a range of qualitative and quantitative research methods to determine people’s goals, needs, and behaviors; be thoughtful about the time spent
3.  Test prototypes of solutions with real people, in the field if possible
4.  Document the findings about user goals, needs, behaviors, and preferences
5.  Share findings with the team and agency leadership
6.  Create a prioritized list of tasks the user is trying to accomplish, also known as "user stories"
7.  As the digital service is being built, regularly test it with potential users to ensure it meets people’s needs

### Key Questions

1.  Who are your primary users?
2.  What user needs will this service address?
3.  Why does the user want or need this service?
4.  Which people will have the most difficulty with the service?
5.  Which research methods were used?
6.  What were the key findings?
7.  How were the findings documented? Where can future team members access the documentation?
8.  How often are you testing with real people?

</div>

</td>

<td >

### <span style="color: rgb(0,0,0);">Checklist</span>

1.  [01\. Interviews](01.-Interviews_63698605.html)
2.  [03\. User Personas](03.-User-Personas_63698608.html) [04\. User Stories & Scenarios](63698630.html)
3.  [06\. Usability testing](06.-Usability-testing_64585755.html)
4.  [02\. User Needs](02.-User-Needs_63698609.html)
5.  [UX Design Process](UX-Design-Process_63698584.html)
6.  [04\. User Stories & Scenarios](63698630.html)
7.  [01\. Interviews](01.-Interviews_63698605.html)

### <span style="color: rgb(0,0,0);">Key Questions</span>

1.  Parents, Foster parents, Caseworkers, Providers
2.  To find the optimal Child Care facility using complex criteria. To communicate with provider and caseworker about facility found.
3.  Existing public facility database have limited functionality and do not allow to locate facility on the map.
4.  Parents (literacy challenges, Spanish-speaking only)
5.  User interviews, Surveys, Personas, Scenarios for usability testing
6.  User expectation for optimal complex search, ability to compare facilities. Communication need.
7.  All user meetings are stored in both mp4 and text form and all are in open access for the team. Usability test results are converted to issues in JIRA.
8.  We test Axure prototype/ latest java build versions with local users on a daily basis. We test it with SME on a weekly basis.

</td>

</tr>

<tr>

<td >

<div >

## PLAY 2 Address the whole experience, from start to finish

### Checklist

1.  Understand the different points at which people will interact with the service – both online and in person
2.  Identify pain points in the current way users interact with the service, and prioritize these according to user needs
3.  Design the digital parts of the service so that they are integrated with the offline touch points people use to interact with the service
4.  Develop metrics that will measure how well the service is meeting user needs at each step of the service

### Key Questions

1.  What are the different ways (both online and offline) that people currently accomplish the task the digital service is designed to help with?
2.  Where are user pain points in the current way people accomplish the task?
3.  Where does this specific project fit into the larger way people currently obtain the service being offered?
4.  What metrics will best indicate how well the service is working for its users?

</div>

</td>

<td>

### Checklist

1.  <span style="color: rgb(0,51,102);">For anticipated online interaction, see [04\. User Stories & Scenarios](63698630.html). For in person, we anticipate that users discover the service through their caseworker or from another parent (the website link would be shared). A caseworker can use the facility search while interacting with a parent regarding placement for their child.  
    </span>
2.  Please see the discussion of User Needs, Pains, and Gains as described in [04\. User Stories & Scenarios](63698630.html)
3.  [UX Design Process](UX-Design-Process_63698584.html)
4.  [06\. Usability testing](06.-Usability-testing_64585755.html)

### Key Questions

1.  <span style="color: rgb(0,51,102);">Users communicate by phone with the provider and caseworker. Some users may know about the online facility database where they can find basic information (information do not contain useful details needed to made selection decision without direct phone call). Some may use the public email to send messages. There is no current support for secured communication between parents and providers or caseworkers.</span>
2.  <span style="color: rgb(0,51,102);"><span style="color: rgb(0,51,102);"><span style="color: rgb(0,51,102);"><span style="color: rgb(0,51,102);">There is no way to find a facility based on distance from a preferred address.</span> </span>Same for specific hours and special needs support. Pain points highlight the need for a secure way to communicate with caseworkers. There is no single place to store communication with caseworkers in a secure manner.</span></span>
3.  For the parents, it is a new service, available 24 hours a day, 7 days a week, to use to find optimal facility (even in anonymous mode) and communicate securely with the caseworker. For the State caseworker, this new facility browse application and communication channel can be a part of a modernization project - parents portal module. This module can communicate with the caseworker's own message system. The secured message center and map visualization components can be reused in other modules.<span style="color: rgb(0,51,102);">  
    </span>
4.  We have goal based metrics - for each basic scenario (use case) we receive a clear Boolean answer indicating whether the user was successful in goal achievement. Depending on deployment location for the full prototype, Google Analytics can also be installed to monitor usage patterns.  
    <span style="color: rgb(128,0,0);">  
    </span>

</td>

</tr>

<tr>

<td>

<div >

## <span style="color: rgb(0,0,0);">PLAY 3</span> Make it simple and intuitive

### Checklist

1.  <span style="color: rgb(0,0,0);">Use a simple and flexible design style guide for the service. Use the [<span style="color: rgb(0,0,0);">U.S. Web Design Standards</span>](https://playbook.cio.gov/designstandards) as a default</span>
2.  Use the design style guide consistently for related digital services
3.  Give users clear information about where they are in each step of the process
4.  Follow accessibility best practices to ensure all people can use the service
5.  Provide users with a way to exit and return later to complete the process
6.  Use language that is familiar to the user and easy to understand
7.  Use language and design consistently throughout the service, including online and offline touch points

### Key Questions

1.  What primary tasks are the user trying to accomplish?
2.  Is the language as plain and universal as possible?
3.  What languages is your service offered in?
4.  If a user needs help while using the service, how do they go about getting it?
5.  How does the service’s design visually relate to other government services?

</div>

</td>

<td>

### Checklist

1.  <span style="color: rgb(0,51,102);"></span><span><span style="color: rgb(0,51,102);">[https://playbook.cio.gov/designstandards](https://playbook.cio.gov/designstandards)</span></span>
2.  [07\. Design Concepts and Mockups](07.-Design-Concepts-and-Mockups_63698603.html)
3.  [07\. Design Concepts and Mockups](07.-Design-Concepts-and-Mockups_63698603.html)
4.  [07\. Design Concepts and Mockups](07.-Design-Concepts-and-Mockups_63698603.html)
5.  [07\. Design Concepts and Mockups](07.-Design-Concepts-and-Mockups_63698603.html)
6.  [08\. Decisions made](08.-Decisions-made_63698623.html)
7.  [Unclear message texts need to be verified and fixed](Unclear-message-texts-need-to-be-verified-and-fixed_64586312.html)

### <span>Key Questions</span>

1.  <span><span style="color: rgb(0,51,102);">The primary tasks are: 1\. Find the Facility using complex criteria. 2\. Communicate with</span></span> <span style="color: rgb(0,51,102);">provider and caseworker about facility.</span><span style="color: rgb(0,51,102);"></span>
2.  <span style="color: rgb(0,51,102);">Given the target audience, the solution uses plain language (simple grammar)  
    </span>
3.  <span style="color: rgb(0,51,102);">Our service is currently offered in English, Spanish and <span style="color: rgb(0,51,102);">Vietnamese</span>. Translation is not perfect for last two. Other languages are in the product backlog.</span>
4.  The product backlog includes support for a generic help icon. Product usability testing included testing for the alignment of the task to the design. We also have an orientation video posted to a YouTube channel on the main page that helps the user to understand how the application works.<span style="color: rgb(0,51,102);">  
    </span>
5.  <span style="color: rgb(0,51,102);">The US Web Design Standards have been followed</span><span><span style="color: rgb(0,51,102);">.</span></span>

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 4</span> Build the service using agile and iterative practices

### Checklist

1.  Ship a functioning “minimum viable product” (MVP) that solves a core user need as soon as possible, no longer than three months from the beginning of the project, using a “beta” or “test” period if needed
2.  Run usability tests frequently to see how well the service works and identify improvements that should be made
3.  <span style="color: rgb(0,0,0);">Ensure the individuals building the service communicate closely using techniques such as launch meetings, war rooms, daily standups, and team chat tools</span>
4.  <span style="color: rgb(0,0,0);">Keep delivery teams small and focused; limit organizational layers that separate these teams from the business owners</span>
5.  Release features and improvements multiple times each month
6.  <span style="color: rgb(0,0,0);">Create a prioritized list of features and bugs, also known as the “feature backlog” and “bug backlog”</span>
7.  <span style="color: rgb(0,0,0);">Use a source code version control system</span>
8.  <span style="color: rgb(0,0,0);">Give the entire project team access to the issue tracker and version control system</span>
9.  <span style="color: rgb(0,0,0);">Use code reviews to ensure quality</span>

### Key Questions

1.  How long did it take to ship the MVP? If it hasn't shipped yet, when will it?
2.  How long does it take for a production deployment?
3.  How many days or weeks are in each iteration/sprint?
4.  Which version control system is being used?
5.  How are bugs tracked and tickets issued? What tool is used?
6.  How is the feature backlog managed? What tool is used?
7.  How often do you review and reprioritize the feature and bug backlog?
8.  How do you collect user feedback during development? How is that feedback used to improve the service?
9.  At each stage of usability testing, which gaps were identified in addressing user needs?

</div>

</td>

<td colspan="1" >

### Checklist

1.  MVP of application prototype version 0.1 was shipped after Sprint 1 on October 2\. So it took 7 days.  

2.  We used interactive Axure wireframes for regular usability testing with users in the early stage of development. Based on feedback obtained through usability testing, we quickly incorporated changes to the wireframe over the course of a two-day development cycle for the Design Group.  

    When MVP became available after Sprint 1, we switched usability testing to the actual application prototype.  

3.  EngagePoint realized the importance of effective communication within the Scrum team. We used daily standups to share progress reports and to resolve any blockers and stoppers. We used [Skype](https://www.skype.com) for instant communication between team members as well as group chats for different activities like Development, Deployment, and QA. In order to conduct Design Sessions with the ability to share screens and draw on the whiteboard, we have used [G](https://www.webex.com/)[oToMeeting. Our Scrum team worked in one open space, so free form communication between team members occurred continuously. Atlassian JIRA was used for issue related communications. Documentation for the project was developed in Atlassian Confluence tool.  

    ](https://www.gotomeeting.com/)
4.  Team structure is described in [U.S. Digital Services Playbook checklist](U.S.-Digital-Services-Playbook-checklist_63700736.html).  

5.  We used one-week Sprints over three weeks and delivered three versions of the application prototype. We realized that a short development cycle sped-up development and provided more control over development for the Product Manager.  

6.  We used AttlasianJIRA to manage our Agile board and Product backlog.  

7.  We used local GitLab repository as a main version control system for source code, then moved source code to a public GitHub repository.  

8.  The entire team had access to both JIRA and GitLab environments.  

9.  We believe that automated tools must be used heavily for code review. This minimizes efforts otherwise required for manual review. Automated code review was managed using SonarCube, which controlled the quality of the source code, caught simple coding bugs, validated code duplication, code coverage, etc.  

    In addition to automated code review, we practiced cross-team review. Two of most experienced team members reviewed all commits to the repository and provided comments on implementation.

### Key Questions

See [U.S. Digital Services Playbook checklist](U.S.-Digital-Services-Playbook-checklist_63700736.html).

1.  Seven days  

2.  _Not Applicable_  

3.  Five-day sprints  

4.  Git  

5.  Atlassian JIRA  

6.  Atlassian JIRA for Agile board  

7.  Once a week during sprint planning  

8.  We used Axure wireframe for initial prototyping, usability testing, collecting initial feedback.  

9.  During usability testing, we have identified following gaps:

1.  the user wants to see the number of facilities which are available for current search criteria
2.  the user wants to see distance from his location to facilities on the map and sort list of facilities by distance
3.  <span>the user wants to be aware that some filters for facilities are selected</span>
4.  <span><span>the user wants to be able to quickly ask about a facility</span></span>
5.  <span><span>the user wants to see contacts in a private inbox and be able to quickly compose a message to this contact</span></span>
6.  <span><span><span>the user wants to be able to export messages in PDF format</span></span></span>

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 5</span> Structure budgets and contracts to support delivery

### Checklist

1.  Budget includes research, discovery, and prototyping activities
2.  Contract is structured to request frequent deliverables, not multi-month milestones
3.  Contract is structured to hold vendors accountable to deliverables
4.  Contract gives the government delivery team enough flexibility to adjust feature prioritization and delivery schedule as the project evolves
5.  Contract ensures open source solutions are evaluated when technology choices are made
6.  Contract specifies that software and data generated by third parties remains under our control, and can be reused and released to the public as appropriate and in accordance with the law
7.  Contract allows us to use tools, services, and hosting from vendors with a variety of pricing models, including fixed fees and variable models like “pay-for-what-you-use” services
8.  Contract specifies a warranty period where defects uncovered by the public are addressed by the vendor at no additional cost to the government
9.  Contract includes a transition of services period and transition-out plan

### Key Questions

1.  What is the scope of the project? What are the key deliverables?
2.  What are the milestones? How frequent are they?
3.  What are the performance metrics defined in the contract (e.g., response time, system uptime, time period to address priority issues)?

</div>

</td>

<td colspan="1" >

### Checklist

1.  Internal budget includes allocation of staff time to do research, proof of concept and prototyping. EngagePoint uses JIRA to support the development effort, including the use of story points to assess the level of scope complexity. Overall monitoring of activity through JIRA and regular Agile approach meetings support the overall project tracking.
2.  Not Applicable (NA)
3.  NA
4.  NA
5.  NA
6.  NA
7.  NA
8.  NA
9.  NA

### Key Questions

1.  See the [Project Scope](Project-Scope_63698597.html)
2.  [Agile](Agile-Approach-Overview_63698611.html) provides regular check points on activities
3.  Performance metrics are currently tied to the [success criteria](Success-criteria_63698669.html).

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 6</span> Assign one leader and hold that person accountable [U.S. Digital Services Playbook checklist](U.S.-Digital-Services-Playbook-checklist_63700736.html)

### Checklist

1.  A product owner has been identified
2.  All stakeholders agree that the product owner has the authority to assign tasks and make decisions about features and technical implementation details
3.  The product owner has a product management background with technical experience to assess alternatives and weigh tradeoffs
4.  The product owner has a work plan that includes budget estimates and identifies funding sources
5.  The product owner has a strong relationship with the contracting officer

### Key Questions

1.  Who is the product owner?
2.  What organizational changes have been made to ensure the product owner has sufficient authority over and support for the project?
3.  What does it take for the product owner to add or remove a feature from the service?

</div>

</td>

<td colspan="1" >

### Checklist

1.  Product Owner identified: Agile ADPQ Product Manager (Margreta)
2.  Project Charter identified the owner and was approved by the CEO.
3.  Product Owner has a PMP and work experience with IT projects/products.
4.  Project Charter included work plan and funding.
5.  Not Applicable

### Key Questions

1.  Margreta(Agile ADPQ Product Manager)
2.  Approval by the CEO (with other senior management support) created the organizational change needed.
3.  The Product owner participates in Design Review meetings and can add or remove features. See [U.S. Digital Services Playbook checklist](U.S.-Digital-Services-Playbook-checklist_63700736.html).

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 7</span> Bring in experienced teams

### Checklist

1.  Member(s) of the team have experience building popular, high-traffic digital services
2.  Member(s) of the team have experience designing mobile and web applications
3.  Member(s) of the team have experience using automated testing frameworks
4.  Member(s) of the team have experience with modern development and operations (DevOps) techniques like continuous integration and continuous deployment
5.  Member(s) of the team have experience securing digital services
6.  A Federal contracting officer is on the internal team if a third party will be used for development work
7.  A Federal budget officer is on the internal team or is a partner
8.  The appropriate privacy, civil liberties, and/or legal advisor for the department or agency is a partner

</div>

</td>

<td colspan="1" >

### Checklist

1.  <span style="color: rgb(0,0,0);">EngagePoint has experienced and talented engineers with broad experience in the development of complex, high-load applications within various domain areas including: state government, health care, insurance, etc.</span>
2.  EngagePoint has a highly-skilled user experience design group with extensive experience in mobile and web application design.
3.  <span style="color: rgb(0,51,102);">EngagePoint possesses over 5 years of experience in automated testing for projects. Our engineers actively use automated testing and test driven development in daily work. We are using JUnit for Java unit testing, Spring Testing Framework for integration testing, Karma JS for JavaScript unittesting, Cucumber for acceptance testing, and Gatling for performance testing</span>.
4.  EngagePoint has experienced DevOps engineers that are specialized in <span>continuous integration and continuous deployment using modern tools like Jenkins, Docker, Chef, etc.</span>
5.  EngagePoint has a Security Analyst that supervises all questions related to security. See team structure in ReadMe.md file.
6.  Not applicable for current project
7.  Not applicable for current project
8.  Not applicable for current project

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 8</span> Choose a modern technology stack

### Checklist

1.  Choose software frameworks that are commonly used by private-sector companies creating similar services
2.  Whenever possible, ensure that software can be deployed on a variety of commodity hardware types
3.  Ensure that each project has clear, understandable instructions for setting up a local development environment, and that team members can be quickly added or removed from projects
4.  [Consider open source software solutions](http://www.whitehouse.gov/sites/default/files/omb/assets/egov_docs/memotociostechnologyneutrality.pdf) at every layer of the stack

### Key Questions

1.  What is your development stack and why did you choose it?
2.  Which databases are you using and why did you choose them?
3.  How long does it take for a new team member to start developing?

</div>

</td>

<td colspan="1" >

### <span style="color: rgb(0,51,102);">Checklist</span>

1.  EngagePoint used the JVM platform for software development and Java 8 as the development language. For the implementation of the application prototype, we have used HTML5, CSS3, <span style="color: rgb(255,102,0);"><span style="color: rgb(0,51,102);">Bootstrap,</span> <span style="color: rgb(0,51,102);">and AngularJS. For back-end development, we have used Spring Boot, PostrgreSQL, Elasticsearch, Hibernate, Spring Framework, etc. All of these technologies are very popular and commonly used in product systems of different scale.</span></span>
2.  We are using Spring Boot project for our application prototype which provides a variety of deployment options out of the box, such as:
    *   Deployment to application containers like Websphere, JBoss, Weblogic, Tomcat, etc. using war file
    *   Embedded into application: package container Tomcat or Jetty. In this way, the application can be started on any environment where JRE is installed. This option simplifies deployment to cloud environments.Spring Boot provides extensive configuration capabilities, which help during automated deployment. <span style="color: rgb(0,51,102);">EngagePoint chooses to deliver software as Docker container, which enables compatibility with virtually any environment, independent of hardware types.</span>
3.  EngagePoint used the open source code generator [JHipster](https://jhipster.github.io/) which provides documentation related to [development environment installation](https://jhipster.github.io/v2-documentation/installation/) as well as other development related instructions. This allows the new <span style="color: rgb(255,102,0);"><span style="color: rgb(0,51,102);">team member to start developing quickly by reducing the necessity</span> <span style="color: rgb(0,51,102);">to learn custom code.</span></span>
4.  <span style="color: rgb(0,51,102);">Every layer of the stack consists of open source solutions.</span>

### Key Questions

<span style="color: rgb(255,102,0);"></span>

1.  EngagePoint selected the Java Virtual Machine (JVM) platform and Java 1.8 as the prototype’s programming language. The JVM platform is commonly used in high-load web applications, such as Google. However, small prototype web applications can also be created quickly and then transitioned to production usage on the same technology platform without having to entirely rewrite the application.  

    Prototype development using the JVM platform is the correct choice due to the availability of qualified developers, low development and support cost, and low risk in the short- and long- term.  
    EngagePoint used the open source code generator [JHipster](https://jhipster.github.io/), which let us generate a generic application based on [Spring Boot](http://projects.spring.io/spring-boot/), [AnglarJS](https://angularjs.org/) with incorporated minimal for production usage functionality such as user management, user roles, configuration, and monitoring.  

    Hipster provides tools that accelerate development and reduce the need for custom coding. Entity Generator supports application prototyping, allowing the Technical Architect to describe the standard Entity Relational Diagram using JDL (a domain specific language). Based on JDL, JHipster generates the boilerplate code needed for simple CRUD operations with these entities: [Liquibase](http://www.liquibase.org/) scripts for database objects, Hibernate entities, repository classes, Java REST resources, AngularJS controllers, REST client services, routers, unit tests for Java and JavaScript, and sample administrative UI.  

    The prototype has two maven profiles: DEV and PROD. The DEV profile is used on the local development environment and incorporates in-memory H2 and Elasticsearch engines. Spring Boot provides an embedded lightweight application container, Tomcat, which runs the prototype. We used [Browsersync](https://www.browsersync.io/) and [Spring Boot Devtools](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html) for automated reload of front-end and back-end code. These techniques reduce development time to seconds as the developer views updates in real time. The PROD profile builds the prototype’s production version, which is optimized for production use.  

    EngagePoint chose AngularJS for front-end development because this JavaScript MVC framework is mature and widely used. AngularJS is open source and is one of the most popular projects on GitHub. There are an enormous amount of publicly available components for AngularJs.  

    For back-end development, EngagePoint selected the Spring Boot project, which helped to implement the application based on Spring Framework faster. Spring Framework in conjunction with Spring Boot provides a solid development platform for implementation of applications with unlimited complexity and performance requirements.  

2.  EngagePoint chose to use PostgreSQL relational database as the main persistence storage. This database is the most advanced open source relational database that [provides features](https://www.postgresql.org/about/featurematrix/) comparable with proprietary databases like Oracle and DB2\. PostgreSQL is mature, stable, and widely used in enterprise solutions and web applications. PostgreSQL is firmly supported by development tools such as Liquibase, Hibernate.  

3.  The Stack of technologies chosen for application prototype contains open source technologies that are well documented and commonly used. This fact helps new team members quickly come up to speed and be productive. Entry threshold for chosen technologies is relatively low.

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 9</span> Deploy in a flexible hosting environment

### Checklist

1.  Resources are provisioned on demand
2.  Resources scale based on real-time user demand
3.  Resources are provisioned through an API
4.  Resources are available in multiple regions
5.  We only pay for resources we use
6.  Static assets are served through a content delivery network
7.  Application is hosted on commodity hardware

### Key Questions

1.  Where is your service hosted?
2.  What hardware does your service use to run?
3.  What is the demand or usage pattern for your service?
4.  What happens to your service when it experiences a surge in traffic or load?
5.  How much capacity is available in your hosting environment?
6.  How long does it take you to provision a new resource, like an application server?
7.  How have you designed your service to scale based on demand?
8.  How are you paying for your hosting infrastructure (e.g., by the minute, hourly, daily, monthly, fixed)?
9.  Is your service hosted in multiple regions, availability zones, or data centers?
10.  In the event of a catastrophic disaster to a datacenter, how long will it take to have the service operational?
11.  What would be the impact of a prolonged downtime window?
12.  What data redundancy do you have built into the system, and what would be the impact of a catastrophic data loss?
13.  How often do you need to contact a person from your hosting provider to get resources or to fix an issue?

</div>

</td>

<td colspan="1" >

### Checklist

1.  On demand provisioning implemented for 2 cases:
    1.  Docker-based environment (DEV)  
        In this case additional Application's instances can be increased or decreased using the Docker-composed `scale=n` command. New instances will be automatically added to running HAproxy instance.
    2.  Amazon ECS based environment (QA/PROD)  
        In this case additional EC2 instances can be added to the application cluster using CLI or AWS Management Console.
2.  <span>Auto Scaling Groups are used to leverage containers based on configurable monitoring parameters. Currently implemented rules configured to add 1 instance if CPU load is over 90% more than 10 minutes and remove 1 instance if CPU load below 20% the same amount of time.</span>
3.  In both cases resources provisioned by API:
    1.  [Docker](https://docs.docker.com/engine/reference/api/docker_remote_api/)
    2.  [Amazon Cloud](http://docs.aws.amazon.com/AWSEC2/latest/APIReference/Welcome.html)
4.  In case of Docker-based configuration, resources can be deployed and migrated in different Data Centers in different regions. In the case of Amazon ECS, this feature is implemented by Regions and Availability zones.
5.  In both cases it is possible to run only the amount of Docker containers or EC2 instances needed and pay only for resources used.
6.  In this Prototype there is a very low amount of static content, so this feature is not applicable.
7.  Because Docker Engine does not require any Vendor-specific OS of hardware, this Application can be hosted on any commodity hardware.

### Key Questions

1.  Amazon Web Service as legacy EC2 virtual machine with Docker Engine and cluster in Amazon Container Service.
2.  The Prototype runs on any Linux or Windows compatible hardware.
3.  Not Applicable
4.  In case of surge traffic or high load, <span>Zabbix trigger will be fired for</span> Docker based deployment. In case of Amazon ESC, additional instances will be automatically created to balance a traffic and will be automatically removed when conditions return to normal.
5.  Amazon EC2/ESC has virtually unlimited capacity.
6.  New resources like application server can be provisioned in a matter of several minutes.
7.  The Application uses Hazelcast distribution cache and Load balancers to scale service on demand.
8.  Amazon provides different payment policies like pay on demand or pay in advance.
9.  The Application is hosted in 2 Regions with 4 Availability zones.
10.  The only disaster-sensitive part of the Solution is the database. After Database recovery application, new containers can be deployed in 5-10 minutes using CLI.
11.  Not Applicable
12.  We recognize data loss can be catastrophic. In most cases, the data source used for this prototype is based upon existing data from other sources. Proper backup (including remote storage of backups) and a recovery strategy for the Database is vital need for the Solution. Multiple simple application instances can be provisioned for load balancing and redundancy
13.  No need to contact Amazon staff to get resources. Containers automatically restart in case of issues.

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 10</span> Automate testing and deployments

### Checklist

1.  Create automated tests that verify all user-facing functionality
2.  Create unit and integration tests to verify modules and components
3.  Run tests automatically as part of the build process
4.  Perform deployments automatically with deployment scripts, continuous delivery services, or similar techniques
5.  Conduct load and performance tests at regular intervals, including before public launch

### Key Questions

1.  What percentage of the code base is covered by automated tests?
2.  How long does it take to build, test, and deploy a typical bug fix?
3.  How long does it take to build, test, and deploy a new feature into production?
4.  How frequently are builds created?
5.  What test tools are used?
6.  Which deployment automation or continuous integration tools are used?
7.  What is the estimated maximum number of concurrent users who will want to use the system?
8.  How many simultaneous users could the system handle, according to the most recent capacity test?
9.  How does the service perform when you exceed the expected target usage volume? Does it degrade gracefully or catastrophically?
10.  What is your scaling strategy when demand increases suddenly?

</div>

</td>

<td colspan="1" >

### Checklist

<span style="color: rgb(255,102,0);"></span>

1.  <span style="color: rgb(0,51,102);">Our continuous integration model contains several consecutive levels of auto tests to assure high quality in the delivered software.  
    </span><span style="color: rgb(0,51,102);">Every next step is started only when a previous step is passed:</span>

    *   <span style="color: rgb(0,51,102);">Build source code</span>
    *   <span style="color: rgb(0,51,102);">Run a series of tests:</span>
        *   <span style="color: rgb(0,51,102);">Unit tests</span>
        *   <span style="color: rgb(0,51,102);">JavaScript unit tests</span>
        *   <span style="color: rgb(0,51,102);">Sonar code analyzer</span>
    *   <span style="color: rgb(0,51,102);">If all previous tests pass - software is deployed to the Development environment to:</span>
        *   <span style="color: rgb(0,51,102);">Test user-facing functionality by a series of behavior driven auto tests (Cucumber)</span>
        *   <span style="color: rgb(0,51,102);">Run performance tests based on Gatling</span>
    *   If all tests pass, software is deployed into AWS PROD environment
2.  We used the following tools for unit and integration testing:  

    *   [Junit](http://junit.org/junit4/) - Java unit tests
    *   [Spring Boot integration testing tools](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) - Java integration tests
    *   [Karma JS](https://karma-runner.github.io/0.13/index.html) - JavaScript unit tests
3.  We used Jenkins to run tests automatically
4.  We used Jenkins for continuous delivery and automated deployment
5.  We used [Gatling](http://gatling.io/#/) for load and performance testing

### Key Questions

1.  We have around 50% code coverage for back-end Java code and around 30% for front-end JavaScript
2.  It takes around 30 minutes
3.  It takes around 30 minutes
4.  Every commit to the master branch starts an automated build and deployment to the DEV environment
5.  We used [Cucumber](https://cucumber.io/), [Gatling](http://gatling.io/#/), [Karma JS](https://karma-runner.github.io/0.13/index.html), [Junit](http://junit.org/junit4/)
6.  We used [Jenkins](https://jenkins.io/) [Sonar](http://www.sonarqube.org/), Cucumber+Selenide, Gatling
7.  Not applicable for application prototype. We do not have requirements from customer.
8.  Not applicable for application prototype. We do not have requirements from customer.
9.  Not applicable for application prototype.
10.  We used a c<span>lustered environment based on EC2 Container Service (ECS).</span> For the Prototype, we created an ECS cluster based on Auto Scaling Group (ESG) of EC2 instances with Elastic Load Balancer (ELB). We can configure various rules in ESG to add and remove EC2 instances based on the current workload or defined schedule.

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 11</span> Manage security and privacy through reusable processes

### Checklist

1.  Contact the appropriate privacy or legal officer of the department or agency to determine whether a System of Records Notice (SORN), Privacy Impact Assessment, or other review should be conducted
2.  Determine, in consultation with a records officer, what data is collected and why, how it is used or shared, how it is stored and secured, and how long it is kept
3.  Determine, in consultation with a privacy specialist, whether and how users are notified about how personal information is collected and used, including whether a privacy policy is needed and where it should appear, and how users will be notified in the event of a security breach
4.  Consider whether the user should be able to access, delete, or remove their information from the service
5.  “Pre-certify” the hosting infrastructure used for the project using FedRAMP
6.  Use deployment scripts to ensure configuration of production environment remains consistent and controllable

### Key Questions

1.  Does the service collect personal information from the user? How is the user notified of this collection?
2.  Does it collect more information than necessary? Could the data be used in ways an average user wouldn't expect?
3.  How does a user access, correct, delete, or remove personal information?
4.  Will any of the personal information stored in the system be shared with other services, people, or partners?
5.  How and how often is the service tested for security vulnerabilities?
6.  How can someone from the public report a security issue?

</div>

</td>

<td colspan="1" >

### Checklist

1.  Not applicable for current project
2.  Not applicable for current project
3.  Not applicable for current project
4.  Not applicable for current project
5.  Not applicable for current project
6.  In the application prototype, we are using configuration management where configuration files for all environments are stored in source code repository, Git. During automated deployment, Jenkins pulls configurations from Git and uses it for application configuration. Git provides full control over configuration versioning.

### Key Questions

1.  Application collects following personal information from users: First Name, Last Name, email address, and case number. In the profile page, other information (optional to provide) includes phone number, home address, date of birth, gender. This information is collected in the password-protected user profile. The user provides his agreement upon providing personal information.
2.  The application collects only minimal necessary information. Data is not used in any way without explicitly informing the user.
3.  The User can access, modify, or delete personal information via the password-protected user profile.
4.  The System does not share any personal information with other services or individuals. However, users may deliberately share some personal information with a case worker in a message. The System has no control of the information user prefers to share with caseworkers in their conversations. All conversations are accessible to a given authorized user / case worker only.
5.  In the scope of prototype development, EngagePoint has used <a rel="nofollow">SonarCube</a> for static code analyses which include security vulnerabilities detection. Basic security scanning is a part of the continuous integration process. EngagePoint anticipates more complex security testing process for production systems which includes scanning using a specialised tool like [Veracode](http://www.veracode.com/).
6.  All issues including security issues can be reported to Public JIRA.

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 12</span> Use data to drive decisions (Monitoring, A/B Testing, customer satisfaction)

### Checklist

1.  Monitor system-level resource utilization in real time
2.  Monitor system performance in real-time (e.g. response time, latency, throughput, and error rates)
3.  Ensure monitoring can measure median, 95th percentile, and 98th percentile performance
4.  Create automated alerts based on this monitoring
5.  Track concurrent users in real-time, and monitor user behaviors in the aggregate to determine how well the service meets user needs
6.  Publish metrics internally
7.  Publish metrics externally
8.  Use an experimentation tool that supports multivariate testing in production

### Key Questions

1.  What are the key metrics for the service?
2.  How have these metrics performed over the life of the service?
3.  Which system monitoring tools are in place?
4.  What is the targeted average response time for your service? What percent of requests take more than 1 second, 2 seconds, 4 seconds, and 8 seconds?
5.  What is the average response time and percentile breakdown (percent of requests taking more than 1s, 2s, 4s, and 8s) for the top 10 transactions?
6.  What is the volume of each of your service’s top 10 transactions? What is the percentage of transactions started vs. completed?
7.  What is your service’s monthly uptime target?
8.  What is your service’s monthly uptime percentage, including scheduled maintenance? Excluding scheduled maintenance?
9.  How does your team receive automated alerts when incidents occur?
10.  How does your team respond to incidents? What is your post-mortem process?
11.  Which tools are in place to measure user behavior?
12.  What tools or technologies are used for A/B testing?
13.  How do you measure customer satisfaction?

</div>

</td>

<td colspan="1" >

### Checklist

1.  Continuous monitoring was implemented by the synergy of built-in AWS containers tools for hardware items and Zabbix for application-specific parameters.
2.  Real-time system performance counters export by built-in metrics agent into various monitoring systems: Zabbix, Graphite, Spark.
3.  All metrics item has count, median, 95 and 99 percent line values.
4.  This demo uses Zabbix as monitoring platform and key items have triggers that send alerts to the support team.
5.  NA
6.  Metrics are internally published on the admin portal of the Application.
7.  Metrics are externally published either individually or simultaneously to Zabbix, Graphite, Spark.
8.  NA

### Key Questions

1.  Key metrics are:
    *   JVM-based parameters
    *   HTTP request statistic
    *   REST statistic
    *   DataSource statistic
2.  Every Application's instance automatically registers in Zabbix with unique host and send metrics updates. Zabbix collects it, analyzes it, triggers alarms, etc.
3.  [Zabbix](http://www.zabbix.com/)
4.  Not Applicable
5.  Not Applicable
6.  Not Applicable
7.  Not Applicable
8.  Not Applicable
9.  Not Applicable
10.  Not Applicable
11.  Not Applicable
12.  Not Applicable
13.  User feedback and usability ratings

</td>

</tr>

<tr>

<td colspan="1" >

<div >

## <span style="color: rgb(0,0,0);">PLAY 13</span> Default to open

### Checklist

1.  Offer users a mechanism to report bugs and issues, and be responsive to these reports
2.  Provide datasets to the public, in their entirety, through bulk downloads and APIs (application programming interfaces)
3.  Ensure that data from the service is explicitly in the public domain, and that rights are waived globally via an international public domain dedication, such as the “Creative Commons Zero” waiver
4.  Catalog data in the agency’s enterprise data inventory and add any public datasets to the agency’s public data listing
5.  Ensure that we maintain the rights to all data developed by third parties in a manner that is releasable and reusable at no cost to the public
6.  Ensure that we maintain contractual rights to all custom software developed by third parties in a manner that is publishable and reusable at no cost
7.  When appropriate, create an API for third parties and internal users to interact with the service directly
8.  When appropriate, publish source code of projects or components online
9.  When appropriate, share your development process and progress publicly

### Key Questions

1.  How are you collecting user feedback for bugs and issues?
2.  If there is an API, what capabilities does it provide? Who uses it? How is it documented?
3.  If the codebase has not been released under an open source license, explain why.
4.  What components are made available to the public as open source?
5.  What datasets are made available to the public?

</div>

</td>

<td colspan="1" >

### Checklist

1.  The application includes a generic email account that can be used to communicate bugs / issues.
2.  The dataset used in this application was the dataset provided by California.
3.  Data set used was provided as part of California's Open Data.
4.  Not Applicable
5.  Not Applicable
6.  Not Applicable
7.  Not Applicable
8.  Published materials to Amazon Cloud Service.
9.  Done as per the California request.

### Key Questions

1.  We had multiple review sessions with users to obtain feedback and incorporated changes based on that feedback. The bugs and issues were fixed as soon as the QA and user team reported issues.
2.  We have not used any specific proprietary APIs. We have used open source software for all our development. Related links for information and documentation is provided as a part of our delivery.
3.  Not Applicable. We have used Open Source licenses.
4.  All components including source code, design, architecture, documentation as well as prototype application
5.  All data sets used by the application

</td>

</tr>

</tbody>

</table>

</div>


</div>
