FROM node:18-alpine AS build

WORKDIR /workspace
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:1.27-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/ecommerce-frontend /usr/share/nginx/html
EXPOSE 80
