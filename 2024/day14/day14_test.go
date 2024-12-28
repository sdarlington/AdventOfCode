package day14

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

var results = []coord{ coord{4,1}, coord{6,5}, coord{8,2}, coord{10, 6}, coord{1,3}}

func TestNext1(t *testing.T) {
	robot := robot{coord{2,4}, velocity{2,-3}}
	for i,l := range results {
		robot = next(robot, 11 , 7)
		if !(robot.location.x == l.x && robot.location.y == l.y) {
			fmt.Println("Got ", robot, " Expected: ", l, "/",i)
			t.Fail()
		}
	}
}

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 12 {
    fmt.Println(result)
  	t.Fail()
  }
}

