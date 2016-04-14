
#Sprint Zero Documentation

##Opinionated scaffolding for software project toolsets


Sprint Zero (sprint0) tries to solve the development startup problem of toolset configuration.  It downloads, deploys and configures the basic tools required to start a development process.


###Codebase
The codebase is segregated into a number of areas : 

* **builder** : For building the bespoke sprint0 docker images
* **cli** : The command line interface for sprint0
* **website** : The source code for the (sprint0.io)[http://sprint0.io] website
* **documentation** : The documentation you are reading now


###Tools
The first release of sprint0 will provide the following tools :

* **Nginx** : Reverse proxy to the other tools and host of the sprint0 pages
* **Jenkins** : Continuous Integration Server
* **GitLab** : GIT compliant Source Code Management

Future releases of sprint0 will provide these further tools :

* **SonarQube** : Static code analysis
* **Mattermost** : Slack compliant hosted messaging system
* **Bloodhound** : Apache Open Source issue tracking system
* **MySQL** : For persistent storage of tool data


