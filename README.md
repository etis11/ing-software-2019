# ing-sw-2019-44
Progetto Ing. Software 2019 gruppo 44 - Adrenalina
Oscar Francesco Pindaro
Alessandro Passoni
Etis Peza

The following folder contains the project and the client's and server's implementation of the Adrenalina board game.
The project has a fully functional CLI, the server and client can communicate with both socket and RMI, with all the rules but final frenzy and venom granade.
We chose the advanced functionality "multiple matches".
We implemented also a GUI, but we didn't manage to attach the logic. So far, It can connect with socket, join a game, and see the change of the tiles that contains weapons.

Summing up:
-CLI
-Socket
-RMI
-FA: multiple matches
-All rules
-GUI works with socket but can't interact with other player


client's jar path : /jar/client
serves's jar path : /jar/server
client with javafx jar path: /jar/clientfx

How to launch the server ( working directory: /jar/server)
java -jar server.jar [server IP] [serverPort] [timerTurno] [timerStartPartita]
- [severIP] MANDATORY : server ip, necessary for rmi
- [serverPort] MANDATORY: server port
- [timerTurno] optional: timer expressed in seconds, it's the lenght of a turn
- [timerStartPartita] optional: timer expressed in seconds, it's the waiting time in the lobby

How to launch the client (working directory /jar/server)
java -jar client.jar <server Ip> <serverPort> <client IP>
All the arguments are MANDATORY

How to launch the gui (working directory: /jar/clientfx)
java -jar clientfx.jar <serverIP> <serverPort>

We used a java programa and based on the graphic framework Processing3.7(https://processing.org/) for the creation of the map json. The source code is inside the mapJsonCreator folder, can be launched as executable on windows.
