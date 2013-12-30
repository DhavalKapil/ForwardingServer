ForwardingServer
================

A server program that accepts input from one client and forwards it to all the clients connected to it

<h3>Description:</h3>
This command line program will wait for clients to be connected to it. If any client sends a message it would be forwarded to each of the clients that are connected to the server including the one who sent the message. The message can be in any format.

<h3>License:</h3>
MIT license http://www.opensource.org/licenses/mit-license.php

<h3>Usage:</h3>
java Server -port -maxNoOfConnecions<br>
port : It is the port at which the server will wait and accept clients<br>
maxNoOfConnections : It is the maximum no. of clients that can connect.
