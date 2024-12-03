package day03

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"
var sample2 = "sample2.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 161 {
    fmt.Println(result)
  	t.Fail()
  }
}

func TestPart2(t *testing.T) {
  result := Part2(sample2)
  if result != 48 {
    fmt.Println(result)
  	t.Fail()
  }
}
