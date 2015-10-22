FROM ubuntu:12.10
MAINTAINER Ricky Walker "ricky.walker@jediq.com"

RUN apt-get -y update
RUN apt-get -y install rubygems
RUN echo "gem: --no-ri --no-rdoc" > ~/.gemrc
RUN gem install puppet librarian-puppet
