FROM nginx:1.9.0
MAINTAINER Ricky Walker "ricky.walker@jediq.com"
#NAME sprint0/nginx

RUN apt-get -y update

RUN apt-get -y install puppet

COPY Puppetfile.pp /puppet/

RUN puppet apply /puppet/Puppetfile.pp
