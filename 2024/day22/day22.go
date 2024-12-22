package day22

import (
	"aoc2024/helper"
	"strconv"
	"log"
)


func parseInput(filename string) (output []int) {
	output = helper.ParseInput(filename, func(each_line string) (starting_number int) {
		starting_number, e := strconv.Atoi(each_line)
		if e != nil {
			log.Println("Error! ", e)
		}
		return
	})

	return
}

func mix(v1, v2 int) int {
	return v1 ^ v2
}

func prune(v1 int) int {
	return v1 % 16777216
}

func calculate_next(start int) int {
	secret := start
	
	result := start * 64
	secret = mix(result,start)
	secret = prune(secret)

	result2 := secret / 32
	secret = mix(result2, secret)
	secret = prune(secret)

	result3 := secret * 2048
	secret = mix(secret, result3)
	secret = prune(secret)

	return secret
}

func calculate(start, iteration int) int {
	secret := start
	for i := 0; i < iteration; i++ {
		secret = calculate_next(secret)
	}
	return secret
}

func Part1(inputFilename string) (result int) {
    starting_numbers := parseInput(inputFilename)

	result = 0
	for _, n := range starting_numbers {
		result += calculate(n, 2000)
	}

    return
}
