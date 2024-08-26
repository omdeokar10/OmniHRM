## Steps 

We have 3 different moving parts here: 
- MongoDB database.[docker volume:mongo_data/db]
- spring boot backend.[performance-management]
- ReactJs frontend.[hrm-ui]

#### Create image for mongo database. 
> [Fetch Mongo image](https://hub.docker.com/_/mongo)  from docker hub. 

> ```sudo docker run -d --name <docker-container-name> -e MONGO_INITDB_ROOT_USERNAME=admin   -e MONGO_INITDB_ROOT_PASSWORD=password -p 27017:27017 -v /home/kaustubh/Desktop/OmniHRM/starter_data:/data/db mongo```

#### Create Image for backend application.
- Using the Dockerfile present in performance-management directory.
- `cd performance-management`
- `./mvnw clean package -Dspring.data.mongodb.uri=mongodb://admin:password@localhost:27017/demo1?authSource=admin`
- `sudo docker build -t <backend-image-name> .`  
- Verify that `<backend-image-name>` is listed when `docker images ls` is executed.

#### Create Image for frontend application.
- Using the Dockerfile present in hrm-ui directory.
- `npm i` 
- `sudo docker build -t <frontend-image-name>`
- Verify that `<frontend-image-name>` is listed when `docker images ls` is executed.

> [!IMPORTANT]  
> Please change the docker-compose file as per your container names & volume directory path.

#### Finally, execute the docker compose command after mondifiying it with appropriate image names & container names. 
- `sudo docker-compose up`

## Done !