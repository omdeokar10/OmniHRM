package main

import (
	"fmt"
	"log"
	"net/http"
)

const port = 8082

func main() {
	// set UserAttendance config
	var app ApplicationAttendance

	log.Println("Starting UserAttendance on port", port)

	// start a web server
	err := http.ListenAndServe(fmt.Sprintf(":%d", port), app.routes())
	if err != nil {
		fmt.Println("Error: ", err)
		log.Fatal(err)
		return
	}
}
