package day01

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"
var input = "input.txt"

// func TestImport(t *testing.T) {
// 	day1(day1sample)
// }

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 11 {
    fmt.Println(result)
  	t.Fail()
  }
}
