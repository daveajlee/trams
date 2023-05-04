<p align="center">
<img src="https://www.davelee.de/common/assets/img/portfolio/trams-logo.png" alt="TraMS" width="300" height="300">
</p>

<p align=center><a href="https://app.codacy.com/gh/daveajlee/trams/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade"><img src="https://app.codacy.com/project/badge/Grade/9b51ca637d4743e4b5f8c75afcfad4e3" alt="Codacy Badge"> </a>
</p>

TraMS is a transport management game where you ensure that your own public transport company picks up passengers on-time. If your vehicles are delayed in traffic then you need to take action to get them back on schedule before your passengers get frustrated and leave for another transport operator.

## Contents

| Folder | Description |
| --- | ----------- |
| app | Future App for TraMS based on React Native for Android and iOS. (Currently being developed and not yet feature complete). Please use the old Desktop Client instead: https://github.com/daveajlee/trams-game |
| desktop | Future Desktop Client for TraMS based on Electron and Angular for Windows, Linux and Mac OS. (Currently being developed and not yet feature complete). Please use the old Desktop Client instead: https://github.com/daveajlee/trams-game  |
| docs | Current TraMS website with more information about the goals of the TraMS project. |
| docker | Old docker files to build docker environment which is not currently used. |
| server | This is the server application of TraMS with a RESTful API which any client can then implement in any programming language. |

## Server

### How to use

1.  To use the server you need to specify the user specific configuration parameters in application.yml
2.  Create an executable jar using mvn clean install.
3.  Run the jar (for example in dev-test mode): java -Dspring.profiles.active=dev-test -jar trams-server.jar

### Documentation

TraMS comes with a Swagger API documentation which can be used to implement the API in a client on any programming language. The swagger user interface is available at: <http://your-trams-server:your-port/swagger-ui.html>

### Available Profiles
*   dev-test - This profile activates the swagger API documentation. This works well for development and testing.

## Desktop

Since the future desktop client based on Electron is still being developed, the old Java Desktop Client can be used instead: https://github.com/daveajlee/trams-game

## Current Limitations

* Details of what is currently implemented can be found here: https://www.davelee.de/trams/#/features
* Details of what is currently planned for the near future: https://www.davelee.de/trams/#/roadmap
* Whilst the server is relatively feature complete (see the above links for more details), there is no client to support this server yet. Please use the old Desktop game repo (https://github.com/daveajlee/trams-game) or write your own client ;)
