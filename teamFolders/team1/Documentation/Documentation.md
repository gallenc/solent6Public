## **P1 - Image Receiver (Sprint-1)**

 1. Images come through as MQTT messages
 2. Stored in a datastore converting the Binary to ASCII
 3. Store a timestamp against the image
 4. CameraID against the image
 5. Each image has its own ID
 
 

## Initial System Model


```
[Go to Starter_Model](https://docs.google.com/document/d/1euZZJ_gKrzLxBLU_O4u_rE5k5T_dUC3BwufIX9MFF9I/edit?usp=sharing)
```




## Functional Features

 - Receive image binary, timestamp and cameraID
 - Convert to ASCII
 - API to interact with database (SELECT, CREATE, DELETE)
 - Store details in database
 - Send ImageID to queue

## Non Functional Features

 - Scalability (MQTT receiver, Database API, Database)
 


## Test Requirements

 - Able to accept multiple images simultaneously 
 - Able to create MQTT messages on the camera
 - Able to receive MQTT messages on the indexer
 - Able to create records in the database through the Image Database API
 - Able to request records from the database through the Image Database API
 - Able to delete records from the database through the Image Database API
 - Able to create jobs on the queue in order to queue up conversations passing through only the imageID
 - When an Image is stored it must have the image, timestamp and cameraID
 - When an image is stored it must return the ID of the record created
 - An image should never be stored without creating a job in the queue
 - Image must be converted from binary to hex on the receiver before being stored on the database
 

## Deployment Requirements

 Commit order - When a commit is made on the master branch
 

 1. All tests should be ran
 2. If all tests pass, a new container should be built
 3. If test fail then send back an error message
 4. The built container should then be pushed to the container repository 
 5. Live server should then pull down the compiled change and update the running containers
 
 
Commit order - When a commit is made on a non master branch
 1. Run tests
 2. Notify user of test failure/success


## Database Model
Images:

|  Int | ID   |
|---|---|---|---|---|
| VARCHAR  | image  | 
|  DATETIME |   timestamp
|  INT/VARCHAR |  cameraid |  

