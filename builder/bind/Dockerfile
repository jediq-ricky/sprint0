FROM sameersbn/bind:latest
MAINTAINER Ricky Walker "ricky.walker@jediq.com"
#NAME sprint0/bind

RUN apt-get -y update

RUN apt-get -y install puppet

COPY Puppetfile.pp /puppet/

RUN puppet module install ajjahn/dns

RUN puppet apply /puppet/Puppetfile.pp
