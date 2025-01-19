#Starting up redis
docker-compose up -d --build

#Check cached keys in redis
docker exec -it redis-container redis-cli
keys *

#Stop redis
docker-compose down

#Check running docker processes
docker ps