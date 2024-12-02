package day02

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 2 {
    fmt.Println(result)
  	t.Fail()
  }
}

func TestPart2(t *testing.T) {
  result := Part2(sample)
  // not 290
  if result != 4 {
    fmt.Println(result)
  	t.Fail()
  }
}
