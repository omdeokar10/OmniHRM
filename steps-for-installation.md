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