 # Meeting Notes

### 13th October 2020
#### What we did
* Created a meeting solution and discussed team synergy.
* Setup Jira for project management.
* Functional/Non-Functional system requirements.
* Added links to mattermost for other teams.
* Created meeting notes for other teams and team members.
* Questions created for Thursday.
* Created message diagram.
* Showed how numberplate recognition worked through GUI.
* Discussed JMS implementation requirements.
* Discussed testing of the solution.

#### To Do
* Special K - Continue JMS exploration and knowledge gathering.
* Matt - Will look at running the Java project on Ubuntu and how to interact through the command line with it.

#### Attending
Matt, Emma, Special K


### 15th October 2020
#### What we did
* Successfully ran javaanpr.jar from the command line.
* Reviewed JMS research and example code. https://github.com/KieronGillingham/SimpleJMSClient
* Discussed possible uses and implementations of researched technologies for the project

#### To Do
* Nothing

#### Attending
Matt, Emma, Special K


### 19th October 2020
#### What we did
* Kieron has tested using ANPR library as a dependency for another JAR project. 
* Bill of Materials
* Talked about the fact that we are two weeks in and no one seems to know what they are doing.

#### To Do
* Threads and then docker/compose
* How to shut the threads down
* Concurrent classes to avoid concurrent access exceptions
* Define JSON messages that we will be sending 
* Serialisation / Deserialisation of the image
* Maven ActiveMQ plugin-in should be used for testing
* JMX allows you to check how many things are in the queue
* Monitor the process 

#### Things to do for presentation one: 
* Deployment requirements
* Class Diagram
* Use case diagrams
* Explanation of our role in the project
* Add explanation to our project plan

#### Attending
Matt, Emma, Special K


### 29th October 2020
#### What we did
* Had a discussion about what to do next

#### To Do
* Add all requirements as issues
* Come up with a map between sprints and version numbers
* Make branch for our work
* Setup test environment on your own machine

#### Attending
Matt, Emma, Special K


### 3rd November 2020
#### What we did
* Set up sprint cards in project for an overall card of what needs to be done. To help with keeping a clear project plan
* Updated all diagrams to meet P1 new requirement to send us all the data over a JMS message instead of just ImageID
* Created a test plan so we know exactly all of the parts of the program we are testing 
* Talked about setting up a contract to agree on standards with other teams 
* Kieron went through a diagram he has created of the entire system
* Further integration tests for Java ANPR library using data streams

#### To Do
* Setup test environment on your own machine
* Finish system diagram
* Awaiting response to agreements with P1 and P3

#### Attending
Matt, Emma, Special K

### 10th November 2020
#### What we did
* Emma set up the class for the serialisation and deserialisation of the JSON object 
* Matt and Kieron working with P4 to get the automated deployment working when pushing code to github (now working) 
* Matt updated contracts and resent out to team
* Kieron has been looking at Craigs code to see how we can adapt it to use for our project 

#### Next steps
* Finding out a way to serialise and deserialise the photo 
* Awaiting response to agreements 

### 12th November 2020
#### What we did
* Kieron and Emma looking at a way to deal with the image
* Matt showed gitflow and automated deployment to group 4 
* Kieron and Emma managed to get working examples of turning a bufferedimage into a string and then back again.

#### Next steps
* Adding to the CarInfoClass to add the ability to serialise and deserialise the json object

### 13th November 2020
#### What we did
* Worked together to get the implemetation of serialisation and deserialisation to the CarInfo Class. 
* Added working tests to test the new methods created
* Renamed the CarInfo class and uploaded to github

### 17th November 2020
#### What we did 
* Matt showed the work he had being doing with team 2 in terms of pushing code to github.
* Kieron went through the jms messaging client he has been working on
* Worked on adding the ability to recieve a message and send it straight out within Kierons programme
* Changed our code within the jsonMessage class to meet the agreement of using iso-8601 java date. 
* Created tests within the application to test sending and recieving of messages. 

### 03rd December 2020
#### What we did
* Craig spoke about the assessments in the lectures.
* We looked at the display server X11 issue that Kieron was having on his local machine.
* We looked at why the platerecognition module was not building with the parent project.
* We submitted our second pull request.
* We also looked at the build issues this was not fixed we have emailed this to Craig and hope to hear from him soon.

