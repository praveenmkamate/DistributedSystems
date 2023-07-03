# Introduction

This is the implementation of the Quotation Services using Actor Programming using Akka, which is built on top of Java. This system is distributed using a range of distribution technologies as part of the COMP41720 module laboratories.

# Running the Program

This is a maven project, complete the following steps:

1.  Download/Clone the project
2.  Use a command shell, and go to the 22200326/quoco-akka folder
3.  Type: `mvn clean install`
4.  Run the broker using the command: `mvn exec:java -pl broker`
5.  Run the three services i.e. AuldFellas, DodgyDrivers and GirlPower using the command: `mvn exec:java -pl <Service Name>`
    Example: For AuldFellas use the command: `mvn exec:java -pl auldfellas`
6.  Run the client using the command: `mvn exec:java -pl client`
7.  The quotations for all the Client Information should be displayed.
