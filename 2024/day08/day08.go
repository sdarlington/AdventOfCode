package day08

import (
	"aoc2024/helper"
	"fmt"
)

type coord struct {
	x, y int
}

type plan struct {
	Grid       []string
	xMax, yMax int
}

func (p plan) cell(xy coord) string {
	return string(p.Grid[xy.y][xy.x])
}

func (p plan) print(antinodes map[coord]bool) {
	for y := 0; y < p.yMax; y++ {
		for x := 0; x < p.xMax; x++ {
			_, ok := antinodes[coord{x, y}]
			if ok {
				fmt.Print("#")
			} else if p.cell(coord{x, y}) != "." {
				fmt.Print(p.cell(coord{x, y}))
			} else {
				fmt.Print(".")
			}
		}
		fmt.Println("")
	}
}

func parseInput(filename string) (output plan, grid map[coord]string, antenna map[string][]coord) {
	input := helper.ParseInput(filename, func(each_line string) (reports string) {
		reports = each_line
		return
	})

	output.Grid = input
	output.xMax = len(input[0])
	output.yMax = len(input)

	grid = make(map[coord]string)
	antenna = make(map[string][]coord)
	for y := 0; y < output.yMax; y++ {
		for x := 0; x < output.xMax; x++ {
			location := coord{x, y}
			cell := output.cell(location)
			if cell != "." {
				grid[location] = cell
				antennaList, ok := antenna[cell]
				if ok {
					antennaList = append(antennaList, location)
				} else {
					antennaList = []coord{location}
				}
				antenna[cell] = antennaList
			}
		}
	}

	return
}

func gridTest(location coord, grid plan) bool {
	return location.x >= 0 &&
		location.x < grid.xMax &&
		location.y >= 0 &&
		location.y < grid.yMax
}

func makePermutation(antenna map[string][]coord, process func(coord, coord)) {
	for _, nodes := range antenna {
		for l1 := 0; l1 < len(nodes)-1; l1++ {
			for l2 := l1 + 1; l2 < len(nodes); l2++ {
				n1 := nodes[l1]
				n2 := nodes[l2]

				process(n1, n2)
			}
		}
	}
	return
}

func Part1(inputFilename string) (result int) {
	output, _, antenna := parseInput(inputFilename)

	antinodes := make(map[coord]bool)
	makePermutation(antenna, func(n1, n2 coord) {
		dx, dy := n2.x-n1.x, n2.y-n1.y
		an1 := coord{n1.x - dx, n1.y - dy}
		an2 := coord{n2.x + dx, n2.y + dy}

		if gridTest(an1, output) {
			antinodes[an1] = true
		}
		if gridTest(an2, output) {
			antinodes[an2] = true
		}
	})
	result = len(antinodes)

	return
}

func Part2(inputFilename string) (result int) {
	output, _, antenna := parseInput(inputFilename)

	antinodes := make(map[coord]bool)
	makePermutation(antenna, func(n1, n2 coord) {
		antinodes[n1] = true
		antinodes[n2] = true

		dx, dy := n2.x-n1.x, n2.y-n1.y

		coordTest := func(an1 coord, mul int) {
			for {
				an1 = coord{an1.x + mul*dx, an1.y + mul*dy}
				if gridTest(an1, output) {
					antinodes[an1] = true
				} else {
					break
				}
			}
		}
		coordTest(n1, -1)
		coordTest(n2, 1)
	})
	result = len(antinodes)

	return
}
