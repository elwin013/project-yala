# Project YALA - Yet Another Link-shortener App

YALA is a simple link shortener that works without logging in - instead it creates "secret key" which allows to view details of created short link and it's statistics.

Generally it is simple app to show that we do not need fancy frameworks to make nice looking, fully working application.

Created for the [MongoDB Atlas Hackathon 2022 on DEV](https://dev.to/devteam/announcing-the-mongodb-atlas-hackathon-2022-on-dev-2107)

## Technologies used

YALA was built using:
* [Javalin](https://javalin.io/) - simple web framework, 
* [jte](https://jte.gg/) - template library,
* [chota](https://jenil.github.io/chota) - micro CSS framework,
* MongoDB for database.


## Running

To run project you need to specify two environmental variables:
* `MONGO_DB_URL` - MongoDB connection string
* `APP_FRONTEND_URL` - app user-facing URL, for local it should be `http://localhost:7070`

To run app in "preview mode" - where no changes can be made (that means no creating new links and deleting old) you
cann pass set `PREVIEW_MODE` variable with `true` value.

## Running in Docker

To run in Docker container:
1. build container e.g. `docker build . -f docker/Dockerfile -t project-yala:latest`
2. run it after the build with env variables
   `docker run -p 7070:7070 -e "APP_FRONTEND_URL=http://localhost:7070/"  "MONGO_DB_URL=$YOUR_MONGO_URL" project-yala:latest`
