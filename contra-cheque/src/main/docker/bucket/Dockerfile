FROM node:14-alpine

WORKDIR /app

RUN mkdir aws

COPY package.json package-lock.json /app/
RUN npm i

COPY index.js /app/

EXPOSE 3000
CMD [ "node", "." ]