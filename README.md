# reactive-pet-supplies

Mongodb

create file : mongo.config

e.g.:
##store data here
dbpath=D:\mongodb\data

##all output go here
logpath=D:\mongodb\log\mongo.log

Start daemon:
mongod --config /d/mongodb/mongo.config 

Import data:
mongoimport --db petsupplies --collection products --file /d/<path-to-file>/products.json
