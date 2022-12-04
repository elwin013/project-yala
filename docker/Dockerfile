FROM openjdk:17-slim

RUN groupadd -r yala &&\
    useradd -r -s /bin/false -g yala yala &&\
    mkdir -p /app

RUN chown -R yala:yala /app

USER yala

WORKDIR /app/

ENV MONGO_DB_URL ${MONGO_DB_URL}
ENV APP_FRONTEND_URL ${APP_FRONTEND_URL}
ENV PREVIEW_MODE ${PREVIEW_MODE}
ENV PRODUCTION true

COPY /docker/entrypoint.sh /app
COPY /target/yala.jar /app

EXPOSE 7070
ENTRYPOINT ["/bin/bash", "/app/entrypoint.sh"]