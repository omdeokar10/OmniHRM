package main

import "time"

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

type ApplicationAttendance struct {
	Server string
}
