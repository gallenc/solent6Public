# SimpleJMSClient

A simple test program for sending and reading messages from an ActiveMQ queue. There is no guarantee that this implementation is correct or effective.

## Usage

This is a basic commandline tool that sends and reads messages in a queue managed by a local ActiveMQ broker. When running the tool takes the following commands:
- `>read` will get the next message on the queue and display its text.
- `>q` will close the tool.

- Any other input will be saved as a message and sent to the queue.

### Broker

Currently, the ActiveMQ Maven plugin has not been configured. In the meantime, you can download and run [this service](http://activemq.apache.org/components/classic/download/) to create a host. The admin console will then be available at `localhost:8161`. See: https://activemq.apache.org/getting-started

## Contact
Kieron - 4gillk91@solent.ac.uk