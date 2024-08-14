# OmniHRM
Provides an end 

> Components :

>> A common request handler providing user authentication and authorization. 

> Features: 

>> A. Time logging for users -> (8000)
	marking their start and end time for day with updates mentioning the work done during the day.
 
>> B. Attendance. ([port])
	<> 
 
>> C. DB handler.[8081]
	A microservice handling all database calls made by all other microservices.
	

## Installing Go
```
curl -OL https://golang.org/dl/go1.16.7.linux-amd64.tar.gz
```
```
sudo tar -C /usr/local -xvf go1.16.7.linux-amd64.tar.gz
```
```
sudo vim ~/.profile
```
```
export PATH=$PATH:/usr/local/go/bin
export GO111MODULE="auto"
export GOBIN="/home/kaustubh/go/bin"
export GOMODCACHE="/home/kaustubh/go/pkg/mod"
export GOPATH="/home/kaustubh/go"
export GOPROXY="https://proxy.golang.org"
```
```
source ~/.profile
```
```
go mod init github.com/Lkishor123/Gnbsim_5G
```
Downloading packages
> go mod tidy
Running project.
> go run ./cmd/api


1. curl -OL https://golang.org/dl/go1.16.7.linux-amd64.tar.gz
2. sudo tar -C /usr/local -xvf go1.16.7.linux-amd64.tar.gz
3. sudo vim ~/.profile
	- export PATH=$PATH:/usr/local/go/bin
	- export GO111MODULE="auto"
	- export GOBIN="/home/vagrant/go/bin"
	- export GOMODCACHE="/home/vagrant/go/pkg/mod"
	- export GOPATH="/home/vagrant/go"
	- export GOPROXY="https://proxy.golang.org"
4. source ~/.profile
5. go mod init `project_name`
6. go mod tidy


## Docker:

### After Installation:

1. Step 1
```
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker
```

2. Step 2
```
sudo chown "$USER":"$USER" /home/"$USER"/.docker -R
sudo chmod g+rwx "$HOME/.docker" -R

```

3. Step 3
```
Enable
sudo systemctl enable docker.service
sudo systemctl enable containerd.service

Disable
sudo systemctl disable docker.service
sudo systemctl disable containerd.service

```

## Delete all comtainers and images:
https://www.digitalocean.com/community/tutorials/how-to-remove-docker-images-containers-and-volumes</p>
`sudo docker rm $(sudo docker ps -a -f status=exited -q)`</p>
`sudo docker rmi -f $(sudo docker images -a -q)`</p>

## Build Images
`Build Steps : sudo docker build -t name:version .`

## Launch
`sudo docker run  --name cont_name -it --cap-add=NET_ADMIN --device /dev/net/tun --privileged --rm image:version /bin/bash`</p>
`sudo docker exec -it container`</p>