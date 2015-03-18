DNS resolver simulation implemented in Java

Note:
	Please start in the following order to avoid errors:-
	1. ComServer
	2. TTServer
	3. ComNameServer
	4. TTNameServer
	5. RootNameServer
	6. DNSResolver
	7. DNSClient

.tt domain: igov.tt
hostnames:	mail.igov.tt
			www.igov.tt
			odpm.igov.tt
			co.igov.tt

.com domain:	google.com
hostnames: 		console.google.com
				maps.google.com
				drive.google.com
				mail.google.com
				calendar.google.com


ComServer 		: .com server 
TTServer 		: .tt server
ComNameServer	: google.com server
TTNameServer	: igov.tt server


CacheRecord : 
	simple class which is used to associate host name and ip address

Basic Flow :
	Client queries DNSResolver (e.g. maps.google.com) DNS resolver first check cache, if found the hostname and ip address is returned, if not, the reslover first contacts the Root Name Server with will give the location of the respective domain's Name Server (.com server) . Once the Resolver has the address of the server it then calls the domain server (google.com server) to determin if the hostname (maps.google.com) exists.