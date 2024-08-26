### Local docker install
```
sudo docker run -d --name docker_mongo_hrm \
    -e MONGO_INITDB_ROOT_USERNAME=admin \
    -e MONGO_INITDB_ROOT_PASSWORD=password \
    -p 27017:27017 mongo \
    -v /home/kaustubh/Desktop/HRM/mongo_hrm_data:/data/db    
```

