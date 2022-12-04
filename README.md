# Project YALA - Yet Another Link-shortener App

YALA is a simple link shortener that works without logging in - instead it creates "secret key" which allows to view details of created short link and it's statistics.

Generally it is simple app to show that we do not need fancy frameworks to make nice looking, fully working application.

It was created for the [https://dev.to/devteam/announcing-the-mongodb-atlas-hackathon-2022-on-dev-2107](MongoDB Atlas Hackathon 2022 on DEV)


## Technologies used

YALA was built using:
* [https://javalin.io/](Javalin) - simple web framework, 
* [https://jte.gg/](jte) - template library,
* [https://jenil.github.io/chota](chota) - micro CSS framework,
* MongoDB for database.


## Running

To run project you need to specify two environmental variables:
* `MONGO_DB_URL` - MongoDB connection string
* `APP_FRONTEND_URL` - app user-facing URL, for local it should be `http://localhost:7070`

To run app in "preview mode" - where no changes can be made (that means no creating new links and deleting old) you
cann pass set `PREVIEW_MODE` variable with `true` value.

