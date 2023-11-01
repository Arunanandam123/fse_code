# Use an official Node.js runtime as the base image
FROM node:16
# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Install project dependencies
RUN npm install

# Copy the rest of the application source code to the working directory
COPY . .

# Build the Angular application
RUN ng build --prod

# Expose a port for the application (you can customize this port)
EXPOSE 80

# Set a custom host for the Angular application
ENV HOST 0.0.0.0

# Define the command to run your application when the container starts
CMD ["npm", "start"]
