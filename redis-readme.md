#Starting redis from docker
docker-compose up -d --build

#Check cached keys in redis
docker exec -it redis-container redis-cli
keys *