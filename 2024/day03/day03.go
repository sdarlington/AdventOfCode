package day03

import (
	"aoc2024/helper"
	"regexp"
	"strconv"
)

func parseInput(filename string) (code []string) {
	code = helper.ParseInput(filename, func(each_line string) (reports string) {
		reports = each_line
		return
	})

	return
}

func day3(inputFilename string, regex string) (result int) {
	lines := parseInput(inputFilename)

	fullRegex := regexp.MustCompile(regex)
	mulRegex := regexp.MustCompile("\\d+")
	enabled := true
	for _, input := range lines {
		mul := fullRegex.FindAllString(input, -1)
		for _, command := range mul {
			if command == "do()" {
				enabled = true
			} else if command == "don't()" {
				enabled = false
			} else if enabled {
				digits := mulRegex.FindAllString(command, -1)
				sum := 1
				for _, d := range digits {
					val, err := strconv.Atoi(d)
					if err == nil {
						sum *= val
					}
				}
				result += sum
			}
		}
	}

	return
}

func Part1(inputFilename string) (result int) {
    result = day3(inputFilename, "mul\\(\\d{1,3},\\d{1,3}\\)")

	return
}

func Part2(inputFilename string) (result int) {
    result = day3(inputFilename, "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")

	return
}
