package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"
	"time"

	"github.com/go-chi/chi/v5"
)

type DBMSServer struct {
	ServerName string
}

type ResponsePayload struct {
	Status  string `json:"status"`
	Message string `json:"message"`
	Version string `json:"version"`
}

type User struct {
	UserName string `json:"username"`
	UserId   int    `json:"userid"`
}

type UserAttendance struct {
	Day               time.Time `json:"day"`
	User              User      `json:"user"`
	UserPresent       bool      `json:"isPresent"`
	UserdateTimeStart time.Time `json:"dateTimeStart"`
	UserdateTimeEnd   time.Time `json:"dateTimeEnd"`
}

var u1 UserAttendance

func (DBMS *DBMSServer) routes() http.Handler {
	// create a router mux
	mux := chi.NewRouter()

	// mux.Use(middleware.Recoverer)
	// mux.Use(app.enableCORS)
	//mux.Get("/", app.Home)
	mux.Get("/", HomeHandler)
	mux.Get("/microservice/db/UserAttendance/user/{user_id}", GetUserHandler)
	mux.Post("/microservice/db/UserAttendance/signin", SignInHandler)

	return mux
}

func init() {
	u1.Day = time.Now()
	u1.User.UserId = 123
	u1.User.UserName = "Max"
	u1.UserPresent = true
	u1.UserdateTimeStart = time.Now()
	u1.UserdateTimeEnd = time.Now()
}

const port = 8004

func SignInHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "Welcome to UserAttendance SignIn Handler")

	var UserAttdnc UserAttendance

	decoder := json.NewDecoder(r.Body)
	err := decoder.Decode(&UserAttdnc)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	fmt.Printf("%+v", UserAttdnc)
}

func HomeHandler(w http.ResponseWriter, r *http.Request) {
	// Create a ResponsePayload struct
	payload := ResponsePayload{
		Status:  "active",
		Message: "UserAttendance Microservice up and running",
		Version: "1.0.0",
	}

	// Marshal the payload into JSON
	jsonData, err := json.Marshal(payload)
	if err != nil {
		http.Error(w, "Failed to marshal JSON", http.StatusInternalServerError)
		return
	}

	// Set the content type to JSON
	w.Header().Set("Content-Type", "application/json")

	// Write the JSON response
	w.Write(jsonData)
}

// func GetUserHandler(w http.ResponseWriter, r *http.Request) {
// 	// Get the user ID from the URL parameters.
// 	userID := chi.URLParam(r, "id")

// 	// Perform logic to fetch the user with the provided ID.
// 	// You can replace this with your own logic to fetch the user.
// 	// For this example, we'll just print the user ID.
// 	fmt.Fprintf(w, "Fetching user with ID: %s", userID)
// }

// GetUser is the route handler for /user:user_id
func GetUserHandler(w http.ResponseWriter, r *http.Request) {
	//Extract the user ID from the URL
	fmt.Println("Inside GetUserHandler")
	userIDStr := chi.URLParam(r, "user_id")

	fmt.Printf("Here, userID %s\n", userIDStr)
	// Search for the user by ID
	//var user UserAttendance

	//Check if the user was found
	userID, _ := strconv.Atoi(userIDStr)
	fmt.Printf("Here, userID int %d and DB userID: %d", userID, u1.User.UserId)

	if u1.User.UserId != userID {
		http.Error(w, "User not found", http.StatusNotFound)
		return
	}

	// Marshal the user data to JSON and send it in the response
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(u1)
}

func main() {

	var DB DBMSServer
	// Print a message that the server is starting
	log.Println("Starting UserAttendance on port", port)

	// Start a web server with the provided multiplexer (mux)
	err := http.ListenAndServe(fmt.Sprintf(":%d", port), DB.routes())
	if err != nil {
		fmt.Println("Error: ", err)
		log.Fatal(err)
		return
	}
}
