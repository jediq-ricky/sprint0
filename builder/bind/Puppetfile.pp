#Puppetfile

node 'testhost.example.com' {

  include dns::server

  dns::zone { 'example.com':
    soa         => 'ns1.example.com',
    soa_email   => 'admin.example.com',
    nameservers => [ 'ns1.example.com', ]
  }
  dns::zone { '1.168.192.IN-ADDR.ARPA':
    soa         => 'ns1.example.com',
    soa_email   => 'admin.example.com',
    nameservers => [ 'ns1.example.com', ]
  }
  dns::record::a { 'ns1':
    zone => 'example.com',
    data => [ '192.168.1.1', ],
    ptr  => true;
  }
}




#node 'dns.sprint0.local' {
#  include dns::server
#
#  # Forwarders
#  dns::server::options { '/etc/bind/named.conf.options':
#    forwarders => [ '8.8.8.8', '8.8.4.4' ]
#  }
#
#  # Forward Zone
#  dns::zone { 'sprint0.local':
#    soa         => 'ns1.sprint0.local',
#    soa_email   => 'admin.sprint0.local',
#    nameservers => ['ns1']
#  }
#
#  # A Records:
#  dns::record::a {
#    'nginx':
#      zone => 'sprint0.local',
#      data => ['127.0.0.1'];
#    'jenkins':
#      zone => 'sprint0.local',
#      data => ['127.0.0.1'];
#   }
#
#}