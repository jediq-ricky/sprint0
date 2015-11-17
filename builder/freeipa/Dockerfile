FROM adelton/freeipa-server:latest
MAINTAINER Ricky Walker "ricky.walker@jediq.com"
#NAME sprint0/freeipa

USER root

RUN dnf update

RUN dnf install puppet

COPY Puppetfile.pp /puppet/

RUN puppet apply /puppet/Puppetfile.pp
