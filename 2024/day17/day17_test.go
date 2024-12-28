package day17

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestOp1(t *testing.T) {
	cpu := cpu{0,0,9,[]int{2,6},0, []int{}}
	next(&cpu)
	if cpu.b != 1 {
		fmt.Println(cpu, " ", cpu.b)
		t.Fail()
	}
}

func TestOp2(t *testing.T) {
	cpu := cpu{10,0,0,[]int{5,0,5,1,5,4},0,[]int{}}
	for i := 0; i < 3; i++ {
		next(&cpu)
	}
	out := genOutput(cpu)
	if out != "0,1,2" {
		fmt.Println(out)
		t.Fail()
	}
}

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != "4,6,3,5,6,3,5,2,1,0" {
    fmt.Println(result)
  	t.Fail()
  }
}

