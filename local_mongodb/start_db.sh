#!/bin/sh
docker start local-mongo
echo WISHLIST_COSMOSDB_DATABASE_NAME=admin
echo WISHLIST_COSMOSDB_CONNECTION_STRING=mongodb://mongoadmin:secret@0.0.0.0:27017
