# Use the official Ubuntu 20.04 as a base image
FROM ubuntu:20.04

# Set environment variables for Go
ENV GO_VERSION 1.19
ENV PATH /usr/local/go/bin:$PATH

# Update and install necessary packages
RUN apt-get update && apt-get install -y \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Download and install Go
RUN wget https://golang.org/dl/go${GO_VERSION}.linux-amd64.tar.gz && \
    tar -C /usr/local -xzf go${GO_VERSION}.linux-amd64.tar.gz && \
    rm go${GO_VERSION}.linux-amd64.tar.gz

# Create a directory for your Go server code
WORKDIR /go/src/app

# Copy your Go server source code into the container
COPY . .

# Build the Go server
RUN go build -o server_attendance_ms ./cmd/api

# Expose the port your Go server will listen on
EXPOSE 8082

# Run the Go server when the container starts
CMD ["./server_attendance_ms"]
