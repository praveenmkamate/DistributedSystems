FROM mcr.microsoft.com/mssql/server:2019-latest
WORKDIR /usr/src/app
COPY ./CreateDataBaseSchema.sql .
COPY ./entrypoint.sh .
COPY ./run-initialization.sh .
ENV ACCEPT_EULA Y
ENV SA_PASSWORD IXlogin#1
EXPOSE 1433
USER mssql
RUN /bin/bash ./entrypoint.sh