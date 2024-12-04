package day04

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 18 {
    fmt.Println(result)
  	t.Fail()
  }
}

func TestPart2(t *testing.T) {
  result := Part2(sample)
  if result != 9 {
    fmt.Println(result)
  	t.Fail()
  }
}
