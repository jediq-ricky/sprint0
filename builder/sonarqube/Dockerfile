FROM jenkins:latest
MAINTAINER Ricky Walker "ricky.walker@jediq.com"
#NAME sprint0/jenkins

USER root

RUN apt-get -y update

RUN apt-get -y install puppet

COPY Puppetfile.pp /puppet/

RUN puppet apply /puppet/Puppetfile.pp
