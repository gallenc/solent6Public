# TMF API Simulator Project Archetype

This project creates an archetype for generating tmforum interface simulators

To build the archetype cd into  this folder and run maven command

```
mvn clean install
```

To run the archetype use the following command in a new folder

```
mvn org.apache.maven.plugins:maven-archetype-plugin:3.2.0:generate -DarchetypeCatalog=local
```
you will be given choice of local archetypes. choose tmforum-api-sdk-project-archetype

```
[INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
Choose archetype:
1: local -> solent.ac.uk.ood.examples:example-project-archetype (example-project-archetype)
2: local -> org.opennms.tmforum.swagger:tmf656-api-sdk-project-archetype (tmf656-api-sdk-project-archetype)
3: local -> org.opennms.tmforum.swagger:tmforum-api-sdk-project-archetype (tmforum-api-sdk-project-archetype)
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 3
```
You will then be given a set of default properties. Change these to suit your project

```
[INFO] Using property: groupId = org.opennms.tmforum.swagger
Define value for property 'artifactId': tmforum-api-sdk-project
[INFO] Using property: version = 0.0.1-SNAPSHOT
[INFO] Using property: package = org.opennms.tmforum.swagger
[INFO] Using property: tmfSpecFullName = Service Problem Management
[INFO] Using property: tmfSpecPackageName = tmf656
[INFO] Using property: tmfSpecUrlPath = /tmf-api/serviceProblemManagement/v3/
Confirm properties configuration:
groupId: org.opennms.tmforum.swagger
artifactId: tmforum-api-sdk-project
version: 0.0.1-SNAPSHOT
package: org.opennms.tmforum.swagger
tmfSpecFullName: Service Problem Management
tmfSpecPackageName: tmf656
tmfSpecUrlPath: /tmf-api/serviceProblemManagement/v3/
 Y: : y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: tmforum-api-sdk-project-archetype:0.0.1-SNAPSHOT
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: org.opennms.tmforum.swagger
[INFO] Parameter: artifactId, Value: tmforum-api-sdk-project
[INFO] Parameter: version, Value: 0.0.1-SNAPSHOT
[INFO] Parameter: package, Value: org.opennms.tmforum.swagger
[INFO] Parameter: packageInPathFormat, Value: org/opennms/tmforum/swagger
[INFO] Parameter: tmfSpecFullName, Value: Service Problem Management
[INFO] Parameter: version, Value: 0.0.1-SNAPSHOT
[INFO] Parameter: package, Value: org.opennms.tmforum.swagger
[INFO] Parameter: groupId, Value: org.opennms.tmforum.swagger
[INFO] Parameter: tmfSpecPackageName, Value: tmf656
[INFO] Parameter: tmfSpecUrlPath, Value: /tmf-api/serviceProblemManagement/v3/
[INFO] Parameter: artifactId, Value: tmforum-api-sdk-project
[INFO] Project created from Archetype in dir: C:\devel\gitrepos\tmforumWork\workup\experimental\codegen-archetype\archetype\target\tmforum-api-sdk-project
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  49.658 s
[INFO] Finished at: 2020-08-07T18:49:40+01:00
[INFO] ------------------------------------------------------------------------

```

You should now have your project generated and ready to go.

