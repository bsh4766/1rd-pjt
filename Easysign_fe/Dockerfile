FROM node:lts-alpine as builder

WORKDIR /front

ENV PATH /front/node_modules/.bin:$PATH

COPY . .

RUN npm install
RUN npm install react-scripts@3.0.1 -g

CMD ["npm", "start"]
EXPOSE 3000