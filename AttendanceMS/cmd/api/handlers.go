package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

func (app *ApplicationAttendance) Home(w http.ResponseWriter, r *http.Request) {
	var payload = struct {
		Status  string `json:"status"`
		Message string `json:"message"`
		Version string `json:"version"`
	}{
		Status:  "active",
		Message: "UserAttendance Microservice up and running",
		Version: "1.0.0",
	}

	_ = app.writeJSON(w, http.StatusOK, payload)
}

/*
expects user
*/
func (app *ApplicationAttendance) SignIn(w http.ResponseWriter, r *http.Request) {
	var user User

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&user); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	fmt.Printf("%+v", user)
	resp := map[string]string{
		"message": "SignIn Successful",
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(resp)
	// Process

	// fetch from DB Not required.
	// verification to be done in main handler deamon before calling attendance micro service.

	// TO DO: verify is Present.
	var UserAttdnc UserAttendance
	UserAttdnc.UserId = user.UserId
	UserAttdnc.UserPresent = true
	UserAttdnc.UserdateTimeStart = time.Now().Format("2006-01-02 15:04:05")
	UserAttdnc.UserdateTimeEnd = time.Now().Format("2006-01-02 15:04:05")
	UserAttdnc.Day = time.Now().Format("2006-01-02 15:04:05")
	//else: send error: already Signed In

	// Send to DB MS
	_, err := app.MS_db_handler(w, r, "POST", UserAttdnc, TestDBURL+"/signin")
	if err != nil {
		fmt.Println("Error While Attendance Signin")
	}
}

func (app *ApplicationAttendance) SignOut(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Entering SignOUT")
	var user User

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&user); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	fmt.Printf("%+v", user)
	// Process

	// fetch from DB
	var UserAttdnc UserAttendance
	fmt.Printf("Entering SignOUT Calling MS_db_handler GET, userID: %d, url: %s\n", user.UserId, TestDBURL)
	UserAtt, err := app.MS_db_handler(w, r, "GET", user.UserId, TestDBURL)

	fmt.Printf("%+v", UserAtt)
	UserAttdnc = UserAtt.(UserAttendance)
	// if StartTime != Endtime: Send Error: Already SignedOut.
	//
	//outputTimeStr := parsedTime.Format("2006-01-02 15:04")

	startime := UserAttdnc.UserdateTimeStart
	endTime := UserAttdnc.UserdateTimeEnd

	if startime != endTime {

		resp := map[string]string{
			"message": "Not SignIn",
		}
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(resp)
		return
	}
	// Else:
	UserAttdnc.UserId = user.UserId
	UserAttdnc.UserPresent = true
	UserAttdnc.UserdateTimeEnd = time.Now().Format("2006-01-02 15:04")
	// Send to DB MS
	// func putToDB(url string, data interface{})

	fmt.Println("Entering SignOUT Calling MS_db_handler GET")
	_, err = app.MS_db_handler(w, r, "POST", UserAttdnc, TestDBURL+"/signout")
	if err != nil {
		fmt.Println("Error While Attendance Signout")
	}

	resp := map[string]string{
		"message": "SignOut Successful",
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(resp)
}
