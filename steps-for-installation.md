We have 3 parts here: 
- spring boot backend.[performance-management]
- ReactJs frontend.[hrm-ui]
- MongoDB database.[docker volume:mongo_data/db]

#### Image for backend application.
- Using the Dockerfile present in performance-management directory.
- `cd performance-management`
- `./mvnw clean install`
- `sudo docker build -t <name-1> .`  
- Verify that `<name-1>` is listed when `docker images ls` is executed.

#### Image for frontend application.
- Using the Dockerfile present in hrm-ui directory.
- `npm i` 
- `sudo docker build -t <name-2>`
- Verify that `<name-2>` is listed when `docker images ls` is executed.

> [!IMPORTANT]  
> Please change the docker-compose file as per your container names & volume directory path.

#### Execute the docker compose command after mondifiying it with appropriate image names & container names. 
- `sudo docker-compose up`