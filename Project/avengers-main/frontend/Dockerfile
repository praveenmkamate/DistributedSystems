FROM node:14-alpine
WORKDIR /usr
COPY package*.json /usr
COPY public /usr
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]