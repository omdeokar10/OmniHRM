package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
)

func (app *ApplicationAttendance) MS_db_handler(w http.ResponseWriter, r *http.Request, requestType string, data interface{}, serverURL string) (responseJSON interface{}, err error) {
	// serverURL := Testserver + MicroserviceDBurl
	switch requestType {
	case "POST":
		responseJSON, err = app.postToDB(w, r, serverURL, data)
	case "GET":
		responseJSON, err = app.getUserFromDB(w, r, serverURL, data)
	case "PUT":
		responseJSON, err = app.putToDB(w, r, serverURL, data)
	}
	return responseJSON, err
}

// Send a POST request with JSON payload
// data: user info
func (app *ApplicationAttendance) postToDB(w http.ResponseWriter, r *http.Request, url string, data interface{}) (responseJSON interface{}, err error) {
	jsonData, err := json.Marshal(data)
	if err != nil {
		fmt.Println("JSON Marshal Error:", err)
		return nil, err
	}

	resp, err := http.Post(url, "application/json", bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("POST Request Error:", err)
		return nil, err
	}
	defer resp.Body.Close()

	// Process the response
	responseJSON, err = io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Response Read Error:", err)
		return nil, err
	}
	responseJSON = data
	return responseJSON, nil
}

// Send a GET request and parse the JSON response
/*
Get User:

	Return:
		type UserAttendance struct {
			user      User      `json:user`
			isPresent bool      `json:isPresent`
			dateTime  time.Time `json:dateTime`
		}

*/
func (app *ApplicationAttendance) getUserFromDB(w http.ResponseWriter, r *http.Request, url string, userID interface{}) (data interface{}, err error) {
	// Construct the GET request URL
	getRequestURL := fmt.Sprintf("%s/%d", url+"/user", userID)
	fmt.Printf("GET Request URL: %s\n", getRequestURL)
	resp, err := http.Get(getRequestURL)
	fmt.Printf("**%+v", resp)
	if err != nil {
		fmt.Println("GET Request Error:", err)
		return nil, err
	}
	defer resp.Body.Close()

	// Parse the response JSON
	var userAtten UserAttendance
	decoder := json.NewDecoder(resp.Body)
	if err := decoder.Decode(&userAtten); err != nil {
		fmt.Println("JSON Decode Error:", err)
		return nil, err
	}

	fmt.Printf("GET Response:_______ %+v", userAtten)
	return userAtten, nil
}

// Send a PUT request with JSON payload
func (app *ApplicationAttendance) putToDB(w http.ResponseWriter, r *http.Request, url string, data interface{}) (responseJSON interface{}, err error) {
	jsonData, err := json.Marshal(data)
	if err != nil {
		fmt.Println("JSON Marshal Error:", err)
		return nil, err
	}

	req, err := http.NewRequest("PUT", url, bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("PUT Request Error:", err)
		return nil, err
	}
	req.Header.Set("Content-Type", "application/json")

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("PUT Request Error:", err)
		return nil, err
	}
	defer resp.Body.Close()

	// Process the response
	responseJSON, err = io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Response Read Error:", err)
		return nil, err
	}
	fmt.Printf("PUT Response: %+v", responseJSON)
	return responseJSON, nil
}
