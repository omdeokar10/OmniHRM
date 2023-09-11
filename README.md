# OmniHRM

Provides a HRM framework.

Components :

1. A common request handler providing user authentication and authorization. 

Features: 

 A. Time logging for users -> (8000)
	marking their start and end time for day with updates mentioning the work done during the day.
 
 B. Attendance. ([port])
	<> 
 
 C. DB handler.[8081]
	A microservice handling all database calls made by all other microservices.
	

## Installing Go

1. curl -OL https://golang.org/dl/go1.16.7.linux-amd64.tar.gz
2. sudo tar -C /usr/local -xvf go1.16.7.linux-amd64.tar.gz
3. sudo vim ~/.profile
	3.1 export PATH=$PATH:/usr/local/go/bin
	3.2 export GO111MODULE="auto"
	3.3 export GOBIN="/home/vagrant/go/bin"
	3.4 export GOMODCACHE="/home/vagrant/go/pkg/mod"
	3.5 export GOPATH="/home/vagrant/go"
	3.6 export GOPROXY="https://proxy.golang.org"
4. source ~/.profile
5. go mod init github.com/Lkishor123/Gnbsim_5G