package day11

import (
	"aoc2024/helper"
	"fmt"
	"math"
	"strconv"
	"strings"
)

type stone struct {
	number int
	next   *stone
}

func (s stone)print() {
    current := &s
    fmt.Println("**")
    for current != nil {
        fmt.Print(current.number, " ")
        current = current.next
    }
        fmt.Println("")
}

func (s stone)len() int {
    current := &s
    c := 0
    for current != nil {
        c++
        current = current.next
    }
    return c
}

func parseInput(filename string) (output []*stone) {
	output = helper.ParseInput(filename, func(each_line string) (stones *stone) {
		// 		reports = each_line

		stone_list := strings.Fields(each_line)
		var current *stone
		for _, s := range stone_list {
			stone_number, _ := strconv.Atoi(s)
			new_stone := stone{stone_number, nil}
			if stones == nil {
				stones = &new_stone
				current = stones
			} else {
				current.next = &new_stone
				current = &new_stone
			}
		}

		return
	})

	return
}

func Part1(inputFilename string) (result int) {
	output := parseInput(inputFilename)[0]

	for i := 0; i < 25; i++ {
		current := output
		for current != nil {
// 		fmt.Println("N: ", current.number)
			if current.number == 0 {
// 		fmt.Println("0: ")
			    current.number = 1
			} else if (int(math.Log10(float64(current.number)))+1)%2 == 0 {
// 		fmt.Println("E: ")
			    digits := int(math.Log10(float64(current.number)))+1
			    sn := strconv.Itoa(current.number)
			    d1, d2 := sn[:digits/2], sn[digits/2:]
			    current.number,_ = strconv.Atoi(d1)
			    next_number, _ := strconv.Atoi(d2)
			    next_stone := stone{next_number, current.next}
			    current.next = &next_stone
			    current = &next_stone
			} else {
// 		fmt.Println("O: ", current.number)
				current.number = current.number * 2024
			}
			current = current.next
		}
// 		output.print()
	}

	return output.len()
}
