package main

type User struct {
	UserName string `json:"username"`
	UserId   int    `json:"userid"`
}

type UserAttendance struct {
	Day               string `json:"day"`
	UserId            int    `json:"userid"`
	UserPresent       bool   `json:"present"`
	UserdateTimeStart string `json:"startTime"`
	UserdateTimeEnd   string `json:"endTime"`
}

type ApplicationAttendance struct {
	Server string
}
